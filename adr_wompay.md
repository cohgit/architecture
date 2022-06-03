# Architecture Desition Record for Wompay
This are de desitions taken with domain team for technical definitions.

1. Whale ZSmart (Backend) -> No hay impacto (As is).
    1. Entrega vía SFTP la deuda diariamente.
    1. Recibe los provisionamientos de DEVELTEL.
    1. Recurrencia Alta Bajas.
1. DMZ OnPremise
    1. Entregar accesos para obtención de deuda
1. EL (Extract and Load) de Wompay de la deuda a la Base
    1. construir triggers en SaaS Mongo Cloud asociado a la nube para el rescate de la deuda con las credenciales SFTP y ejecutar carga de datos en la colección de deuda en la base de "Pagos recurrentes".
1.  Base de  Datos "Pagos Recurrentes".
    1. Crear base de datos de pagos recurrentes con
        1. Billing.
        1. Product.
        1. Charge.
        1. Recurrence.
        1. Log Trazability.
        1. Enroll TokenCard.
    1. Considerar la migración de datos y servicios a AWS (up to cloud).
    1. validar si la historia se migra a mongo o se va a Datalake (BI). objetivo quedarse con la información operativa necesaria.
1. Proceso de Pagos de Recurrentes.
    Es un microservicio agendado diariamente para el pago de recurrentes.
    1. lea las recurrencias activas al dia de ejecucción (productos y cargas) y las recurrencias activas que tengan deuda con fecha de vencimiento menor o igual a la fecha de ejecución, además de deben excluir las deudas que superan el monto máximo.
    1. ejecute la recaudación de las recurrencias filtradas invocando la solución de cargos vía Kushki con el subscriptionid.
    1. Notifica de la recaudación a DEVETEL de las recurrencias pagadas.
    1. Notifica a los clientes de la actiones tomadas y sus resultados.
    1. Registra trazabilidad de lo ejecutado por este proceso.
1. Microservicios Actuales de Wompay (up to cloud) opcional a la renovación técnica de wompay. 
    1. Subida de información a mongo cloud atlas
    1. actualización de componentes al cloud (subir o reemplazar).
1.  Recurrencia de montos fijos con kushki
    1. requiere enrolamiento por cada subscription, no se requiere esto.

# Assumptions
1. No está en alcance la conciliación
1. No está en alcance la recurrencia en Whale ZSmart salvo el metodo de pago tipo numero "16" denominado WomPay (configuración solamente).
1. No está en alcance la recurrencia en kushki.