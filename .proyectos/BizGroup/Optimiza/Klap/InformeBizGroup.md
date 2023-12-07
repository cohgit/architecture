### Informe de consultoría Bizgroup
Este informe tiene como objetivo entregar los apoyos entregados por parte de la consultoría de arquitectura cloud para cliente optimiza para proyecto de diseño e implementación cloud de su cliente KLAP, buscando desde el levantamiento de los requerimientos validando la buena arquitectura con los mejores estándares.

## Contexto General
El cliente tiene la necesidad de manejar con una mejor tecnología incoporrando soluciones cloud, principalmente es el manejo de mensajería en linea para la optima transaccionalidad.

## Requerimientos de arquitectura
1. Alta disponibilidad.
1. Resilencia.
1. Adaptabilidad Operacional.
1. Seguridad e Integridad.
1. Infraestructura como código.

## Observaciones al diseño revisado V1
el foco de las observaciones fueron a la comunicación del diagrama de AWS en donde habían algunas corecciones que hacer por ejemplo:
1. La alta disponibilidad de red en multiple zonas.
1. Distribución de los componentes en las diferentes redes.
1. Indentificación de costos de los componentes.
1. Revisión del servicio de transferencia de archivos.
1. Escritura de erchivo tipo write flush (streaming).

## Observaciones al diseño revisado V2
1. El framework de mensajería se evalúa a red pandas modalidad BYC.
1. Aplica observación de transit gateway
1. Cambia Componente de Procesamiento de EC2 Kubernetes a AWS EKS.
1. Requerimiento de trasnferencia SFTP propone eliminar AWS Transfer Family en vista que servicio debe dejar archivo vía sftp en maquina onpremise.

## Observaciones al diseño revisado V3
1. Componentes transversales Cloud Trail
1. 

## Observaciones al diseño revisado V4
## Observaciones al diseño revisado V5
## Implementación de IaC