## Instalacion

Referencia : https://hackernoon.com/analyzing-your-code-with-sonarqube-running-via-docker-image

## Instalar en Docker
```console
docker run -d --name sonarqube -p 9000:9000 sonarqube
```

## Ingreso Admin

Login en : http://localhost:9000/

## Crear Token desde Azure DevOps
https://dev.azure.com/image-maker-cl/BAC

## generar token
https://dev.azure.com/image-maker-cl/_usersSettings/tokens

## importar en Sonar con el token
Colocar URl DevOPs : https://dev.azure.com/image-maker-cl
Agregar Token Usuario DevOps

## seleciconar SAC

## Crear usuario report en SonarQube

## Crear token de report
squ_5ac557346ad5254ad79ad782f2abe8e9072f6fcb


## en el proyecto selecciona ejecucion local


## ejecutar desde Docker el sonar
```console
docker run --add-host=host.docker.internal:host-gateway --rm -e SONAR_TOKEN="squ_5ac557346ad5254ad79ad782f2abe8e9072f6fcb" -e SONAR_HOST_URL="http://host.docker.internal:9000" sonarsource/sonar-scanner-cli -Dsonar.projectKey="BAC_sac_556b81ea-b078-4e6d-b1b2-a58dcf7ed427"

```


Diego Mayor tenia el Ambiente SAC (envia servicio a As400 en claro)
Juan le costo configurar el Ambiente Rational / Whephere Application Server
codificacion PCI






