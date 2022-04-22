# Flutter

Instalaciones Requeridas

Url de Flutter : https://docs.flutter.dev/get-started/install/windows#install-android-studio
Url de Android Studio : https://developer.android.com/studio
Url de Visual Studio (Comunidad) : https://visualstudio.microsoft.com/es/downloads/


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

quitar proxies
set HTTPS_PROXY=
set HTTP_PROXY=
set http_proxy=
set https_proxy=

## WSL set proxy
export http_proxy=http://10.120.136.40:8080/
export https_proxy=http://10.120.136.40:8080