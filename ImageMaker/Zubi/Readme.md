Acceso a OVH

https://www.ovh.com/manager/#/hub
3151-0720-06/Cesar
Greenest.2024


Energy Panel OV2 Ambientes Dev y Prod en la nube OVH Cloud

GitHub Server
Github Repos Front y Back
Base de Datos Mysql con backup
Front con escalabilidad horizontal automágtica
Back con escalabilidad horizontal automática
VPN / Mecanismo de acceso  para los desarrolladores a los servicios OVH
Balanceo para servicios escalados (Back y Front)
Crear Server Ubuntu con GitHub

## Crear Red General / Front / Back
Localizacion : Gravelines GRA11

## Instalacion GitLab Server
requerimientos https://docs.gitlab.com/ee/install/requirements.html
instalacion https://about.gitlab.com/install/#ubuntu

1. Crear Maquina d2-8-gra11-epv2-gitlab RAM 8 GB 4 vCores ( Discovery : d2-8 / Location : Gravelines GRA11 )
1. Crear Red
1. Crear Key : https://help.ovhcloud.com/csm/en-dedicated-servers-ssh-introduction?id=kb_article_view&sysparm_article=KB0044021
1. Asignar IP Floating
1. Configurar dominio gitlab 141.94.173.137 en https://my.noip.com/dynamic-dns

## LLave creada
La llave creada se configura en la consola ovh con el nombre epv2-key-gitlab

### Pub
```console
% cat id_ecdsa_epv2.pub 
ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBB9yFLuo1t6uv+HhaYmYOsSOqLRR96XpkWUwEv0+PzWhszGh+c/lTXMwcFdymalkHktUXPj7Gg9LFiXIU4DHtVc= imagemaker@IMCL-BULVCH.local
```
### Private
```console
% cat id_ecdsa_epv2
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAaAAAABNlY2RzYS
1zaGEyLW5pc3RwMjU2AAAACG5pc3RwMjU2AAAAQQQfchS7qNberr/h4WmJmDrEjqi0Ufel
6ZFlMBL9Pj81obMxofnP5U1zMHBXcpmpZB5LVFz4+xoPSxYlyFOAx7VXAAAAuHUDmyR1A5
skAAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBB9yFLuo1t6uv+Hh
aYmYOsSOqLRR96XpkWUwEv0+PzWhszGh+c/lTXMwcFdymalkHktUXPj7Gg9LFiXIU4DHtV
cAAAAgHxINOm9sQUOtpldJmZetWjgaDzvvE96hXjtZUAZHE7oAAAAcaW1hZ2VtYWtlckBJ
TUNMLUJVTFZDSC5sb2NhbAECAwQ=
-----END OPENSSH PRIVATE KEY-----
```

## conectarse a servidor
```console
% ssh -i id_ecdsa_epv2 ubuntu@epv2gitlab.ddns.net
```

## Script Instalacion Gitlab
```console
$sudo apt-get update
$sudo apt-get install -y curl openssh-server ca-certificates tzdata perl
$curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.deb.sh | sudo bash
$sudo EXTERNAL_URL="https://epv2gitlab.ddns.net" apt-get install gitlab-ee
$sudo cat /etc/gitlab/initial_root_password
```

## WARNING: This value is valid only in the following conditions
#          1. If provided manually (either via `GITLAB_ROOT_PASSWORD` environment variable or via `gitlab_rails['initial_root_password']` setting in `gitlab.rb`, it was provided before database was seeded for the first time (usually, the first reconfigure run).
#          2. Password hasn't been changed manually, either via UI or via command line.
#
#          If the password shown here doesn't work, you must reset the admin password following https://docs.gitlab.com/ee/security/reset_user_password.html#reset-your-root-password.
Password: dAszKsvX3x9vjdhfDxZXv8AsOTcp7KpqC52zBQvgQtU=
# NOTE: This file will be automatically deleted in the first reconfigure run after 24 hours.


## Servicio Gitlab
URL : https://epv2gitlab.ddns.net/
Login : root 
Password: dAszKsvX3x9vjdhfDxZXv8AsOTcp7KpqC52zBQvgQtU=

## Cambio de DNS

```console
% sudo vi /etc/gitlab/gitlab.rb
```
Cambia la variable external_url con la nueva url

external_url 'https://repositorio.greenest.app'

reconfigura gitlab 

```console
%  sudo gitlab-ctl reconfigure
```
## Registro a ZeroTier

referencia https://www.zerotier.com/download/ parte "Linux (DEB/RPM)"

```console
$ curl -s https://install.zerotier.com | sudo bash
```

registro a la red "e3918db48393fa2e" de Zewro Tier provista por Alfredo Ayala (alfredo@feelingdevs.com)
```console
¢ sudo zerotier-cli join e3918db48393fa2e
```


## Configuracion de correo en gitlab
1. Configurar Mails : https://docs.gitlab.com/omnibus/settings/smtp

## Instalación de Kubernete FRONT
crear un servicio de contenedores administrado
Localizacion : Gravelines GRA11
Versión: 1.29.3-0
Red : 1702- epv2-red-front
Puerta de Enlace : On - IP en blanco para colocar por default
Tipo de Nodo : Discovery D2-4 4GB RAM / 2VCores / 250mbs
Nombre : d2-4-epv2-kubernetes-front


## Instalación de Kubernete BACK
crear un servicio de contenedores administrado
Localizacion : Gravelines GRA11
Versión: 1.29.3-0
Red : 1702- epv2-red-back
Puerta de Enlace : On - IP en blanco para colocar por default
Tipo de Nodo : Discovery D2-4 4GB RAM / 2VCores / 250mbs
Nombre : d2-4-epv2-kubernetes-back

## Registro de imágenes
Nombre: epv2-registry
Region: GRA
Plan : S
Versión Harbor : 2.6.4

## Aplicacion con Docker
https://help.ovhcloud.com/csm/en-public-cloud-private-registry-create-private-image?id=kb_article_view&sysparm_article=KB0050342

## Conectando a UI Harbor
https://help.ovhcloud.com/csm/en-public-cloud-private-registry-connect-to-ui?id=kb_article_view&sysparm_article=KB0050329

## Generar Claves de Private Registry
URL : https://vjv08g10.c1.gra9.container-registry.ovh.net/harbor/projects
Username : xIdDgmuqsB 
Password : 0P9w27g135V6xi48

## Crear Proyecto en Registry
epv2

## Push Imagen
code
```console
$mkdir hello-ovh
$cd hello-ovh
$vi Dockerfile
$vi index.html
$docker build --tag vjv08g10.c1.gra9.container-registry.ovh.net/epv2/hello-ovh:1.0.0 .
$docker login vjv08g10.c1.gra9.container-registry.ovh.net
$docker push vjv08g10.c1.gra9.container-registry.ovh.net/epv2/hello-ovh:1.0.0
```

## Deploy Image

https://help.ovhcloud.com/csm/en-ie-public-cloud-kubernetes-deploy-application?id=kb_article_view&sysparm_article=KB0049712


## crea usuario admin
user-pP2ab4RUw9xu 
AmBrgdMzqsmmrBrg9Vfy86TnCYSUSq99.

## Deploy to kubernetes
https://help.ovhcloud.com/csm?id=kb_article_view&sysparm_article=KB0050386


```console
% kubectl create secret docker-registry regcred --docker-server=vjv08g10.c1.gra9.container-registry.ovh.net --docker-username=xIdDgmuqsB --docker-password=0P9w27g135V6xi48
% kubectl get secret regcred -o jsonpath="{.data.\.dockerconfigjson}"
% kubectl apply -f hello.yml
% kubectl get pods -n default -l app=hello-ovh
% kubectl get services -n default -l app=hello-ovh
```


## Creación de proyecto en servidor gitlab
1. creación de usuario cesar.ogalde@imagemaker.com
1. creacion de proyecto hello-ovh
1. creación de git runner 

## creacion de gitlab runner
 Crear gitlab-runner en : https://repositorio.greenest.app/admin/runners

```console
% gitlab-runner register  --url https://repositorio.greenest.app  --token glrt-rYBxtCRqzr172niDqG_5
```

## agregar en configuración de gitlab-runner la configuración del host de docker

agrega al archivo config.toml la configuración de 

```console
$sudo cat /etc/gitlab-runner/config.toml
[[runners]]
  name = "d2-8-gra11"
  url = "https://repositorio.greenest.app"
  id = 3
  token = "glrt-nC42oMEy48hRe3SFme4G"
  token_obtained_at = 2024-04-07T16:12:47Z
  token_expires_at = 0001-01-01T00:00:00Z
  executor = "docker"
  [runners.docker]
    tls_verify = false
    image = "ruby:2.7"
    privileged = false
    disable_entrypoint_overwrite = false
    oom_kill_disable = false
    disable_cache = false
    volumes = ["/var/run/docker.sock:/var/run/docker.sock","/cache"]
    shm_size = 0
    network_mtu = 0
```


agrega docker al server
   69  echo "Instaklación Docker Para Gitlab runner"
   70  sudo apt-get update
   71  sudo apt-get install ca-certificates curl
   72  sudo install -m 0755 -d /etc/apt/keyrings
   73  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
   74  sudo chmod a+r /etc/apt/keyrings/docker.asc
   75  echo   "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" |   sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   76  sudo apt-get update
   77  sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
   78  sudo docker run hello-world

Permisos de ejecuccion de docker para ubuntu y gitlab-runner

   sudo usermod -aG docker gitlab-runner
   sudo usermod -aG docker ubuntu (reiniciar)

## Acceso a Base de Datos
172.24.99.26 DevFrontSaaSWeb
172.24.99.34 DevFrontAPI_NEW

## creación de usuario base de datos desarrollo
cesar.ogalde / UBf8svg0b26tno4VDZdW

## Conectar a base de datos desde servidor VPN por linea de comando
Copiar Certificado de acceso en Home
```console
$scp -i epv2-key-vpn crt-db-mysql-dev.pem ubuntu@141.95.166.176:~
```
Conectarse al servidor VPN
```console
$ ssh -i epv2-key-vpn ubuntu@141.95.166.176
```
Instalar cliente Mysql para pruebas de comunicación
```console
$ sudo apt-get install mysql-client
$ mysql --version
```
Obtener IP pública de VPN para autorización en OVH
```console
$ curl ifconfig.me
```
Autorizar IP en OVH

https://www.ovh.com/manager/#/public-cloud/pci/projects/e483a249d934437e8646532df9209249/storages/databases-analytics/databases/e0770d11-0533-4df1-8b6e-9d72838dc906/allowed-ips

IP Máscara : 141.95.166.176/32
Descripción : EPV2 VPN Server Access IP

Conectar a BDD via linux
```console
$ mysql -u cesar.ogalde -p -P 20184 -h mysql-e0770d11-o5945d2ef.database.cloud.ovh.net --ssl-mode=VERIFY_CA --ssl_ca=crt-db-mysql-dev.pem
```

## Conectar a base de datos desde servidor VPN por DBeaver
URL : jdbc:mysql://localhost:3306/defaultdb?ssl-mode=REQUIRED
Usuario : cesar.ogalde 
Password : UBf8svg0b26tno4VDZdW
Certificado : crt-db-mysql-dev.pem
SSH Tunel
   IP Pùblica VPN : 141.95.166.176 / IP Zerotier ¿?
   Usuario : ubuntu
   Private Key : epv2-key-vpn
   LocalHost : localhost (colocar en url)
   LocalPort : 3306 (colocar en url)
   RemoteHost : mysql-e0770d11-o5945d2ef.database.cloud.ovh.net
   RemotePort : 20184

Test Conection OK!


## Deploy From Helm

Install Helm en local
https://help.ovhcloud.com/csm/en-ie-public-cloud-private-registry-deploy-chart-from-kubernetes-registry?id=kb_article_view&sysparm_article=KB0050352

Credenciales Registry
Username : xIdDgmuqsB 
Password : 0P9w27g135V6xi48

## Instalación vía helm
```console
% helm registry login https://vjv08g10.c1.gra9.container-registry.ovh.net
% helm install myrelease oci://vjv08g10.c1.gra9.container-registry.ovh.net/epv2/hello-ovh --version 1.0.0

```

MongoDemo
cesarogalde : n7x3R2odgGheqEDA

mongodb+srv://cesarogalde:n7x3R2odgGheqEDA@clusterdemo.l0idfne.mongodb.net/?retryWrites=true&w=majority&appName=ClusterDemo