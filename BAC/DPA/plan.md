CI/CD
OK 0.- (1sem) Inducción a proyectos ansibles y propuestas de devops (mejora de agentes awx desa y produccion).
OK 1.- (3sem) Creación de Agentes Linux con Capacidades Amsible CLI AWX.
OK 1.1.- Creación de proyecto Cloud_DEV terraform maquinas linux redhat en desarrollo
OK 1.2.- Configuración de Agente Ansible LInux Desa.
OK 1.2.1.- Código Provisión de Agente DevOps y registro
OK 1.2.2.- Código Provisión de AWX (Cliente Open y Luego Ansible Tower).
OK 1.2.3.- Código Provisión de PowerShell (en Linux).
OK 1.2.4.- Código Provisión de Git (algo lo eliminó por defecto de las máquinas).
OK 1.2.5.- Solicitar contenedor estado terraform.
OK 1.2.6.- Ejecución de pipelines de terraform (plan y apply).
OK 1.2.7.- Validar y cambiar template (Ansible) con Agente Desarrollo.
OK 1.3.- Creación de proyecto Cloud_PROD terraform máquinas linux redhat en producción.
OK 1.3.1.- Configuración proyecto terraform productivo
OK 1.3.2.- Configuración cambios de redes, keyvault, estado, etc.
OK 1.3.3.- Armado de Ramas, Políticas y solicitud de VB para ejecutar.
OK 1.4.- Ejecución de Flujo CI/CD Beta en agente desarrollo.
OK 2.- (1sem) Flujo de Pasos de Configuración Desa a STG y STG a Producción en Templates Beta.
OK 2.1.- Cambios de ejecución template de conexión ssh a script directo.
OK 2.2.- Generación de artefactos de configuración y publicados en el build.
OK 3.- (2sem) Flujo CICD ANSIBLE en Proyecto IBMi.
OK 3.1.- Configuración Ambientes
OK 3.2.- Configuración de variables
OK 3.3.- Problema con Variables secretas con $ en la password.
OK 3.4.- Problema de exportación de archivo.
OK 3.5.- Aplica Flujo Beta en desa y producción.
OK 4.- (1sem) Mejores Prácticas DevOps Automatizaciones Ansible
OK 4.1.- Presentación Mejores Prácticas.
OK 4.2.- Diagrama Levantamiento de políticas (Ambientes, Ramas, Aprobadores).
5.- (6sem) Implementación.
5.1.- (1sem) Documentación pipeline de creación Proyectos Ansibles según buenas prácticas.
5.2.- (1sem) Definición y estructuras de documentación (MarkDown) y Validación en pipeline.
5.3.- (1sem) Incorporar Tarea de Validación de código en template pipeline.
             - el uso de ansible-lint (On RHEL, ansible-lint package is part of "Red Hat Ansible Automation Platform" subscription, which needs to be activated.)
             - la otra alternativa es usar pip y librería abierta pero da error en gcc (pip3 install ansible-lint)
             Command "python setup.py egg_info" failed with error code 1 in /tmp/pip-build-bppb8vl1/ruamel.yaml.clib/
5.4.- (1sem) Agregar Environment's con restricción de ramas, templates.
5.5.- (1sem) Agregar pruebas unitarias de desarrollo al pipeline
5.6.- (1sem) Complementar requerimientos generales heredados con los específicos de Ansible, sobre todo en autorizaciones

Roadmap Arquitectura
OK 1.- Apoyo en Revision de Roadmap Arquitectura DevOPS (Jonathan mendez)
2.- Apoyo en visión Backstage (Terraform Cloud, gestión de estados)