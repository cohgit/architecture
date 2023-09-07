### STAGE 1: Run ###
## Construido a partir de una imagen 'openjdk:8-alpine'
FROM openjdk:8-alpine
## Paquete de fuentes necesario para la generacion de reportes
RUN apk add ttf-dejavu
## VARIABLES DE ENTORNO
ENV REMOVE_DB_HOST_WO=jdbc:postgresql://bd-remove-dev.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_HOST_RO=jdbc:postgresql://bd-remove-dev.cfda9ws4nwwx.us-east-1.rds.amazonaws.com
ENV REMOVE_DB_PORT=5432
ENV REMOVE_DB_NAME=remove_dev
# ENV REMOVE_DB_PARAMS=
ENV REMOVE_DB_USER=devadmin
ENV REMOVE_DB_PASSWORD=DyhIPdEr#mP8RnQFu#
## Se crea el directorio que contendrá los logs
RUN mkdir /remove-engine-logs
## Copia el archivo ejecutable con los servicios dentro del contenedor
COPY remove-engine-0.1-jar-with-dependencies.jar remove-engine-0.1-jar-with-dependencies.jar
## Expone el puerto 4601 para acceder a los servicios
EXPOSE 4601
## Inicia el servidor autocontenido
##ENTRYPOINT ["java","-jar","remove-engine-0.1-jar-with-dependencies.jar"]
ENTRYPOINT ["java","-Xms4g","-Xmx8g","-jar","remove-engine-0.1-jar-with-dependencies.jar"]