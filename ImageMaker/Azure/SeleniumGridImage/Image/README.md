Esta receta es para poder crear via packer una imagen de seleniumgrid

Verifica correctamente la subscripción en la que necesites la información
```console
az account show
```

muestra el subscription id
```console
az account show --query "{ subscription_id: id }"
```

Muestra el publicador Canonical
```console
az vm image list-publishers -l eastus2 --query "[?starts_with(name, 'Canonical')]"
```
muestra imagenes offers
```console
az vm image list-offers --publisher Canonical --location eastus2 --query "[?starts_with(name, 'Ubuntu')]" --output table
```

lista los skus de ubuntu 18.04-LTS
```console
az vm image list --all --publisher Canonical --location eastus2 --output table --query "[?starts_with(sku,'18.04-LTS')]"
```

usa grupo de recursos destinado para pruebas
```console
az group create -n grparqselenium -l eastus2
```

crea las credenciales con el siguiente comando
```console
az ad sp create-for-rbac --name packerCredential --role Contributor --scopes /subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/grparqselenium --query "{ client_id: appId, client_secret: password, tenant_id: tenant }"
```
mira la salida del comando
```console
{
  "client_id": "8f5122e1-6e0e-4420-9f8e-63282ae5f724",
  "client_secret": "xDM8Q~jwTF5uQ0EeVmO5ee2GwEM9dgXNyRgMYdAH",
  "tenant_id": "ca17d960-f14a-4694-93c3-4dec0a12781a"
}
```

exporta los comandos
```console
export AZ_CLIENT_ID="8f5122e1-6e0e-4420-9f8e-63282ae5f724"
export AZ_CLIENT_SECRET="xDM8Q~jwTF5uQ0EeVmO5ee2GwEM9dgXNyRgMYdAH"
export AZ_TENANT_ID="ca17d960-f14a-4694-93c3-4dec0a12781a"
export AZ_SUBSCRIPTION_ID="702cf96d-4910-446c-b26a-002ca67c2505"
```

valida en environment
```console
% env | grep AZ_                                                  
AZ_CLIENT_ID=3ce63c68-a76a-40b1-833b-cb6388b98a53
AZ_CLIENT_SECRET=BMD8Q~MPodDmD6yRZZIAPgr.TymSGyplCY-iuc1E
AZ_TENANT_ID=ebad9241-2387-4155-8952-c8d8718bf80c
AZ_SUBSCRIPTION_ID=702cf96d-4910-446c-b26a-002ca67c2505
```

genera la image con packer
```console
packer build ubuntu.json
```

output
```console
azure-arm: output will be in this color.

==> azure-arm: Running builder ...
==> azure-arm: Getting tokens using client secret
==> azure-arm: Getting tokens using client secret
    azure-arm: Creating Azure Resource Manager (ARM) client ...
==> azure-arm: Getting source image id for the deployment ...
==> azure-arm:  -> SourceImageName: '/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/providers/Microsoft.Compute/locations/Central US/publishers/Canonical/ArtifactTypes/vmimage/offers/UbuntuServer/skus/16.04-LTS/versions/latest'
==> azure-arm: Creating resource group ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> Location          : 'Central US'
==> azure-arm:  -> Tags              :
==> azure-arm:  ->> task : Image deployment
==> azure-arm:  ->> dept : Engineering
==> azure-arm: Validating deployment template ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> DeploymentName    : 'pkrdp80ze00dvjo'
==> azure-arm: Deploying deployment template ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> DeploymentName    : 'pkrdp80ze00dvjo'
==> azure-arm: Getting the VM's IP address ...
==> azure-arm:  -> ResourceGroupName   : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> PublicIPAddressName : 'pkrip80ze00dvjo'
==> azure-arm:  -> NicName             : 'pkrni80ze00dvjo'
==> azure-arm:  -> Network Connection  : 'PublicEndpoint'
==> azure-arm:  -> IP Address          : '104.43.193.157'
==> azure-arm: Waiting for SSH to become available...
==> azure-arm: Connected to SSH!
==> azure-arm: Provisioning with shell script: /var/folders/5j/yg6bb5_n38dc94vj30qh82vm0000gn/T/packer-shell4349642
    azure-arm: Hit:1 http://archive.ubuntu.com/ubuntu xenial InRelease
    azure-arm: Get:2 http://security.ubuntu.com/ubuntu xenial-security InRelease [99.8 kB]
    azure-arm: Get:3 http://archive.ubuntu.com/ubuntu xenial-updates InRelease [99.8 kB]
    azure-arm: Get:4 http://archive.ubuntu.com/ubuntu xenial-backports InRelease [97.4 kB]
    azure-arm: Get:5 https://esm.ubuntu.com/infra/ubuntu xenial-infra-security InRelease [7524 B]
    azure-arm: Get:6 https://esm.ubuntu.com/infra/ubuntu xenial-infra-updates InRelease [7475 B]
    azure-arm: Get:7 http://archive.ubuntu.com/ubuntu xenial/universe amd64 Packages [7532 kB]
    azure-arm: Get:8 http://security.ubuntu.com/ubuntu xenial-security/main Translation-en [360 kB]
    azure-arm: Get:9 http://security.ubuntu.com/ubuntu xenial-security/universe amd64 Packages [786 kB]
    azure-arm: Get:10 http://security.ubuntu.com/ubuntu xenial-security/universe Translation-en [226 kB]
    azure-arm: Get:11 http://security.ubuntu.com/ubuntu xenial-security/multiverse amd64 Packages [7864 B]
    azure-arm: Get:12 http://security.ubuntu.com/ubuntu xenial-security/multiverse Translation-en [2672 B]
    azure-arm: Get:13 https://esm.ubuntu.com/infra/ubuntu xenial-infra-security/main amd64 Packages [686 kB]
    azure-arm: Get:14 http://archive.ubuntu.com/ubuntu xenial/universe Translation-en [4354 kB]
    azure-arm: Get:15 http://archive.ubuntu.com/ubuntu xenial/multiverse amd64 Packages [144 kB]
    azure-arm: Get:16 http://archive.ubuntu.com/ubuntu xenial/multiverse Translation-en [106 kB]
    azure-arm: Get:17 http://archive.ubuntu.com/ubuntu xenial-updates/main amd64 Packages [2049 kB]
    azure-arm: Get:18 http://archive.ubuntu.com/ubuntu xenial-updates/main Translation-en [461 kB]
    azure-arm: Get:19 http://archive.ubuntu.com/ubuntu xenial-updates/universe amd64 Packages [1220 kB]
    azure-arm: Get:20 http://archive.ubuntu.com/ubuntu xenial-updates/universe Translation-en [359 kB]
    azure-arm: Get:21 http://archive.ubuntu.com/ubuntu xenial-updates/multiverse amd64 Packages [22.6 kB]
    azure-arm: Get:22 http://archive.ubuntu.com/ubuntu xenial-updates/multiverse Translation-en [8476 B]
    azure-arm: Get:23 http://archive.ubuntu.com/ubuntu xenial-backports/main amd64 Packages [9812 B]
    azure-arm: Get:24 http://archive.ubuntu.com/ubuntu xenial-backports/main Translation-en [4456 B]
    azure-arm: Get:25 http://archive.ubuntu.com/ubuntu xenial-backports/universe amd64 Packages [11.3 kB]
    azure-arm: Get:26 http://archive.ubuntu.com/ubuntu xenial-backports/universe Translation-en [4476 B]
    azure-arm: Fetched 18.7 MB in 41s (449 kB/s)
    azure-arm: Reading package lists...
    azure-arm: Reading package lists...
    azure-arm: Building dependency tree...
    azure-arm: Reading state information...
    azure-arm: Calculating upgrade...
    azure-arm: The following package was automatically installed and is no longer required:
    azure-arm:   grub-pc-bin
    azure-arm: Use 'sudo apt autoremove' to remove it.
    azure-arm: 0 upgraded, 0 newly installed, 0 to remove and 0 not upgraded.
    azure-arm: Reading package lists...
    azure-arm: Building dependency tree...
    azure-arm: Reading state information...
    azure-arm: The following package was automatically installed and is no longer required:
    azure-arm:   grub-pc-bin
    azure-arm: Use 'sudo apt autoremove' to remove it.
    azure-arm: The following additional packages will be installed:
    azure-arm:   nginx-common nginx-light
    azure-arm: Suggested packages:
    azure-arm:   fcgiwrap nginx-doc ssl-cert
    azure-arm: The following NEW packages will be installed:
    azure-arm:   nginx nginx-common nginx-light
    azure-arm: 0 upgraded, 3 newly installed, 0 to remove and 0 not upgraded.
    azure-arm: Need to get 345 kB of archives.
    azure-arm: After this operation, 1098 kB of additional disk space will be used.
    azure-arm: Get:1 http://security.ubuntu.com/ubuntu xenial-security/main amd64 nginx-common all 1.10.3-0ubuntu0.16.04.5 [26.9 kB]
    azure-arm: Get:2 http://security.ubuntu.com/ubuntu xenial-security/universe amd64 nginx-light amd64 1.10.3-0ubuntu0.16.04.5 [315 kB]
    azure-arm: Get:3 http://security.ubuntu.com/ubuntu xenial-security/main amd64 nginx all 1.10.3-0ubuntu0.16.04.5 [3494 B]
==> azure-arm: debconf: unable to initialize frontend: Dialog
==> azure-arm: debconf: (Dialog frontend will not work on a dumb terminal, an emacs shell buffer, or without a controlling terminal.)
==> azure-arm: debconf: falling back to frontend: Readline
==> azure-arm: debconf: unable to initialize frontend: Readline
==> azure-arm: debconf: (This frontend requires a controlling tty.)
==> azure-arm: debconf: falling back to frontend: Teletype
==> azure-arm: dpkg-preconfigure: unable to re-open stdin:
    azure-arm: Fetched 345 kB in 0s (926 kB/s)
    azure-arm: Selecting previously unselected package nginx-common.
    azure-arm: (Reading database ... 54185 files and directories currently installed.)
    azure-arm: Preparing to unpack .../nginx-common_1.10.3-0ubuntu0.16.04.5_all.deb ...
    azure-arm: Unpacking nginx-common (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: Selecting previously unselected package nginx-light.
    azure-arm: Preparing to unpack .../nginx-light_1.10.3-0ubuntu0.16.04.5_amd64.deb ...
    azure-arm: Unpacking nginx-light (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: Selecting previously unselected package nginx.
    azure-arm: Preparing to unpack .../nginx_1.10.3-0ubuntu0.16.04.5_all.deb ...
    azure-arm: Unpacking nginx (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: Processing triggers for ureadahead (0.100.0-19.1) ...
    azure-arm: Processing triggers for systemd (229-4ubuntu21.31) ...
    azure-arm: Processing triggers for ufw (0.35-0ubuntu2) ...
    azure-arm: Processing triggers for man-db (2.7.5-1) ...
    azure-arm: Setting up nginx-common (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: debconf: unable to initialize frontend: Dialog
    azure-arm: debconf: (Dialog frontend will not work on a dumb terminal, an emacs shell buffer, or without a controlling terminal.)
    azure-arm: debconf: falling back to frontend: Readline
    azure-arm: Setting up nginx-light (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: Setting up nginx (1.10.3-0ubuntu0.16.04.5) ...
    azure-arm: Processing triggers for ureadahead (0.100.0-19.1) ...
    azure-arm: Processing triggers for systemd (229-4ubuntu21.31) ...
    azure-arm: Processing triggers for ufw (0.35-0ubuntu2) ...
    azure-arm: WARNING! The waagent service will be stopped.
    azure-arm: WARNING! Cached DHCP leases will be deleted.
    azure-arm: WARNING! root password will be disabled. You will not be able to login as root.
    azure-arm: WARNING! /etc/resolvconf/resolv.conf.d/tail and /etc/resolvconf/resolv.conf.d/original will be deleted.
    azure-arm: WARNING! packer account and entire home directory will be deleted.
==> azure-arm: Querying the machine's properties ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> ComputeName       : 'pkrvm80ze00dvjo'
==> azure-arm:  -> Managed OS Disk   : '/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/pkr-Resource-Group-80ze00dvjo/providers/Microsoft.Compute/disks/pkros80ze00dvjo'
==> azure-arm: Querying the machine's additional disks properties ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> ComputeName       : 'pkrvm80ze00dvjo'
==> azure-arm: Powering off machine ...
==> azure-arm:  -> ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> ComputeName       : 'pkrvm80ze00dvjo'
==> azure-arm: Generalizing machine ...
==> azure-arm:  -> Compute ResourceGroupName : 'pkr-Resource-Group-80ze00dvjo'
==> azure-arm:  -> Compute Name              : 'pkrvm80ze00dvjo'
==> azure-arm:  -> Compute Location          : 'Central US'
==> azure-arm: Capturing image ...
==> azure-arm:  -> Image ResourceGroupName   : 'prueba-cesar-ogalde-vm'
==> azure-arm:  -> Image Name                : 'ubuntuNginxImage'
==> azure-arm:  -> Image Location            : 'Central US'
==> azure-arm: 
==> azure-arm: Deleting individual resources ...
==> azure-arm: Adding to deletion queue -> Microsoft.Compute/virtualMachines : 'pkrvm80ze00dvjo'
==> azure-arm: Adding to deletion queue -> Microsoft.Network/networkInterfaces : 'pkrni80ze00dvjo'
==> azure-arm: Adding to deletion queue -> Microsoft.Network/publicIPAddresses : 'pkrip80ze00dvjo'
==> azure-arm: Adding to deletion queue -> Microsoft.Network/virtualNetworks : 'pkrvn80ze00dvjo'
==> azure-arm: Attempting deletion -> Microsoft.Network/virtualNetworks : 'pkrvn80ze00dvjo'
==> azure-arm: Waiting for deletion of all resources...
==> azure-arm: Attempting deletion -> Microsoft.Compute/virtualMachines : 'pkrvm80ze00dvjo'
==> azure-arm: Attempting deletion -> Microsoft.Network/networkInterfaces : 'pkrni80ze00dvjo'
==> azure-arm: Attempting deletion -> Microsoft.Network/publicIPAddresses : 'pkrip80ze00dvjo'
==> azure-arm:  Deleting -> Microsoft.Compute/disks : '/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/pkr-Resource-Group-80ze00dvjo/providers/Microsoft.Compute/disks/pkros80ze00dvjo'
==> azure-arm: Removing the created Deployment object: 'pkrdp80ze00dvjo'
==> azure-arm: 
==> azure-arm: Cleanup requested, deleting resource group ...
==> azure-arm: Resource group has been deleted.
Build 'azure-arm' finished after 6 minutes 13 seconds.

==> Wait completed after 6 minutes 13 seconds

==> Builds finished. The artifacts of successful builds are:
--> azure-arm: Azure.ResourceManagement.VMImage:

OSType: Linux
ManagedImageResourceGroupName: prueba-cesar-ogalde-vm
ManagedImageName: ubuntuNginxImage
ManagedImageId: /subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Compute/images/ubuntuNginxImage
ManagedImageLocation: Central US
```


Ahora crea la imagen en azure
```console
az group create -n prueba-cesar-ogalde-vm -l centralus
```
output
```console
{
  "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm",
  "location": "centralus",
  "managedBy": null,
  "name": "prueba-cesar-ogalde-vm",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null,
  "type": "Microsoft.Resources/resourceGroups"
}
```


```console
az vm create  --resource-group prueba-cesar-ogalde-vm --name vmFromImageUbuntuNginx --image /subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Compute/images/ubuntuNginxImage --admin-username azureuser --generate-ssh-keys
```

output
```console
It is recommended to use parameter "--public-ip-sku Standard" to create new VM with Standard public IP. Please note that the default public IP used for VM creation will be changed from Basic to Standard in the future.
{
  "fqdns": "",
  "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Compute/virtualMachines/vmFromImageUbuntuNginx",
  "location": "centralus",
  "macAddress": "60-45-BD-32-2B-A7",
  "powerState": "VM running",
  "privateIpAddress": "10.0.0.4",
  "publicIpAddress": "40.113.224.241",
  "resourceGroup": "prueba-cesar-ogalde-vm",
  "zones": ""
}
```

Ahora abre el puerto 80 para ver NGINX
```console
az vm open-port --resource-group prueba-cesar-ogalde-vm --name vmFromImageUbuntuNginx --port 80
```
output
```console
{
  "defaultSecurityRules": [
    {
      "access": "Allow",
      "description": "Allow inbound traffic from all VMs in VNET",
      "destinationAddressPrefix": "VirtualNetwork",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Inbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/AllowVnetInBound",
      "name": "AllowVnetInBound",
      "priority": 65000,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "VirtualNetwork",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    },
    {
      "access": "Allow",
      "description": "Allow inbound traffic from azure load balancer",
      "destinationAddressPrefix": "*",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Inbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/AllowAzureLoadBalancerInBound",
      "name": "AllowAzureLoadBalancerInBound",
      "priority": 65001,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "AzureLoadBalancer",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    },
    {
      "access": "Deny",
      "description": "Deny all inbound traffic",
      "destinationAddressPrefix": "*",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Inbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/DenyAllInBound",
      "name": "DenyAllInBound",
      "priority": 65500,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "*",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    },
    {
      "access": "Allow",
      "description": "Allow outbound traffic from all VMs to all VMs in VNET",
      "destinationAddressPrefix": "VirtualNetwork",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Outbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/AllowVnetOutBound",
      "name": "AllowVnetOutBound",
      "priority": 65000,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "VirtualNetwork",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    },
    {
      "access": "Allow",
      "description": "Allow outbound traffic from all VMs to Internet",
      "destinationAddressPrefix": "Internet",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Outbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/AllowInternetOutBound",
      "name": "AllowInternetOutBound",
      "priority": 65001,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "*",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    },
    {
      "access": "Deny",
      "description": "Deny all outbound traffic",
      "destinationAddressPrefix": "*",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "*",
      "destinationPortRanges": [],
      "direction": "Outbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/defaultSecurityRules/DenyAllOutBound",
      "name": "DenyAllOutBound",
      "priority": 65500,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "*",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/defaultSecurityRules"
    }
  ],
  "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
  "flowLogs": null,
  "flushConnection": null,
  "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG",
  "location": "centralus",
  "name": "vmFromImageUbuntuNginxNSG",
  "networkInterfaces": [
    {
      "auxiliaryMode": null,
      "dnsSettings": null,
      "dscpConfiguration": null,
      "enableAcceleratedNetworking": null,
      "enableIpForwarding": null,
      "etag": null,
      "extendedLocation": null,
      "hostedWorkloads": null,
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkInterfaces/vmFromImageUbuntuNginxVMNic",
      "ipConfigurations": null,
      "location": null,
      "macAddress": null,
      "migrationPhase": null,
      "name": null,
      "networkSecurityGroup": null,
      "nicType": null,
      "primary": null,
      "privateEndpoint": null,
      "privateLinkService": null,
      "provisioningState": null,
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "resourceGuid": null,
      "tags": null,
      "tapConfigurations": null,
      "type": null,
      "virtualMachine": null,
      "vnetEncryptionSupported": null,
      "workloadType": null
    }
  ],
  "provisioningState": "Succeeded",
  "resourceGroup": "prueba-cesar-ogalde-vm",
  "resourceGuid": "effb9414-3909-4a15-96a1-95dd577ffe8c",
  "securityRules": [
    {
      "access": "Allow",
      "description": null,
      "destinationAddressPrefix": "*",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "22",
      "destinationPortRanges": [],
      "direction": "Inbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/securityRules/default-allow-ssh",
      "name": "default-allow-ssh",
      "priority": 1000,
      "protocol": "Tcp",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "*",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/securityRules"
    },
    {
      "access": "Allow",
      "description": null,
      "destinationAddressPrefix": "*",
      "destinationAddressPrefixes": [],
      "destinationApplicationSecurityGroups": null,
      "destinationPortRange": "80",
      "destinationPortRanges": [],
      "direction": "Inbound",
      "etag": "W/\"0c2da528-783c-4ca0-ab82-3b85324fd505\"",
      "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/prueba-cesar-ogalde-vm/providers/Microsoft.Network/networkSecurityGroups/vmFromImageUbuntuNginxNSG/securityRules/open-port-80",
      "name": "open-port-80",
      "priority": 900,
      "protocol": "*",
      "provisioningState": "Succeeded",
      "resourceGroup": "prueba-cesar-ogalde-vm",
      "sourceAddressPrefix": "*",
      "sourceAddressPrefixes": [],
      "sourceApplicationSecurityGroups": null,
      "sourcePortRange": "*",
      "sourcePortRanges": [],
      "type": "Microsoft.Network/networkSecurityGroups/securityRules"
    }
  ],
  "subnets": null,
  "tags": {},
  "type": "Microsoft.Network/networkSecurityGroups"
}
```

Prueba del servicio NGINX
```console
curl http://40.113.224.241/ | grep h1
```
output
```console
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   612  100   612    0     0   1540      0 --:--:-- --:--:-- --:--:--  1565
<h1>Welcome to nginx!</h1>
```

Elimina el recurso
```console
az group delete --name prueba-cesar-ogalde-vm
```