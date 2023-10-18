## Objetivo general
Recibir mensajería y data enriquecida para volcar en archivo disponible por protocolo SFTP

## Alcance Funcional
2.000.000 millones de mensajes diarios, ejemplo 1kb
Archivo Disponible NRT(Near Real Time) por SFTP Visa / Mastercard por cada ventana de procesamiento
Registro en Tabla PreLiquidación, Calculo campo X
Campo PAN Encriptado
Control de Errores Mensajes no procesados.

## Alcance Técnico
Region us-est-1
Disponibilidad 24/7 99,99%
Crecimiento Horizontal (escalabilidad)
Control de Accesos
Encriptación Campo PAN (tránsito)
Exportación Archivo Formato
Salida Log Todos los eventos del mensaje CLF (INFO, DEBUG)
Considerar Well-Architected

## Automatizaciones
DevOps
GitOps
