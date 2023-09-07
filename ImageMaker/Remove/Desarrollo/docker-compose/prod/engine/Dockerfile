### STAGE 1: Run ###
## Construido a partir de una imagen 'openjdk:8-alpine'
FROM openjdk:8-alpine
## Paquete de fuentes necesario para la generacion de reportes
RUN apk add ttf-dejavu
## VARIABLES DE ENTORNO
ENV REMOVE_SERVICES_PORT=80
ENV REMOVE_DB_HOST_WO=jdbc:postgresql://removeprod.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_HOST_RO=jdbc:postgresql://removeprod.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_PORT=5432
ENV REMOVE_DB_NAME=remove_prd
# ENV REMOVE_DB_PARAMS=
ENV REMOVE_DB_USER=adminprd
ENV REMOVE_DB_PASSWORD=1QYGPV9LVBWJKdpikq
#ENV REMOVE_HEADERS_FOR_CLOUD=true
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN=https://removesolutions.com
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS=GET,PUT,POST,DELETE,OPTIONS
#ENV REMOVE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS=true
#ENV REMOVE_HEADER_ACCESS_CONTROL_REQUEST_METHOD=Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,
ENV REMOVE_HEADERS_FOR_PROD=true
## Se crea el directorio que contendrá los logs
RUN mkdir /remove-engine-logs
## Copia el archivo ejecutable con los servicios dentro del contenedor
COPY remove-engine-0.1-jar-with-dependencies.jar remove-engine-0.1-jar-with-dependencies.jar
## Expone el puerto 80 para acceder a los servicios
EXPOSE 80
## Inicia el servidor autocontenido
##ENTRYPOINT ["java","-jar","remove-engine-0.1-jar-with-dependencies.jar"]
ENTRYPOINT ["java","-Xms4g","-Xmx8g","-jar","remove-engine-0.1-jar-with-dependencies.jar"]