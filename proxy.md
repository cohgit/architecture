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

## Configuración de proxy para Git
git config --global http.proxy "http://10.120.136.40:8080"

## WSL set proxy
export http_proxy=http://10.120.136.40:8080/
export https_proxy=http://10.120.136.40:8080

## configurar NPM 
npm config set proxy http://username:password@host:port

## Maven save time
mvn clean install -DproxySet=true -DproxyHost=ur.proxy.server -DproxyPort=port

## Quitar proxies
set HTTPS_PROXY=
set HTTP_PROXY=
set http_proxy=
set https_proxy=
git config --global --unset http.proxy
npm config delete proxy

