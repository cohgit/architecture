docker login -u cesarogalde -p Or0BCNp20c/9LYyiQo3/D0gRCAFt1JXjl87krS/BRH+ACRATgc8r huggaacrdev.azurecr.io



Add to pom.xml

<plugin>
  <groupId>com.microsoft.azure</groupId>
  <artifactId>azure-webapp-maven-plugin</artifactId>
  <version>2.5.0</version>
</plugin>

Run 

./mvnw azure-webapp:config


add to pom.xml

        <region>eastus2</region>
        <resourceGroup>rg-hugga-dev-v2.0</resourceGroup>

    <appSettings>
        <property>
            <name>PORT</name>
            <value>8080</value>
        </property>
        <property>
            <name>WEBSITES_PORT</name>
            <value>8080</value>
        </property>
        <property>
            <name>WEBSITES_CONTAINER_START_TIME_LIMIT</name>
            <value>600</value>
        </property>
    </appSettings>

add to application properties 

quarkus.package.type=uber-jar

If you made any changes to the pom.xml file, rebuild the JAR file using the following command:


Deploy your web app to Azure by using the following command:654



./mvnw install -DskipTests -DtenantId=ca17d960-f14a-4694-93c3-4dec0a12781a -DresourceGroup=rg-hugga-dev-v2.0 azure-webapp:deploy


 <plugin> 
        <groupId>com.microsoft.azure</groupId>  
        <artifactId>azure-webapp-maven-plugin</artifactId>  
        <version>1.24.0</version>  
        <configuration>
          <schemaVersion>v2</schemaVersion>
          <subscriptionId>702cf96d-4910-446c-b26a-002ca67c2505</subscriptionId>
          <resourceGroup>rg-hugga-dev-v2.0</resourceGroup>
          <appName>hugga-ms-create-user-azf</appName>
          <pricingTier>B1</pricingTier>
          <region>eastus</region>
          <runtime>
            <os>Linux</os>
            <javaVersion>Java 17</javaVersion>
            <!--webContainer>Java SE</webContainer-->
          </runtime>
          <appSettings>
              <property>
                  <name>PORT</name>
                  <value>8080</value>
              </property>
              <property>
                  <name>WEBSITES_PORT</name>
                  <value>8080</value>
              </property>
              <property>
                  <name>WEBSITES_CONTAINER_START_TIME_LIMIT</name>
                  <value>600</value>
              </property>
          </appSettings>
          <deployment>
            <resources>
              <resource>
                <directory>${project.basedir}/target</directory>
                <includes>
                  <include>*.jar</include>
                </includes>
              </resource>
            </resources>
          </deployment>
        </configuration>
      </plugin> 




https://hugga-data-adapter-afz.azurewebsites.net/
https://authorization-manager-afz.azurewebsites.net/
https://hugga-ms-read-users-afz.azurewebsites.net/
https://hugga-ms-create-user-azf.azurewebsites.net/



az webapp config set --resource-group rg-hugga-dev-v2.0 -n hugga-ms-survey --remote-debugging-enabled=false

az webapp create-remote-connection --subscription 702cf96d-4910-446c-b26a-002ca67c2505 --resource-group rg-hugga-dev-v2.0 -n hugga-ms-survey &

az webapp ssh --resource-group rg-hugga-dev-v2.0 -n hugga-ms-survey

az webapp log tail --resource-group rg-hugga-dev-v2.0 -n hugga-ms-survey


docker run -d --expose=8080 --name hugga-ms-survey_0_b5cc7b36 -e PORT=8080 -e WEBSITES_PORT=8080 -e WEBSITE_SITE_NAME=hugga-ms-survey -e WEBSITE_AUTH_ENABLED=False -e WEBSITE_ROLE_INSTANCE_ID=0 -e WEBSITE_HOSTNAME=localhost -e WEBSITE_INSTANCE_ID=3a2fd09a8218955a9d75f0d5754a43057c1c3d98af105301a8c54f762259ba11 -e WEBSITE_USE_DIAGNOSTIC_SERVER=False mcr.microsoft.com/azure-app-service/java:17-java17_220406005815


docker run -d --expose=8080 --name huggalnxwebappqa_0_6b463a11 -e WEBSITE_SITE_NAME=huggalnxwebappqa -e WEBSITE_AUTH_ENABLED=False -e WEBSITE_ROLE_INSTANCE_ID=0 -e WEBSITE_HOSTNAME=huggalnxwebappqa.azurewebsites.net -e WEBSITE_INSTANCE_ID=9b591136678f6fad255b090d443330d34ff88e64677fd4d53ffcdfcc5def44b4 -e WEBSITE_USE_DIAGNOSTIC_SERVER=True mcr.microsoft.com/appsvc/node:18-lts_20230228.2.tuxprod /home/site/wwwroot/node_modules/next/dist/bin/next start

az webapp log tail --resource-group rg-hugga-qa-v2.0 -n huggalnxwebappqa