# Configuración de proxy en computador WOM

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
export https_proxy=http://10.120.136.40:8080

## configurar NPM 
npm config set proxy http://username:password@host:port

## Maven save time
mvn clean install -DproxySet=true -DproxyHost=ur.proxy.server -DproxyPort=port

## minikube
set HTTP_PROXY=http://10.120.136.40:8080/
set HTTPS_PROXY=http://10.120.136.40:8080/
set NO_PROXY=localhost,127.0.0.1,10.96.0.0/12,192.168.59.0/24,192.168.49.0/24,192.168.39.0/24


## Quitar proxies
set HTTPS_PROXY=
set HTTP_PROXY=
set NO_PROXY=
set http_proxy=
set https_proxy=
set no_proxy=
git config --global --unset http.proxy
npm config delete proxy

