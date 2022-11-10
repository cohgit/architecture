tmf-account-management-service.cloud.wom.cl
tmf-agreement-management-service.cloud.wom.cl
tmf-appointment-service.cloud.wom.c
tmf-customer-bill-service.cloud.wom.cl
tmf-geographic-address-service.cloud.wom.cl
tmf-number-portability-service.cloud.wom.cl
tmf-party-management-service.cloud.wom.cl
tmf-payment-management-service.cloud.wom.cl
tmf-prepay-balance-service.cloud.wom.cl
tmf-product-catalog-service.cloud.wom.cl
tmf-product-inventory-service.cloud.wom.cl
tmf-product-offering-qualification-service.cloud.wom.cl
tmf-product-order-service.cloud.wom.cl
tmf-resource-inventory-service.cloud.wom.cl
tmf-resource-pool-management-service.cloud.wom.cl
tmf-usage-consumption-service.cloud.wom.cl  
tmf-user-roles-permissions-service.cloud.wom.cl
tmf-federated-id-service.cloud.wom.cl

ssh -L 8082:tmf-party-management-service.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229

ssh -L 8082:tmf-product-offering-qualification-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-geographic-address-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-login-manager-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-party-management-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-product-catalog-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:login-manager-service.integration.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-product-order-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-payment-management-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-product-inventory-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-usage-consumption-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-customer-bill-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-prepay-balance-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-resource-inventory-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-federated-id-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-number-portability-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229



ssh -L 8082:tmf-party-management-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229
ssh -L 8082:tmf-product-inventory-service.qa.cloud.wom.cl:8081 -N -T -i privot-stg.pem ec2-user@54.204.34.229


ssh -L 8090:confluence.novalte.corp:8090 caogaldh@192.168.249.29

Andres (equipo Rodrigo Porras e-commerce/miwom)
chico (equipo de Williams MAT angular)