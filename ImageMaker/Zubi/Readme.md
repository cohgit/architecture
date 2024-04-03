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

## TODO
1. Configurar Mails : https://docs.gitlab.com/omnibus/settings/smtp

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
MongoDemo
cesarogalde : n7x3R2odgGheqEDA

mongodb+srv://cesarogalde:n7x3R2odgGheqEDA@clusterdemo.l0idfne.mongodb.net/?retryWrites=true&w=majority&appName=ClusterDemo


## Deploy Image

https://help.ovhcloud.com/csm/en-ie-public-cloud-kubernetes-deploy-application?id=kb_article_view&sysparm_article=KB0049712

