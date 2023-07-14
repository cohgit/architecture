## use .m2 dependency in WSL
ln -s /mnt/c/Users/caogaldh/.m2 ~/.m2


## ejecuta la eliminación de archivos
mvn install exec:java@delete-mails -Dhttps.proxyHost=10.120.136.40 -Dhttps.proxyPort=8080 -Dhttps.nonProxyHosts=localhost,127.0.0.1,::1 -Dhttp.proxyHost=10.120.136.40  -Dhttp.proxyPort=8080  -Dhttp.nonProxyHosts=localhost,127.0.0.1,::1


mvn spring-boot:run -Dspring.config.location=file:/Users/imagemaker/dev_p/cronicare.net/cronicare-git-ops/backend/.env.develop