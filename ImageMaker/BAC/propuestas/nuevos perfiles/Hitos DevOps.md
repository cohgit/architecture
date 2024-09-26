
### **1. Gestión del Código y Control de Versiones**

**Hito: Uso de control de versiones con repositorios en Azure DevOps**
- **Artefacto:** Repositorios en Azure DevOps configurados y en uso.
- **Resultado Esperado:** Todos los proyectos activos deben estar bajo control de versiones, con un repositorio específico por proyecto.
- **Impacto:** Asegura la trazabilidad y el control del código fuente, permitiendo una gestión eficiente de versiones y colaboraciones, lo que reduce el riesgo de conflictos de código y pérdida de información.

**Hito: Estándar de gestión de ramas según Ambiente**
- **Artefacto:** Política de ramas definida y documentada.
- **Resultado Esperado:** Todas las ramas en los repositorios siguen la convención de nombres y reglas definidas para ambientes (ej. `dev`, `qa`, `prod`).
- **Impacto:** Mejora la organización y claridad en la gestión del código, facilitando la integración continua y evitando errores al desplegar en ambientes equivocados.

### **2. Automatización de Compilación y Despliegue**

**Hito: Proceso de compilación por ambientes de manera automática**
- **Artefacto:** Pipelines de compilación configurados en Azure DevOps.
- **Resultado Esperado:** Cada commit en una rama asociada a un ambiente dispara automáticamente un proceso de compilación.
- **Impacto:** Incrementa la eficiencia del desarrollo al reducir tiempos de espera entre cambios de código y despliegues en diferentes ambientes, lo que acelera el ciclo de vida del desarrollo.

**Hito: Activar flujo de aprobaciones automáticas**
- **Artefacto:** Pipeline configurado con Gates y condiciones de aprobación automática en Azure DevOps.
- **Resultado Esperado:** Aprobaciones automáticas para despliegues en ambientes no productivos con criterios predefinidos.
- **Impacto:** Minimiza los cuellos de botella en los procesos de despliegue, manteniendo un control adecuado sin sacrificar la agilidad en los entornos de desarrollo y pruebas.

**Hito: Construcción de pipelines automáticos de despliegue en ambiente**
- **Artefacto:** Pipelines de despliegue configurados en Azure DevOps.
- **Resultado Esperado:** Despliegues en ambientes de `qa`, `staging`, y `prod` se realizan de forma automática mediante pipelines.
- **Impacto:** Aumenta la confiabilidad y consistencia en los despliegues, reduciendo el riesgo de errores humanos y asegurando que los despliegues se realicen de manera uniforme en todos los ambientes.

**Hito: Parametrizar la compilación por ambiente**
- **Artefacto:** Pipelines con parámetros configurables según ambiente.
- **Resultado Esperado:** Compilaciones adaptables a diferentes ambientes mediante la parametrización dinámica en los pipelines.
- **Impacto:** Proporciona flexibilidad en el proceso de despliegue, permitiendo adaptaciones rápidas a diferentes condiciones de ambiente, lo que reduce errores y facilita la gestión de configuraciones.

### **3. Calidad del Código y Pruebas Automáticas**

**Hito: Activar plan de cobertura SonarQube**
- **Artefacto:** Integración de SonarQube con pipelines en Azure DevOps.
- **Resultado Esperado:** Cada compilación genera un reporte de cobertura con métricas clave (coverage, code smells, deuda técnica).
- **Impacto:** Mejora la calidad del código al asegurar que se mantengan altos estándares de cobertura y se identifiquen problemas potenciales desde el inicio, lo que disminuye la deuda técnica y los riesgos en producción.

**Hito: Elaborar pruebas unitarias automáticas**
- **Artefacto:** Suite de pruebas unitarias implementadas y versionadas.
- **Resultado Esperado:** Pruebas unitarias automatizadas que cubren al menos el 70% del código fuente.
- **Impacto:** Mejora la calidad del software al detectar y corregir errores en etapas tempranas del desarrollo, lo que reduce los costos y tiempos de remediación en etapas posteriores.

**Hito: Incorporar a los pipelines las pruebas automáticas**
- **Artefacto:** Integración de pruebas unitarias y de integración en pipelines de Azure DevOps.
- **Resultado Esperado:** Cada pipeline ejecuta pruebas automáticas antes del despliegue.
- **Impacto:** Asegura que solo se despliegue código de alta calidad, evitando la introducción de errores en producción y mejorando la confianza en cada despliegue.

**Hito: Indicadores de calidad automáticos**
- **Artefacto:** Dashboards de calidad configurados en Azure DevOps/SonarQube.
- **Resultado Esperado:** Visualización en tiempo real de métricas de calidad, como cobertura de código, duplicación, y vulnerabilidades.
- **Impacto:** Proporciona una visibilidad constante del estado de la calidad del código, permitiendo a los equipos tomar decisiones proactivas para mejorar la calidad y reducir riesgos operativos.


## Argumento Servicio de Apoyo DevSecOPS Transversal
1.- Se exige a los nuevos proyectos los quality gates ( .. )
2.- Los proyectos legados que no cumplerán con esta calidad necesitan otra ruta devsecops
3.- Se debe aumentar la adoción DevOps en las células
4.- La capacidad de central tiene prioridades para los nuevos proyectos.
5.- Se activo capacidad devops a celula cero papel, propuesta es incoprorada en esta oferta.






tira a la propuesta, la distribucion 