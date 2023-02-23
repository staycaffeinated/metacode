
<#if (project.applicationName)??>
spring.application.name=${project.applicationName}
<#else>
spring.application.name=example-service
</#if>
server.port=8080
<#if (project.basePath)??>
server.servlet.context-path=${project.basePath}
<#else>
server.servlet.context-path=/
</#if>

spring.data.mongodb.uri=mongodb://localhost:27017
spring.datasource.url=jdbc:mongodb://localhost:27017

spring.data.mongodb.database=petstore

