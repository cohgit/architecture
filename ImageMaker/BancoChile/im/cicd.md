Banco de Chile

Feedback Plan de Mejora
- La migration de Bamboo a Jenkins será un parto, se puede buscar una forma de aliviar, una herramienta que lea los planes y los traduzca a Jenkins el banco lo compraría.
- Quieren escuchar cosas de monitoreo!!, faltan manos y conocimiento. Pidieron ayuda en dynatrace, necesitan un experto.
- Varios operadores de kuberentes donde declaras tu POD y el vault clave de OCI se encarga de la sincronización (actualmente cambias una clave de base de datos debes reiniciar los contenedores del pod, como sabes los contenedores a reiniciar?). Actualmente es reactivo.

1.  identificar el flujo completo de Devops

Integration Continua

Terraform 
Ansible (le instalan java a las maquinas virtuales, o comandos desde terraform, ejemplo volúmenes nfs)


OCI y AWS

Todo esta en On-Premise

Herramientas
Bitbucket CVS Codigo Fuente
Jira
Confluence
Versionamiento Branch Base 
Bamboo
Tienen un Jenkins (están migrándose) - No se puede reutilizar un pipeline, se debe copiar y pegar el archivo en todos los microservicios, si hay algún cambio se debe hacer en todos lados.
Istio Service Mesh


OCI

Oracle Kubernetes Service

Lenguaje Programación
Java
Spring Boot

Volumenes y secretos de Kubernetes, Secret.property se puede definir los volúmenes del pod y secretos, están en un vault de OCI, hay un plan de bamboo que copia todo lo de OCI y lo instala en el cluster de Kubernetes, si cambias el vault de OCI debes ir a bamboo. 

Solution
Varios operadores de kuberentes donde declaras tu POD y el vault clave de OCI se encarga de la sincronización (actualmente cambias una clave de base de datos debes reiniciar los contenedores del pod, como sabes los contenedores a reiniciar?). Actualmente es reactivo.

External Secret Operator 

Kubetctl apply y cambiaras el secreto a mano.

Estuvo apoyando a Felipe Roa

Mejoras en Monitoreo

Faltan herramientas de monitoreo como corresponde. 

Grafana y Prometheus se les cae porque tienen 72 nodos en el cluster de desarrollo.
Están migrando a Elastic. (Hay un montón de pega por hacer, pero no lo están haciendo)

Quieren escuchar cosas de monitoreo!!
Alerta de Metricas cuando un servidor del cluster se quede sin memoria, cuando un nodo se quede sin ip, son configuraciones que se pueden hacer en el mismo OCI.

Usan Dynatrace, fue configurado para cuando se caiga un nodo les mande una notificación en canal de team. 

2. Cuantas personas hay en el area

El equipo de devsecops tiene varios equipos, hay como 36 personas. (4 a 5 células)
Jose Ramon Navarro, es devsecops es el lider de la célula (Jefe de Proyecto u otro cargo). 
Cada devsecops ve entre 3 a 10 proyectos (estan asignados). La célula se componene de 8 personas.
Oscar Eduardo Molina Catalan, oemolina@bancochile.cl
Subgerente desarrollo Cloud

3. Cual es el flujo desarrollo

	Se compila el código,
	Se ejecuta pruebas unitarias
	Validation en Veracode.
	Dockerizacion 
	Registry del banco
	Se realiza deploy

	5 ambientes,
	CHILE
	desarrollo, qa, beta (ambiente productivo no expuesto a los clientes), dr (disaster recovery), produccion
	desarrollo, (developers)
	beta (devdecops, , se puede hacer a cualquier hora)
	produccion (hay una célula de las 36 personas que se encarga de producción, fuera de horario, devsecops apoya)

	USA
	dr (hay una célula de las 36 personas que se encarga de dr, en USA ausborn, replica de producción, una vez al año apagan producción, freeze y ejecutan el dr)

4. Herramientas en cada etapa

El plan de bamboo no se crea a mano, se llena un property con los campos de:

Donde esta el proyecto en bitbucket, nombre proyecto, ejecuta un plan de bamboo donde se crea el plan para el proyecto. Modificar agente de producción. Para los planes de frontend no hay generador, hay que clonar otro frontend clonarlo y modificarlo.

No tienen herramientas DAST.
No tienen scanner de seguridad de imagen de docker. 

Prorizando en Jenkins (nueva en el banco, un par de meses)
La migration de Bamboo a Jenkins será un parto, se puede buscar una forma de aliviar, una herramienta que lea los planes y los traduzca a Jenkins el banco lo compraría.

Cuando compila lo hace en los servidores de bamboo y dockeriza lo que esta en el dockerfile. 
Estan compilando fuera del contenedor. Instalan la version de node dentro de la maquina.
Certificado en OCI están, en dynatrace no!. Dynatrace solo se usaba en producción, hoy en día en todos los ambientes