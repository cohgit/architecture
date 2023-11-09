## Requerimiento de Exportación
Cliente solicita una manera en AWS para consultar datos de remove pero son afectar performance de la base de datos

## Solución propuesta
El estándar de la explotación de los datos es sacar la información de la base y disponerla en almacenamiento para que sea utilizada por servicios de datos para consultas y reporterías.

## Solucción Técnica
El servicio de Base de datos RDS cuenta con mecanismos de exportación tipo dump o desde snapshot/respaldos a almacenamiento s3

Se sugiere la exportación directa en s3 según la siguiente documentación de AWS [Exportar a S3](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_ExportSnapshot.html#USER_ExportSnapshot.Exporting)

## Plan de Actividades

1. Crear componentes necesario para la recepcción de datos (bucket, keys, etc). (Real 2h)
1. Crear Task actual con el último snapshot de la base productiva.(Real 2h)
1. Ejecutar a creación de la exportación (Real 3h).
1. Crear una lectura de datos simple como POC (Estimado 5h)
1. Crear api de lectura requerida para consultas de datos segun lo necesite el cliente. (Pendiente a solicitar) Estimado (3h)
1. Documentar la receta de ejecución operativa para que sea ejecutada por el servicio de IM a Remove mensualmente o cuando se solicite. (Estimado 2h)

Total (17 h) 
1. Ejecutado 70% (12h)
1. Pendiente 30% (5h)