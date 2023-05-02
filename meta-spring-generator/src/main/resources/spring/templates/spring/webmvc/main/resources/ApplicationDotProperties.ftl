
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

# -------------------------------------------------------------------------------------------------------
# These properties are present to handle NoHandlerFoundException
# which occurs, for instance, if an invalid path is encountered.
# An invalid path won't resolve to any controller method and thus
# raise an error that's handled by the DefaultHandlerExceptionResolver.
# Credit to:
# https://stackoverflow.com/questions/36733254/spring-boot-rest-how-to-configure-404-resource-not-found
# https://reflectoring.io/spring-boot-exception-handling/
# -------------------------------------------------------------------------------------------------------
spring.mvc.throw-exception-if-not-handler-found=true
spring.web.resources.add-mappings=false


# Obfuscate the /actuator endpoint, which is the default health probe.
# Health probes enable a liveness check and a readiness check.
# Since Docker containers are commonly deployed via Kubernetes,
# these health probes enable Kubernetes to monitor the health of this service.
# If this service is deployed via Kubernetes, the Kubernetes deployment.yaml should
# include:
#   livenessProbe:
#     httpGet:
#       path: /_internal/health/liveness
#       port: 8080
#   readinessProbe:
#     httpGet:
#       path: /_internal/health/readiness
#       port: 8080
management.endpoints.web.base-path=/_internal
management.endpoint.health.probes.enabled=true

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.id.new_generator_mappings=false
<#if (project.isWithPostgres())>
spring.jpa.database=POSTGRESQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.id.new_generator_mappings=false
</#if>


<#-- define the jdbc driver -->
<#if (project.isWithPostgres())>
# POSTGRES
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
<#else>
# H2
spring.datasource.username=root
spring.datasource.password=secret
spring.datasource.driver-class-name=org.h2.Driver
</#if>
<#-- define the jdbc url -->
<#if (project.isWithPostgres())>
    <#if project.schema?? && project.schema?has_content>
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?currentSchema=${project.schema}
    <#else>
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    </#if>
<#else>
    <#if (project.schema)??>
spring.datasource.url=jdbc:h2:mem:${project.schema}
    <#elseif (project.applicationName)??>
spring.datasource.url=jdbc:h2:mem:${project.applicationName}
    <#else>
spring.datasource.url=jdbc:h2:mem:testdb
    </#if>
</#if>
<#if (project.isWithLiquibase())>
# Liquibase
# Enabled is 'true' by default; change it to 'false' to turn if off
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
</#if>
