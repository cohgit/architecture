## Requerimiento Baja de Costos
Identificar y Reducir los costos de base de datos

### Análisis de Costos
Se realiza análisis de los costos de base de datos y se identifica activación de H/A para multiples zonas MultipleAZ

[PPT AWS RDS Costos](https://docs.google.com/presentation/d/19yofyau6bKTm9KG7mka0l8Y2KIEZFEKt4h5QB1UP_9c)

Se identifica una baja de costo del 50% del valor de cargo mensual de la base de datos productiva debido a cambias MultipleAZ a SingleAZ, esto es 1.000 USD por mes de ahorro (evaluando el comportamiento de la base en el procesamiento).

### Cambio propuesto
Se sugiere cambiar la característica AWS RDS Postgress Multiple Availability Zones a Single Availability Zones

### Consideraciones
Para lograr la bajada de costos se deben considerar las observaciones de la POC realizada con centro de costos "remove-04".

1. No se aplican los cambios de precios solo con cambiar el flag de Multiple AZ. al menos en los días siguientes.
1. Se sugiere crear una nueva Base de datos productiva sin H/A con SingleAZ y Eliminar Anterior MultipleAZ, se puede hacer en cualquier momento no necesariamente con corte finde mes.
1. Al hacer el cambio se debe monitorear una semana al menos para identificar que no afecte a otros procesos.
1. Una vez que no afecte se puede evaluar ajustar instancia de BDD CPU / RAM

### Plan de Actividades

Este es el plan de actividades en plazo para realizar el cambio de MultipleAZ a SingleAZ

QA (8d)

1. Identificación de certificados, redes, grupos de acesos, etc. 1d
1. Crear Infraestriuctura de BDD con SingleAZ con las mismas características de la actual en terraform. 1d
1. Identificar Snapshot de respaldo ir subir datos a nueva instancia. 1d
1. Solicitar cambio de accesos a aplicativo y desplegar los cambios en producción en ambiente QA/DEV 3d (Validar con Eduardo o Williams)
1. Monitorear comportanmiento de ejecución de scanner. 2d

Producción (11d)

1. Identificación de certificados, redes, grupos de acesos, etc. 1d
1. Crear Infraestriuctura de BDD con SingleAZ con las mismas características de la actual en terraform. 1d
1. Identificar Snapshot de respaldo ir subir datos a nueva instancia. 1d
1. Solicitar cambio de accesos a aplicativo y desplegar los cambios en producción en ambiente Productivo 3d (Validar con Eduardo o Williams)
1. Monitorear comportanmiento de aplicativo 7d.

Total (19d)