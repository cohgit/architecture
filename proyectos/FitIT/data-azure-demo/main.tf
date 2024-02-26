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
  service_endpoints = ["Microsoft.Sql"]
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

  security_rule {
    name                       = "HTTP"
    priority                   = 1002
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "80"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }
   security_rule {
    name                       = "SQLServer"
    priority                   = 1003
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "1433"
    source_address_prefix      = "10.0.2.0/32"
    destination_address_prefix = "${azurerm_virtual_network.net_demo.address_space.0}"
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
  size                = "Standard_B4ms"
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

# tarjeta de red
resource "azurerm_network_interface" "nic_sql_demo" {
  name                = "nic-sql-demo"
  location            = azurerm_resource_group.rg_demo.location
  resource_group_name = azurerm_resource_group.rg_demo.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.subnetdemo.id
    private_ip_address_allocation = "Dynamic"
  }

  depends_on = [ azurerm_resource_group.rg_demo, azurerm_subnet.subnetdemo ]
}

resource "azurerm_windows_virtual_machine" "vm_sql_demo" {
  name                = "vmsqldemo"
  resource_group_name = azurerm_resource_group.rg_demo.name
  location            = azurerm_resource_group.rg_demo.location
  size                = "Standard_B2s"
  admin_username      = "sqluser"
  admin_password      = "P@$$w0rd1234!"
  network_interface_ids = [
    azurerm_network_interface.nic_sql_demo.id,
  ]
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }
  source_image_reference {
    publisher = "MicrosoftSQLServer"
    offer     = "sql2019-ws2019"
    sku       = "SQLDEV-gen2"
    version   = "latest"
}
  depends_on = [ azurerm_network_interface_security_group_association.sg_nic_demo ]
}

