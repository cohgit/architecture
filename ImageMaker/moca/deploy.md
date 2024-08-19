 
 ### Clona el proyecto de DevOps moca acr helm y usa rama de desarrollo
 ```console
 git clone https://cesar.ogalde@dev.azure.com/image-maker-cl/Moca/_git/moca-acr-helm
 cd moca-acr-helm/
 git checkout based/develop
 ```

 ### instala azure cli si no lo tiene instalado
 ```console
 curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
 az version
 ```
 
 ### Autenticar con el usuario de azure devops abriendo el link con el ususario de imagemaker
 ```console
 az login
 ```

### Activa las librerias neecsarias
```console
 source .activate
```
si tienes error con linux usa el siguiente comando
```console
 export MOCA_ACR_HELM_PATH=$(pwd)
 echo $MOCA_ACR_HELM_PATH
 source .activate
```
 
 echo "se carga el utilitario de libreria"
 moca_scan MOCA-766
 moca_scan MOCA-776
 echo "scanea todo los imagenes relacionadas a la rama con el ID del feature segun la convecion de git en devops"
 echo "los checks muestra que en contro un match mostrando la imagen rama e id del commit (para revisioanes mas profundas)"
 echo "los checks muestra que en contro un match mostrando la imagen rama e id del commit (para revisioanes mas profundas) y hash de la imagen"
 git diff
 echo "modifica un archivo - moca artifatcs, la metadata branch y quien desplego y muestra quien desplega hoy, elimina las ramaas anteriorres de despliegue y deja las nuevas (SIN HACER NADA)"
 moca_build_charts
 echo "genera imagenes y tag, te pregunta por cada una en caso que el desarrollador no necesite subir"
 git diff
 echo "agrega values.yml por cada microservicio"
 git config user.email "cesar.ogalde@imagemakeraz.onmicrosoft.com"
 git config user.name "César Ogalde H"
 echo "genera un despliegue en desarrollo"