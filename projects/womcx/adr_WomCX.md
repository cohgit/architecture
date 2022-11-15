# Architecture Desition Record for PLataforma Unificada

## NFR's WomCX
1. Security
    1. Context
        1. Public
        1. Customer
        1. Colaborator
    1. Autenticate
    1. Authorization
    1. Access Control "Profile" (RBAC / Role-Based Access Control)
1. Front
    1. MicroServices
        1. MicroFrontend
        1. MicroServices 
    1. Language
        1. ReactJS
    1. Content Delivery Network (CDN)
        1. CloudFront
1. Technicals 
    1. Monitoring
    1. Notifications
    1. Traceability
    1. CI/CD
    1. Control Versions
    1. SEO
    1. Analytics
    1. Reporting
    1. Feature toggle
    1. Personalitation
    1. Kit UX
    1. Isolation Store And Digital
    1. Shopping Cart Persistence / Shared
    1. Responsive Design
    1. API / Headless Magento
    1. Role Matrix / Permissions

1. Fast Search API
    1. Elastic Caché, Algolia vs Lucene Atlas Mongo 

1. Datos WomCX - Fase 1
    1. ¿?Migración de la operación anterior a mongo o la estrategia de servicios conectado en 
    1. Nuevos reportes deberán ir a GCP y los reportes deberán ir a equipo de BI
    1. (*)Considerar el uso de una tabla de transacciones MAT en Oracle para la homologación Recargas en Linea. 

1. Datos WomCX - Target
    1. Onpremise de reporte se quedan igual hasta la migración a GCP y desactivación de MAT.

Cloud AWS
1. Cuenta de AWS Account para proyecto
    1. Fase Trasición
        1. Cuenta AWS E-Commerce
    1. Dase Target
        1. Cuenta AWS Shared / Digital
1. Cluster EKS
    1. Fase Transición
        1. Cuantificar el aumento de costo de la operación de los componentes actuales.
    1. Fase Target



Srv* Servicios OnPremise y Datos
1. Fase Transicicón
    1. Reutilización de servicios SRV
1. Fase Target
    1. Migración de servicios a Cloud y considerando reportes en BI

Tmf* Servicios Core
1. Fase Transicicón
    1. Reutilización de servicios TMF del cluster de ecommerce llamado "tmf-cluster"
        acl\acl-georesearch-service
        acl\configuration-service
        alb-ingress-controller\ingress-controller
        auth\configuration-service
        auth\login-manager
        backoffice\backoffice
        backoffice\configuration-service
        ecommerce-bff\ecommerce-graphql
        instana-agent\instana-agent
        mesh\controller
        mesh\crds
        mtls-nginx\mtls-nginx-wsdl
        mtls-nginx\mtls-nginx
        nginx\nginx
        tmf\configuration-service
        tmf\tmf-account-management-service
        tmf\tmf-agreement-management-service
        tmf\tmf-appointment-service
        tmf\tmf-customer-bill-service
        tmf\tmf-customer-management-service
        tmf\tmf-federated-id-service
        tmf\tmf-geographic-address-service
        tmf\tmf-geographic-site-service
        tmf\tmf-number-portability-service
        tmf\tmf-party-management-service
        tmf\tmf-payment-management-service
        tmf\tmf-prepay-balance-service
        tmf\tmf-product-catalog-service
        tmf\tmf-product-inventory-service
        tmf\tmf-product-offering-qualification-service
        tmf\tmf-product-order-service
        tmf\tmf-resource-inventory-service
        tmf\tmf-resource-pool-management-service
        tmf\tmf-usage-consumption-service
        tmf\womcl-geographic-address-service
    1. 
1. Fase Target
    1. Migración de servicios a Cloud y considerando reportes en BI



Reunion con Atlas / MongoDB
* Lucene Search Text Activado por los datos de MongoDB
* AWS north virginia east-us-1, no hay cambios de plataforma
* Atlas garantiza la HA master con replica y failover (5sgs)
* Comunicación Clouster ATLAS directa por VPC peering
* Costos Bajan los transfer iosp
* RBAC user LDAP para MongoDB
* Active Directory Conector IAM 
* Atlas Usuarios / Certificados / IAM
* Atlas Respaldos son basados en Sapnshots 1 hora de uno y otro con la retención
* Atlas continuos backup (offload) para puntos de recuperación.
* Organización de WOM / Proyectos / Cluster
* Definiciones del Modelado de Datos y Bases de Datos de servicios para revisar la creación nuevo cluster análisis.
* tarde 1/2 medio día
* Compartir Arquitectura con equipo Mongo 