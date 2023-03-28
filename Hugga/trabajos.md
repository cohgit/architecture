32H de trabajo devops

1.- Estandarización de despliegue quarkus con registro Azure Container Registry (ACR).
    -Se realizaron diversas pruebas con docker para subir imágenes en el ACR
    -Se probaron nativas, en java , con Quarkus app pero no funcionaban en los diferentes proyectos ya que algunos tenían un tipo de package diferentes, esto me tomo bastante tiempo.
    -
2.- Automatización de la infraestructura como código.
    - se crea repositorio gitops en el proyecto Hügga para el uso de terraform.
    - se crean Azure Functions, pero por temas de proyectos paso a azufre webapps.
    - hubo un problema de versionamiento de terraform y se tuvo que actualizar la versión 2 veces
    - se importaron elementos como se crearon también.
    - se manejó el estado de terraform vía git.
 
3.- Despliegue de web’s Next
    - se probó con Azure Function sin embargo este no prosperó por el tema de los diferentes tipos de packages de los mavens
    - se probó con Azure WebApps ya que dio mas versatilidad para las pruebas de los despliegues con JAR(runner), Copiando el proyecto completo, carpeta quarkus-app, etc.
    - Se identificó estudiando quarkus que hay una variable “quarkus.package.type” que mo estaba igual en todos los respos, se comentó y la imagen Dokerfile.jvm se podía usar con confiabilidad pues este arrancó para todos los servicios.
    - por lo tanto se establece que el pipeline debe ser estándar a los siguientes pasos
      ./mvnw package
      docker build and push en el ACR del Azure.
    sin embargo no respetaba el tema de las versiones.
    - Se invetigó como generar versiones en maven para poder pushear correctamente “npm version  1.0.$(build.buildnumber) --no-git-tag-version”
    - Se investigó como usar el repositorio NPM pkgs.dev.azure.com para la dependencia de compilación de las librerías next
    - Cada vez que se realiza push el pipeline de develop publica la versión de la librería en pkgs.dev.azure.com para que la use la web de next.
    - en el proyecto de next se debe hacer una configuración con el repositorio .npmrc para que tome la ultima versión de la librería apuntando al repo pkgs.dev.azure.com

4.- DevOps en Azure
    - Para un proyecto se probó compilar, copiar archivos y publicar artefacto, sin embargo por varios intentos no se pudo desplegar en un azure fucntion, pero con webapps se logró.
    - El ideal es generar un release tomando el artefacto para desplegarlo en azure webapp (previamente generado por terraform)
    - los release no funcionaron para los microservicios, asi que se iteró hasta que se llegó a la definición de en la compilación generar la imagen y subirla a ACR (configurado de terraform)
    - Se hizo con docker directo ejecutando ./mvnw package; docker login; docker build y push y funcionó desde el pipeline

5.- Despliegue de Imágenes quarkus (Dockerfile.jvm)
    - Logrando hacer la imagen se tuvo que investigar como hacer que Azure WebApp tome dicha imagen para levantar su servicio.
    - Se logró estandarizar los puertos de escucha y variables para develop, hay que eliminar las variables de la imagen y dejarlas en la configuración de terraform para que el webapp las defina y desde ahi se configure la imagen.
    - Para trabajos locales en el caso de necesitar variables quarkus usar %dev.quarkus.variable=valor con el comando ./mvnw quarkus:dev las considera y estas son ignoradas al levantar la imagen.
    - de esta manera quedó estanadr la generación de imágenes y se configuraron los pipelines de todos los microservicios encontrados.
    - se subieron los componentes en terraform para que tome las configuraciones (se dejaron en /labs/ las imágenes y los webapp de les puso un prefijo “t-” para diferenciar los automáticos de terraform).

6.- DNS dev.hugga.io
    - se obtuvo acceso a wix y sostuvo comunicación de soporte por el DNS
    - Se identificó que DNS *.hugga.io estaba configurado en Azure y el www lo redirecciona a wix.
    - además ya estaba apuntando a otro sitio por lo que tuve que sacar del registro el wildcard *.hugga.io
    - se investiga y la mejor manera es haciendo un Azure Front Door asi que se eimplementa (se trata se hacer por terraform pero falla, y tiene varios warning’s por tanto al ser sensible se siguen los pasos de la nube).
    - se establece una ruta dev.hugga.io a la web de next, toma un tiempo para validar y replicar.
    - se establece una ruta devbackend.hugga.io para la web de backoffice.