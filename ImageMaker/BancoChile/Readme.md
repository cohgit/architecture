## Credenciales 

CheckList de Conexiones
1.- Usuario de dominio  (https://portalbch.bancochile.cl:11443/my.policy)
2.- MFA Configurado (https://aka.ms/mfasetup)
3.- Teams (https://teams.microsoft.com/)
4.- Correo (https://outlook.office.com/mail/)
5.- VPN [Vpn_ext_desarrollo_mfa] (https://bchmfa.bancochile.cl/)
6.- Jira [jira-users] (http://jira.bch.bancodechile.cl:8080/)
7.- Confluence [confluence-users] (https://confluence.bch.bancodechile.cl:8443)
8.- BitBucket [cdn-apc2, Developers-CDN](http://bitbucket.bch.bancodechile.cl:7990)
9.- Bamboo [bamboo-users] (http://bamboo.bch.bancodechile.cl:8085/)



## Fuentes Requerimientos de subida de documentos
[16:32] Joan Delgado Delgado Torres

FRONT 

lyra-header
http://bitbucket.bch.bancodechile.cl:7990/projects/LYRA/repos/lyra-header/browse

lyra-excepciones
http://bitbucket.bch.bancodechile.cl:7990/projects/LYRA/repos/lyra-gestion-excepciones/browse

bch-excepciones
http://bitbucket.bch.bancodechile.cl:7990/projects/LYRA/repos/bch-excepciones/browse

 

BACKEND

ms-servicio-excepciones
http://bitbucket.bch.bancodechile.cl:7990/projects/CDNOCI/repos/ms-servicios-excepciones/browse

bpm-servicios-excepciones
http://bitbucket.bch.bancodechile.cl:7990/projects/CDNOCI/repos/bpm-servicios-excepciones/browse

proxy-oci
http://bitbucket.bch.bancodechile.cl:7990/projects/SMC/repos/proxy-oci/browse

 
bd
http://bitbucket.bch.bancodechile.cl:7990/projects/CDNOCI/repos/liquibase-cdn-excepciones/browse


Catálogos

catalogo-api-connect
http://bitbucket.bch.bancodechile.cl:7990/projects/TC/repos/catalogo-api-connect/browse/api-connect-oci-cred.properties?at=refs%2Fheads%2Frelease

Catalogo-endpoints-service
http://bitbucket.bch.bancodechile.cl:7990/projects/TC/repos/catalogo-endpoints-services/browse



Dependencias y ambientes necesarios para los componentes de Taurus , Orion y bch

ln -s /Users/imagemaker/dev/bch/taurus-venta-empresas /Users/imagemaker/dev/bch/bch-venta-productos/vendor/

+-- node-v10.15.3 
+-- angular@1.3.20
+-- bower@1.8.8
+-- bower-nexus3-resolver@1.0.2
+-- grunt-karma@0.8.3
+-- grunt-cli@1.2.0
+-- grunt@0.4.5
+-- mversion@2.0.1
+-- npm@6.4.1

npm install sin vpn
npm install con vpn
npm uninstall -g bower-nexus3-resolver
npm install -g bower-nexus3-resolver@1.0.2
npm install -g bower@1.8.8
bower cache clean
bower install