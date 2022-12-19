# Configuración de proxy en computador WOM

## Configuración Curl con proxy
curl --proxy "http://10.120.136.40:8080" -sL https://j.mp/glab-cli  | sudo sh

## Configuración de proxy WOM en comandos para flutter
end CMD 
set https_proxy=10.120.136.40:8080
set http_proxy=10.120.136.40:8080

## Configuración de proxy WOM en android studio 
En Android Studio -> System settings -> HTTP Proxy ->
Auto-detect proxy settings
URL  : http://wpad.novalte.corp/wpad.dat

## Configuración de proxy WOM en Java
Cuando tengas que aceptar las licencias de flutter procura configurar las variables de Java para Proxy
set HTTPS_PROXY=http://10.120.136.40:8080
set HTTP_PROXY=http://10.120.136.40:8080
set NO_PROXY=localhost,127.0.0.1,::1
# All CMD
setx HTTP_PROXY http://10.120.136.40:8080
setx HTTPS_PROXY http://10.120.136.40:8080
setx NO_PROXY=localhost,127.0.0.1,::1

## Configuración de proxy para Git
git config --global http.proxy "http://10.120.136.40:8080"

## WSL set proxy
export http_proxy=http://10.120.136.40:8080/
export https_proxy=http://10.120.136.40:8080/

export http_proxy=http://10.220.253.45:8080/
export https_proxy=http://10.220.253.45:8080/

## configurar NPM 
npm config set proxy http://10.120.136.40:8080/

## Maven save time
mvn clean install -DproxySet=true -DproxyHost=ur.proxy.server -DproxyPort=port

## minikube
set HTTP_PROXY=http://10.120.136.40:8080/
set HTTPS_PROXY=http://10.120.136.40:8080/
set NO_PROXY=localhost,127.0.0.1,10.96.0.0/12,192.168.59.0/24,192.168.49.0/24,192.168.39.0/24

## ssh proxy
-o "ProxyCommand=nc --proxy 10.120.136.40:8080 %h %p"

## WSL - Ubuntu APT
en el archivo /etc/apt/apt.conf.d/proxy.conf
Acquire::http::Proxy "http://10.120.136.40:8080/";
Acquire::https::Proxy "http://10.120.136.40:8080/";

export HTTP_PROXY=10.120.136.40:8080
export HTTPS_PROXY=10.120.136.40:8080
export NO_PROXY=localhost,127.0.0.1,::1

## Maven arguments
-Dhttps.proxyHost=10.120.136.40 -Dhttps.proxyPort=8080 -Dhttps.nonProxyHosts=localhost,127.0.0.1,::1 -Dhttp.proxyHost=10.120.136.40  -Dhttp.proxyPort=8080  -Dhttp.nonProxyHosts=localhost,127.0.0.1,::1

## Quitar proxies
set HTTPS_PROXY=
set HTTP_PROXY=
set NO_PROXY=
set http_proxy=
set https_proxy=
set no_proxy=
git config --global --unset http.proxy
git config --global --unset https.proxy
npm config delete proxy
export http_proxy
export https_proxy


## route directly
C:\WINDOWS\system32>route add 10.120.10.220 MASK 255.255.255.255 192.168.254.2
 Correcto 

## route segment 
C:\WINDOWS\system32>route add 10.120.0.0 MASK 255.255.255.255 192.168.254.2
 Correcto 

## docker ~/.docker/config.json
{
 "proxies":
 {
   "default":
   {
     "httpProxy": "http://10.120.136.40:8080",
     "httpsProxy": "http://10.120.136.40:8080",
     "noProxy": "localhost"
   }
 }
}