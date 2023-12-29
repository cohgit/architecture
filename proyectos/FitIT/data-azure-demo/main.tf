terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "=3.0.0"
    }
  }
}
provider "azurerm" {
  features {}
}

# grupo de recursos demo
resource "azurerm_resource_group" "rg_demo" {
  name     = "rg-demo"
  location = "West US"
}

# red demo
resource "azurerm_virtual_network" "net_demo" {
  name                = "network-demo"
  address_space       = ["10.0.0.0/16"]
  location            = azurerm_resource_group.rg_demo.location
  resource_group_name = azurerm_resource_group.rg_demo.name

  depends_on = [ azurerm_resource_group.rg_demo ]
}

# subred demo
resource "azurerm_subnet" "subnetdemo" {
  name                 = "internal-subnet"
  resource_group_name  = azurerm_resource_group.rg_demo.name
  virtual_network_name = azurerm_virtual_network.net_demo.name
  address_prefixes     = ["10.0.2.0/24"]
  depends_on = [ azurerm_virtual_network.net_demo ]
}

# Create public IPs
resource "azurerm_public_ip" "public_ip_demo" {
  name                = "public-ip-demo"
  location            = azurerm_resource_group.rg_demo.location
  resource_group_name = azurerm_resource_group.rg_demo.name
  allocation_method   = "Dynamic"
  depends_on = [ azurerm_resource_group.rg_demo ]
}

# Create Network Security Group and rules
resource "azurerm_network_security_group" "nsg_demo" {
  name                = "nsg-demo"
  location            = azurerm_resource_group.rg_demo.location
  resource_group_name = azurerm_resource_group.rg_demo.name

  # conexión remota RDP
  security_rule {
    name                       = "RDP"
    priority                   = 1000
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "*"
    source_port_range          = "*"
    destination_port_range     = "3389"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }
  
  security_rule {
    name                       = "WinRM"
    priority                   = 1001
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "5985"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }
  
  depends_on = [ azurerm_resource_group.rg_demo ]
}

# tarjeta de red
resource "azurerm_network_interface" "nic_demo" {
  name                = "nic-demo"
  location            = azurerm_resource_group.rg_demo.location
  resource_group_name = azurerm_resource_group.rg_demo.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.subnetdemo.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = azurerm_public_ip.public_ip_demo.id
  }

  depends_on = [ azurerm_resource_group.rg_demo, azurerm_subnet.subnetdemo, azurerm_public_ip.public_ip_demo ]
}

# Connect the security group to the network interface
resource "azurerm_network_interface_security_group_association" "sg_nic_demo" {
  network_interface_id      = azurerm_network_interface.nic_demo.id
  network_security_group_id = azurerm_network_security_group.nsg_demo.id
  depends_on = [ azurerm_network_interface.nic_demo , azurerm_network_security_group.nsg_demo ]
}

resource "azurerm_windows_virtual_machine" "vm_demo" {
  name                = "vmdemo"
  resource_group_name = azurerm_resource_group.rg_demo.name
  location            = azurerm_resource_group.rg_demo.location
  size                = "Standard_B2s"
  admin_username      = "adminuser"
  admin_password      = "P@$$w0rd1234!"
  network_interface_ids = [
    azurerm_network_interface.nic_demo.id,
  ]
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }
  source_image_reference {
    publisher = "MicrosoftWindowsServer"
    offer     = "WindowsServer"
    sku       = "2019-Datacenter"
    version   = "latest"
  }

 
  depends_on = [ azurerm_network_interface_security_group_association.sg_nic_demo ]
}
/*
resource "null_resource" "configure" {
    provisioner "remote-exec" {
        inline = [
            "Invoke-WebRequest -Uri 'https://download.microsoft.com/download/7/0/9/709CBE7F-2005-4A83-B405-CA37A9AB8DC8/PowerBIReportServer.exe' -OutFile 'C:\\PowerBIReportServer.exe'",
            "Start-Process -Wait -FilePath 'C:\\PowerBIReportServer.exe' -ArgumentList '/quiet /norestart'",
        ]
        connection {
            type     = "winrm"
            user     = "adminuser"
            password = "P@$$w0rd1234!"
            host     = "${azurerm_windows_virtual_machine.vm_demo.public_ip_address}"
        }
    }
    depends_on = [ azurerm_windows_virtual_machine.vm_demo ]
}
*/

resource "azurerm_mssql_server" "sql_server_demo" {
  name                         = "sql-server-demo-v2"
  resource_group_name          = azurerm_resource_group.rg_demo.name
  location                     = azurerm_resource_group.rg_demo.location
  version                      = "12.0"
  administrator_login          = "sqladmin"
  administrator_login_password = "Password1234!"
}

resource "azurerm_mssql_database" "db_sql_server_demo" {
  name                        = "demo"
  server_id                   = azurerm_mssql_server.sql_server_demo.id
  collation                   = "SQL_Latin1_General_CP1_CI_AS"
  license_type                = "LicenseIncluded"
  #max_size_gb                 = 250
  sku_name                    = "S0"
  depends_on = [ azurerm_mssql_server.sql_server_demo ]
}
