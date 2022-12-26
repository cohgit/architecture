8082:acl-cxone-service.integration.cloud.wom.cl:8081
8082:acl-cxone-service.qa.cloud.wom.cl:8081


curl --location --request POST 'http://localhost:8082/services/queuecallback?channel=APP&phoneNumber=%2B569161626363&skill=1234' --data-raw ''
curl --location --request POST 'http://localhost:8082/services/promise?channel=APP&firstName=Steven&lastName=Delgado&phoneNumber=%2B569161626363&skill =1234'--data-raw ''


curl --location --request POST 'http://acl-cxone-service.integration.cloud.wom.cl:8081/services/queuecallback?channel=APP&phoneNumber=%2B569161626363&skill=1234' --data-raw ''

curl --location --request POST 'http://acl-cxone-service.integration.cloud.wom.cl:8081/services/promise?channel=APP&firstName=Steven&lastName=Delgado&phoneNumber=%2B569161626363&skill =1234'--data-raw ''



arn:aws:elasticloadbalancing:us-east-1:026649068530:loadbalancer/net/nlb-acl-cxone-service/09261b459f95f2ac





## Requerimiento 
Se solicita acceder a mismo servicio de CXOne desde MIWom, este servicio es el ACL de CXOne.

## Solución
Se utilizarán la integración vía PrivateLink para la integración entre VPC de diferentes cuentas.

## PreRequisitos
1. Accesos a cuenta aws-dig-ecomm-backend-dev(#026649068530) con permisos necesarios.
1. Acceso a maquina de pivote "ec2-54-204-34-229.compute-1.amazonaws.com" con certificado.
1. Accesos a cuenta aws-dig-miwom-dev(#907719427697) con permisos necesarios.
1. URL de servicio ACL operativo "http://acl-cxone-service.integration.cloud.wom.cl:8081/services/queuecallback" 
1. URL de servicio ACL operativo "http://acl-cxone-service.integration.cloud.wom.cl:8081/services/promise"
1. Permisos para la configuración PrivateLink

## Contexto de la solución
Es un mecanismo de integración de red para reutilizar servicios desarrollados que están desplegados en diferentes clúster EKS.

## Implementación

### Localización del Servicio

El dns del servicio acl-cxone-service.integration.cloud.wom.cl está configurado en route 53 como zonas de hosteo llamada "integration.cloud.wom.cl" que tiene un registro de tipo A llamado acl-cxone-service.integration.cloud.wom.cl que apunta a la IP 100.64.179.64.

se identificó la interfaz donde está configurada dicha IP 

