
docker exec -it a3e9e6b02d04 /bin/sh

docker cp shared-lib-aop-war-1.0.0.war a3e9e6b02d04:/u01/oracle/user_projects/domains/base_domain/autodeploy


[ERROR] Failed to execute goal com.oracle.weblogic:weblogic-maven-plugin:10.3.6.0:deploy (default-cli) on project adep-forms-rest: weblogic.deploy.api.tools.deployer.DeployerException: Task 0 failed: [Deployer:149026]deploy application adep-forms-rest on AdminServer.
[ERROR] Target state: deploy failed on Server AdminServer
[ERROR] weblogic.application.ModuleException: Error: Unresolved Webapp Library references for "WebAppModule(adep-forms-rest:adep-forms-rest.war)", defined in weblogic.xml 



[Extension-Name: shared-lib-commons-war, Specification-Version: 1, Implementation-Version: 1.0.0, exact-match: true], 
[Extension-Name: shared-lib-jackson-war, Specification-Version: 1.1, Implementation-Version: 1.1.0, exact-match: true], 
[Extension-Name: shared-lib-translator-war, Specification-Version: 1, Implementation-Version: 1.0.0, exact-match: true], 
[Extension-Name: shared-lib-aop-war, Specification-Version: 1, Implementation-Version: 1.0.0, exact-match: true], 
[Extension-Name: taurus-shared-lib-log-war, Specification-Version: 1.1, Implementation-Version: 1.1.0, exact-match: true], 
[Extension-Name: taurus-shared-lib-common-war, Specification-Version: 2.2, Implementation-Version: 2.2.0, exact-match: true], 
[Extension-Name: taurus-shared-lib-reporteria-war, Specification-Version: 1.5.1, Implementation-Version: 1.5.1, exact-match: true], 
[Extension-Name: taurus-shared-lib-cdnsession-api-war, Specification-Version: 2.3, Implementation-Version: 2.3.0, exact-match: true], 
[Extension-Name: taurus-shared-lib-cdncommon-extension-war, Specification-Version: 1.1, Implementation-Version: 1.1.0, exact-match: true], 
[Extension-Name: taurus-shared-lib-cdnsession-model-war, Specification-Version: 2.11, Implementation-Version: 2.11.0, exact-match: true]
[Extension-Name: taurus-shared-lib-spring-war, Specification-Version: 1.4, Implementation-Version: 1.4.0, exact-match: true], 
