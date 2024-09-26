
1. **Arquitectura de Microservicios**:
   - Implementación de una arquitectura de microservicios con alta disponibilidad y **resiliencia superior al 99.97%**.
   - Estándar de microservicios aplicado en más de **tres aplicaciones operativas**.
   - **Adopción estratégica** con asignación de un arquitecto para evaluar y optimizar el uso de microservicios.
   - Enfoque hacia **API Management** y **API Gateway** como parte del gobierno de la arquitectura.

2. **Plataforma y Contenedores**:
   - Uso intensivo de **Kubernetes** para la orquestación de contenedores.
   - Evaluación de opciones **serverless**, limitada por la dependencia del proveedor.
   - **Automatización completa** en Kubernetes; serverless requiere automatización adicional.
   - **Costo promedio de contenedores** optimizado a **14 USD**.
   - Implementación de **Istio** como malla de servicios (Service Mesh).
   - Soporte para **Dockerfile** y **Linux Alpine** en la construcción de imágenes de contenedores.

3. **DevSecOps**:
   - Proceso de **DevSecOps completo** integrado con **Azure DevOps**, con implementación desde la fase inicial del desarrollo.
   - **Cobertura del 90%** en seguridad, sin vulnerabilidades desde el comienzo, y con métricas rigurosas: **0% deuda técnica** y **0% code smells**.
   - Uso de **ArgoCD**, **HELM** y **GitOps** para la gestión de despliegues y configuraciones.
   - Gestión de **secrets** y **certificados** utilizando **Azure KeyVault** y **OAuth 2.0**.

4. **Tecnologías y Frameworks**:
   - Preferencia por **SpringBoot** debido a su **madurez de 14 años**, estándar abierto y rentabilidad en comparación con otros lenguajes como Python.
   - **Adopción de .NET Core 8** como una tecnología clave.
   - Integración con **Registry Azure** y **ArgoCD** con el apoyo del equipo DevOps.
   - **Evaluación rigurosa** de cada nueva tecnología antes de su adopción.
   - Uso de tecnologías como **Swagger (API)** para la documentación de servicios.

5. **Prácticas y Estándares**:
   - Desarrollo y despliegue de **microservicios para todas las nuevas tecnologías**.
   - **Proceso de revisión y evaluación** obligatorio para todas las tecnologías nuevas.
   - Orientación hacia un modelo de **open banking basado en APIs**.
   - Integración con sistemas de mensajería como **RabbitMQ** y **IBM MQ** y soporte para **REST**, **Web Services** y **GraphQL**.
   - Creación de un **HealthCheck** automatizado para la infraestructura.

6. **Governanza y Políticas**:
   - **Arquitectura empresarial** en fase inicial, con consideraciones políticas en curso.
   - Uso de **API Manager propio** y desarrollo de componentes de validación XML.
   - **Enfoque en la calidad del código**: cobertura mínima del 90%, sin code smells, deuda técnica ni vulnerabilidades.

7. **Bases de Datos y Compilación**:
   - Estrategia de base de datos por microservicio o esquema.
   - Atractivo en la adopción de microservicios por los **tiempos de compilación rápidos y la velocidad de construcción**.
   - Implementación de **Dacpac** y uso de **SQL Server**.
