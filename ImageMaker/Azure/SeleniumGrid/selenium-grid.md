uscripción  Imagemaker
Grupo de recursos arq-dev-cicd-test
Región East US 2
Location eastus2
Network Interface Name arq-dev-cicd-test-server-ni
Enable Accelerated Networking true
Network Security Group Name arq-dev-cicd-test-server-nsg
Network Security Group Rules
[{"name":"SSH","properties":{"priority":300,"protocol":"TCP","access":"Allow","direction":"Inbound","sourceAddressPrefix":"*","sourcePortRange":"*","destinationAddressPrefix":"*","destinationPortRange":"22"}},{"name":"HTTPS","properties":{"priority":320,"protocol":"TCP","access":"Allow","direction":"Inbound","sourceAddressPrefix":"*","sourcePortRange":"*","destinationAddressPrefix":"*","destinationPortRange":"443"}},{"name":"HTTP","properties":{"priority":340,"protocol":"TCP","access":"Allow","direction":"Inbound","sourceAddressPrefix":"*","sourcePortRange":"*","destinationAddressPrefix":"*","destinationPortRange":"80"}}]
Subnet Name arq-dev-cicd-test-subnet
Virtual Network Name arq-dev-cicd-test-vnet
Address Prefixes ["172.18.0.0/16"]
Subnets [{"name":"arq-dev-cicd-test-subnet","properties":{"addressPrefix":"172.18.0.0/24"}}]
Public Ip Address Name arq-dev-cicd-test-server-ip
Public Ip Address Type Static
Public Ip Address Sku Standard
Pip Delete Option Delete
Virtual Machine Name arq-dev-cicd-test-server
Virtual Machine Computer Name arq-dev-cicd-test-server
Virtual Machine RG arq-dev-cicd-test
Os Disk Type Premium_LRS
Os Disk Delete Option Delete
Data Disks
[{"lun":0,"createOption":"attach","deleteOption":"Delete","caching":"ReadOnly","writeAcceleratorEnabled":false,"id":null,"name":"arq-dev-cicd-test-server_DataDisk_0","storageAccountType":null,"diskSizeGB":null,"diskEncryptionSet":null}]
Data Disk Resources
[{"name":"arq-dev-cicd-test-server_DataDisk_0","sku":"Premium_LRS","properties":{"diskSizeGB":32,"creationData":{"createOption":"empty"}}}]
Virtual Machine Size Standard_D2s_v3
Nic Delete Option Delete
Admin Username cogalde