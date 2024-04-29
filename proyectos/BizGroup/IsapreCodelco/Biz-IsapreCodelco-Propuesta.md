-Elaborar/trabajar enfoque para dar respuesta al Requerimiento de corto plazo Cobertura Brechas de Seguridad Sistema Interconsultas, los temas a considerar son:
- Enfoque de Solución (diagrama + explicación)
- Factores críticos de éxito (que se requiere del Cliente)
- Entregables
- Plan de trabajo Alto Nivel


# Propuesta de Solución para Sistema de interconsultas medicas

La siguiente solución como objetivo resolver las inquietudes más imaportantes del equipo de Isapre Codelco, respectoa las observaciones de seguridad que tiene el sistema medico de interconsultas.

## Requerimiento

Se necesita evaluar la mejora de las observaciones de seguridad que tienen relación con la obsolecencia del sistema, sus dependencia y protocolos de seguridad no soportados. la estrategia es mejorar técnicamente el sistema y modificar el aplicativo en términos funcionales.

### Requerimientos Técnicos.

Los requerimientos técnicos a abordar son las observaciones identificadas por la organización respecto al soporte de leguaje, protocolos, sistema y dependencias.

Los puntos abordados son :

1. Encriptación de la Base de Datos con una versión TLS soportada que el aplicativo por el tiempo no soporta.
1. Librería de manipulación de PDF obsoleta.
1. Uso de Mails en el sistema.

## Solución Propuesta

La solución es migrara el sistema, sin embargo es de plazo mediano largo, por lo que hay que aplicar un asilamiento a nivel de aplicativo en contenedor para no tener las vulnerabilidades en el contexto de servidores.

## Estrategias

Las estrategias que se identifica es asilar el aplicativo en un entorno de contendores, activando las mismas pruebas actuales del sistema y considerando la carga actual.

![alt](Sist.%20Interconsultas/assets/EstrategiaSolucion.png)

### Aislación de la aplicación

Consiste en armar una imagen dcoker con la versión legada del sistema y framework .net para garantizar la continuidad del aplicativo, y aplicar los cambios requeridos por las vulnerabilidades indicadas.

Una de las ventajas de hacer esto es conocer exactamente lo que usa el sistema como dependencias y al aislar sbaremos con exactitud si las integraciones identificadas son las corectas o hay no identificadas.

### Exposición API

Consiste en agregar una capa adicional de API con versión nueva de net core como API individual reutilizando las llamadas del los ASP a la Base de Datos con las versiones actualizadas.

Agregar una capa de API Manager para exponer dichos servicios para su reutilización al exterior.

### Actualización Front

Esta estapa consiste en actualizar la capa front del sistema usando API solamente, esto puede servir de ejemplo o baso para otros proyectos de la compañia.

Va a permitir dar valor a la funcionalidad del sistema Actual.

## Actividades Esatrategia de Asilación.

Para lograr la aislación es que debemos tener las siguientes consideraciones :

### Supuestos

1. Disponer de los fuentes del aplicativo y/o proceso de instalación.
1. Disponer de un servidor con las misma capacidades con servicio de docker incorporado.
1. Considerar que esto no considerar la manualidad respecto a DevOps, la automaticación del proceso de desarrollo hay que evaluar.

### creación de sistema

1. Crear pruebas automaticas base para aplicar en sistema actual y asilado de manera de certificar no impato del cambio.
1. Armar desde los componentes la imagen que usará para ejecutar, se evaluarán mas de una alternativa, respetando las dependencias legadas.
1. Identificar los servicios de entrada y salida del sistema para exponer como servicio balanceado.
1. Identificar y otorgar accesos a los servicios que consume el sistema.
1. Cambiar las librerías de Correos y evaluar impacto.
1. cambiar las librerías de conexión y evaluar impacto, es es una tarea de alta complejidad y debe estar abierto a diferentes posibles soluciones ya que está asociada a investigación por que debe tener al menos 3 a 5 iteraciones.

### Armado de ambientes Calidad / Producción

1. Identificar los procedimientos y/o requerimientos para el armado de los ambientes.
1. Acompañamiento en el proceso de liberación en productivo.


## Estimación

1. Tester - Contrucción de Pruebas Automáticas 90 Horas.
1. Arquitecto - Setup y Armado de imagenes de sistema 90 Horas.
1. DevOps - Iteraciones 2 o 3 ciclos Prueba / Error de 3 días 70 Horas.
1. Tester - Certificacion 90 Horas.
1. Arquitecto/DevOps - Acompañamiento 90 Horas.

Total 430 horas.




