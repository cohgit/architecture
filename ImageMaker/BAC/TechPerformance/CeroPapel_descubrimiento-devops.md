Objetivo de la medición

Para optimizar los procesos DevOps en Cero Papel, estamos organizando los repositorios en Azure DevOps, reactivando y optimizando los pipelines, actualizando versiones de software (especialmente Java) e implementando indicadores clave para medir el rendimiento, la frecuencia de despliegue y la calidad del software. Ya contamos con acceso a BAC y solo nos falta obtener acceso al proyecto Cero Papel para comenzar a medir el estado actual, asegurando mejoras continuas y un rápido tiempo de respuesta para el negocio.


Hallazgos Semana 13 al 17 de Mayo

1. La organización y los repositorios en Azure DevOps necesitan ser adecuadamente configurados.
1. El proyecto existente (https://dev.azure.com/ORG-CRI-GTI/CEROPAPEL) carece de los accesos necesarios.
1. Los pipelines, cuyo último registro de ejecución data de enero de 2024, necesitan una revisión exhaustiva ya que no se han identificado pasos productivos.

Riesgos 

1. Desactivación del servidor Git Gogs en junio.
1. Obsolescencia de las versiones de Java utilizadas.
1. Aumentar la capacidad DevOps para la célula.
1. Reactivar los pipelines existentes


Mitigaciones:
1. crear equipo ceropapel en azuredevops y agregar al equipo. 50%
1. Iniciar proceso de subida y reactivación de pipelines. 0%


Indicadores de Valor
1. Tiempo de Despliegue, Evaluar el periodo desde el inicio del requerimiento hasta su liberación
1. Frecuencia de Despliegue, Cuantifica la regularidad y eficacia de los despliegues realizados
1. Tasa de Errores en los pasos, Analizar la correlación entre procesos y fallos para medir la calidad del software
1. Colaboración Fábrica/Operación, Valorar la integración y eficacia entre desarrollo y operaciones
1. Automatización, Evaluar la extensión de la automatización en despliegues y pruebas.
1. Calidad del Software, Inspeccionar el desarrollo, prácticas, seguridad y adhesión a estándares establecidos
1. Seguridad, Identificar vulnerabilidades y verificar el cumplimiento con estándares de seguridad.
1. Performance, Medir la disponibilidad, eficiencia operativa y rendimiento post-despliegue
1. Tiempo de recuperación, Asegurar y evaluar la prontitud en la recuperación de sistemas tras incidentes
1. Interacciones para release, Determinar la eficiencia del ciclo de desarrollo a través de las iteraciones necesarias para la liberación

Detalle de los riesgos

1. Desactivación del servidor Git Gogs en junio
    - Riesgo		: Interrupción de las operaciones de desarrollo debido a la desactivación del servidor Git Gogs programada para junio.
    - Impacto potencial	: Pérdida de acceso a los repositorios de código y posible retraso en los proyectos en curso.
    - Medidas de mitigación	: Planificar la migración a una nueva plataforma de gestión de repositorios antes de la fecha de desactivación.
1. Obsolescencia de las versiones de Java utilizadas
    - Riesgo		: Uso continuado de versiones obsoletas de Java.
    - Impacto potencial	: Vulnerabilidades de seguridad, incompatibilidades con bibliotecas y herramientas modernas, y problemas de rendimiento.
    - Medidas de mitigación	: Actualizar a versiones más recientes de Java y realizar pruebas de compatibilidad exhaustivas.
1. Aumentar la capacidad DevOps para la célula
    - Riesgo		: Capacidad insuficiente del equipo DevOps para satisfacer las necesidades de la célula.
    - Impacto potencial	: Retrasos en la entrega de proyectos, acumulación de tareas pendientes y reducción de la eficiencia operativa.
    - Medidas de mitigación	: Proporcionar capacitación continua para el equipo y sumar a sus procesos buenas prácticas DevOps.
1. Reactivar los pipelines existentes
    - Riesgo		: Fallos en la reactivación de los pipelines existentes.
    - Impacto potencial	: Interrupciones en el flujo de trabajo de desarrollo y despliegue, y posibles errores en la producción.
    - Medidas de mitigación	: Revisar y actualizar los pipelines antes de reactivarlos, asegurando su correcta configuración y funcionamiento.

Semanas 20 al 31 de Mayo

Medición y adecuación

Esta semana estuvimos validando accesos y con eso las primeras instancias de comunicación con el cliente BAC, entre ellas una reunión con arquitecto, chats con equipo de devops entre otras.
Desde el punto de arquitectura se abordaron los temas de mayor relevancia de cara a dar valor desde la experiencia de César Ogalde (capacidades devops y de arquitectura)


Hallazgos
1. Problemas de conexión con el  servicio git obsoleto desde VDI ni servidores de cero papel.
1. Al revisar los accesos a Azure devops cuenta con respos migrados desactualizados.
1. Pipeline de cero papel tiene un error el agente Azure DevOps  en la ejecución.
1. El servidor obsoleto de git cuenta con un servicio de SonarQube que se da de baja.
1. Estas tareas deben resolverlas alguien del equipo con capacidades devops, hoy la de debe agregar esa capacidad a los equipos.

Riesgos

1. Sin la conexión ( VDI / Servidores Cero Papel) no se podría hacer la tarea de migración de fuentes asignada a César Ogalde.
1. Los repositorios desactualizados pueden romper los avances ya versionados
1. CI/CD no está afinada (uso de pipelines, desactualización de fuentes y pipelines con errores) dando como resultado una menor velocidad.
1. La calidad del software está comprometida ya que los procesos de revisión de test automáticos no se están ejecutando automáticamente.
1. Tareas asociadas al CI/CD podrían ser asignadas al equipo impactando el valor de negocio entregado


Próximos Pasos

1. Instalación de servicio sonar en servidor de desarrollo, activación con los fuentes para poder medir el indicador de calidad y cuando esté afinado subir a sonaque de la organización.
1. Migración de Repositorios a azure Devops, integrar con el equipo de desarrollo.
1. Revisar Pipelines configurados para ver su reactivación, reutilización o evaluar rehacer dichos pipelines
