### STAGE 1: Run ###
## Construido a partir de una imagen 'openjdk:8-alpine'
FROM openjdk:8-alpine
## Paquete de fuentes necesario para la generacion de reportes
RUN apk add ttf-dejavu
## VARIABLES DE ENTORNO
#ENV REMOVE_SERVICES_PORT=80
ENV REMOVE_DB_HOST_WO=jdbc:postgresql://bd-remove-dev.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_HOST_RO=jdbc:postgresql://bd-remove-dev.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_PORT=5432
ENV REMOVE_DB_NAME=remove_dev_2
# ENV REMOVE_DB_PARAMS=
ENV REMOVE_DB_USER=devadmin
ENV REMOVE_DB_PASSWORD=DyhIPdEr#mP8RnQFu#
#ENV REMOVE_HEADERS_FOR_CLOUD=true
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN=https://qa.removesolutions.com
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS=GET,PUT,POST,DELETE,OPTIONS
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS=true
#ENV REMOVE_HEADER_ACCESS_CONTROL_REQUEST_METHOD=Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,
#ENV REMOVE_HEADERS_FOR_QA=true
## Se crea el directorio que contendrá los logs
RUN mkdir /remove-ws-logs
## Copia el archivo ejecutable con los servicios dentro del contenedor
COPY remove-sr-0.1-jar-with-dependencies.jar remove-sr-0.1-jar-with-dependencies.jar
## Expone el puerto 80 para acceder a los servicios
EXPOSE 80
## Inicia el servidor autocontenido
##ENTRYPOINT ["java","-jar","remove-sr-0.1-jar-with-dependencies.jar"]
ENTRYPOINT ["java","-Xms4g","-Xmx8g","-jar","remove-sr-0.1-jar-with-dependencies.jar"]