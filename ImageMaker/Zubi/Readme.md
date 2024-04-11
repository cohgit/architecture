# Servicio de Creación de Infraestructura Cloud para Zubi Group

La grupo empresarial ZubiGroup nos ha encomendado la misión de crear la infraestructura en su nube demnominada OVH Cloud para aplicación Energy Panel.

## Entrega de Requerimientos

Se solicita la creación de infraestructura para el sistema Energy Panel para ambientes de desarrollo y producción. una arquitectura escalable que permita la demanda.

### Requerimientos

A continuación se describen los componentes que se deben considerar.

1. Servidor de Repositorio Git para proyectos Front y Back, en tecnología Gitlab.
1. Base de Datos MySQL con Backup, se configurará un servidor MySQL para almacenar los datos de la aplicación, con un sistema de respaldo automatizado para garantizar la integridad de los datos.
1. Frontend con Escalabilidad Horizontal Automática según carga, se implementará el Frontend en un entorno que permita la escalabilidad horizontal automática, utilizando tecnologías como contenedores Docker y orquestación de contenedores con Kubernetes.
1. Backend con Escalabilidad Horizontal Automática según carga, se implementará el Backend en un entorno similar al Frontend, con capacidades de escalabilidad horizontal automática para manejar cargas de trabajo variables.
1. VPN / Mecanismo de Acceso para Desarrolladores a los Servicios OVH, se establecerá una VPN u otro sewvicio provisto por OVH para permitir el acceso seguro de los desarrolladores a los servicios alojados en OVH. Se implementarán medidas de seguridad necesarias.
1. Balanceo para Servicios Escalados (Backend y Frontend), se configurará un sistema de balanceo de carga para distribuir el tráfico entre las instancias escaladas tanto del Backend como del Frontend, utilizando soluciones provista por el proveedor cloud u otras.
1. Configuración de Dominios, se configurarán los dominios correspondientes para apuntar a los ambientes, asegurando la disponibilidad y accesibilidad de la aplicación.


### Insumos 

Zubi hace entrega de credenciales para la nube OVH Cloud con el objetivo de crear los componentes.

- URL: https://www.ovh.com/manager/#/hub
- Username : 3151-0720-06/Cesar
- Password : Greenest.2024

Se evalúa terraform para la creación de componentes sin embargo no está muy avanzado y tiene una mezcla entre openstack, aws y ovh por tanto sen crearán los componentes a través de la interfaz web.

En caso de requerir se puede ver la referencia : [public-cloud-compute-terraform](https://help.ovhcloud.com/csm/en-public-cloud-compute-terraform?id=kb_article_view&sysparm_article=KB0050797)

## Diseño de Arquitectura

La definción de arquitectura responde principalmente a una arquitectura escalable y resilente a la demanda esperada.

Un aspecto importante es actualizar arquitectura monolitica a una de microservicios.

Acceso seguro con certificados y también con pivote sobre todo por la base de datos.

![alt](assets/IM-Zubi-Energy%20Panel%20V2.png)

### Algunos Componentes

1. Repositorio de fuentes.
1. Acceso Seguro.
1. Servios Contenerizados escalables.
1. Registro de Imagenes.
1. Base de Datos.
1. Nube OVH.

## Creación de infraestructura Energy Panel 

Se identifica que es la 2da versión por tanto los recursos usarán un nombrado con prefijo epv2 que significa Energy Panel v2.

### Crear Red General / Front / Back para Dev y Producción
Se define una localización para las redes, esto es en Gravelines (GRA11) con servicio gateway activando DHCP.
Localizacion : Gravelines GRA11

la lista de redes son :
1. VLan id 1701 con nombre "epv2-red-general".
   red destinada a los componentes transversales como VPNsss, y servidor de repositorio
1. VLan id 1702 con nombre "epv2-red-front".
   red destinada a los componentes dedicados aislados del front ambiente de desarrollo
1. VLan id 1703 con nombre "epv2-red-back".
   red destinada a los componentes backend asilados de otras capas ambiente de desarrollo
1. VLan id 1702 con nombre "epv2-red-front-prd".
   red destinada a los componentes front aislados para ambiente de producción
1. VLan id 1703 con nombre "epv2-red-back-prd".
   red destinada a los componentes backend aislados para ambiente de producción.


### Repositorios de fuentes

Se define para el repositorio de fuentes con instalación onpremise, con ejecución de devops en el mismo server onpremise.

#### Instalacion GitLab Server

Se indentifica en el sitio web de gitlab la documentación para la los requerimientos minimos del servidor en https://docs.gitlab.com/ee/install/requirements.html

Con esta información se identifica que se va a realizar una instancia del tipo Discovery d2-8 (se uso compartido) con 8GB de RAM, 4 vCores de procesamiento, 50gb de almacenamiento, 500Mbit/s banda ancha públioca localizada en Gravelines GRA11.

Además se obtiene la documentación de la instalación del servidor en la siguiente referencia https://about.gitlab.com/install/#ubuntu

1. Crear Maquina d2-8-gra11-epv2-gitlab RAM 8 GB 4 vCores ( Discovery : d2-8 / Location : Gravelines GRA11 )
1. Utilizar la red "epv2-red-general"
1. Crear Key : https://help.ovhcloud.com/csm/en-dedicated-servers-ssh-introduction?id=kb_article_view&sysparm_article=KB0044021
1. Asignar IP Floating 141.94.173.137
1. Configurar DNS gitlab 141.94.173.137 con el nombre repositorio.greenest.app (Hablar con Juan David david@zubi.group )

## Llave Creada para gitlab server.

La llave creada se configura en la consola ovh con el nombre epv2-key-gitlab adjunto en los archivos:

1. Clave Privada : [epv2-key-gitlab](assets/epv2-key-gitlab)
   ```console
   % cat epv2-key-gitlab
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

1. Clave Pública : [epv2-key-gitlab.pub](assets/epv2-key-gitlab.pub)
   ```console
   % cat epv2-key-gitlab.pub 
   ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBB9yFLuo1t6uv+HhaYmYOsSOqLRR96XpkWUwEv0+PzWhszGh+c/lTXMwcFdymalkHktUXPj7Gg9LFiXIU4DHtVc= imagemaker@IMCL-BULVCH.local
   ```

## Conectarse a servidor

Para poder conectarse al servicio de gitlab es necesario tener la llave privada en el mismo directorio.

```console
% ssh -i epv2-key-gitlab ubuntu@repositorio.greenest.app
```

## Instalacion Gitlab

La instalación de gitlab está provista por la dcoumentación de gitlab https://about.gitlab.com/install/#ubuntu

Script de instalación
```console
$sudo apt-get update
$sudo apt-get install -y curl openssh-server ca-certificates tzdata perl
$curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.deb.sh | sudo bash
$sudo EXTERNAL_URL="https://repositorio.greenest.app" apt-get install gitlab-ee
$sudo cat /etc/gitlab/initial_root_password
```

## Servicio Gitlab

Una vez instalado el servicio ingresar con la URL + usuario (root) y la password generada en el archivo /etc/gitlab/initial_root_password

Credenciales

URL : https://repositorio.greenest.app/

Login : root 

Password: dAszKsvX3x9vjdhfDxZXv8AsOTcp7KpqC52zBQvgQtU=

## Cambio de DNS

En caso de cambio de DNS del repositorio se debe editar el siguiente archivo.

```console
% sudo vi /etc/gitlab/gitlab.rb
```
Cambia la variable external_url 

external_url 'https://repositorio.greenest.app'

reconfigura gitlab 

```console
%  sudo gitlab-ctl reconfigure
```


## Registro a ZeroTier

referencia de descarga e instalación https://www.zerotier.com/download/ parte "Linux (DEB/RPM)"

Instalación

```console
$ curl -s https://install.zerotier.com | sudo bash
```

registro a la red "e3918db48393fa2e" de Zewro Tier provista por Alfredo Ayala (alfredo@feelingdevs.com)

```console
$ sudo zerotier-cli join e3918db48393fa2e
```

Revisar la MAC para pedir que el administrador de la red lo autorice, ya estará como ACCESS_DENIED

```console
$ sudo zerotier-cli listnetworks
200 listnetworks <nwid> <name> <mac> <status> <type> <dev> <ZT assigned ips>
200 listnetworks e3918db48393fa2e  2e:5c:d6:53:93:e5 ACCESS_DENIED PRIVATE ztk4jb4tvn -
```

Indicar al adminiatrador que autorice la MAC 2e:5c:d6:53:93:e5, una vez autorizado mostrará la IP Asignada.

```console
$ sudo zerotier-cli listnetworks
200 listnetworks <nwid> <name> <mac> <status> <type> <dev> <ZT assigned ips>
200 listnetworks e3918db48393fa2e GreenestAI 2e:fe:07:f5:1f:24 OK PRIVATE ztk4jb4tvn 172.24.99.136/16
```

## Configuracion de correo en gitlab
1. Configurar Mails : https://docs.gitlab.com/omnibus/settings/smtp

### Componentes Contenerizados

## Instalación de Kubernetes FRONT
crear un servicio de contenedores administrado
Localizacion : Gravelines GRA11
Versión: 1.29.3-0
Red : 1702- epv2-red-front
Puerta de Enlace : On - IP en blanco para colocar por default
Tipo de Nodo : Discovery D2-4 4GB RAM / 2VCores / 250mbs
Nombre : d2-4-epv2-kubernetes-front


## Instalación de Kubernetes BACK
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

## Conectar a base de datos desde servidor VPN 
El objetivo es bajar la carga operativa de acceso a la base de desarrollo ya que los desarrolladores cambian de ubicacion y hay que repetir el acceso a cada día.

La solución a esto acceder directamente vá ByPass como pivote

### Configuración de acceso por DBeaver

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

### Configuración de acceso por tunnel directo SSH




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