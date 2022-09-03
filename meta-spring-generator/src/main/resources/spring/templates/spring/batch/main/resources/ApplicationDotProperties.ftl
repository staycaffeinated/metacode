
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

<#if (project.isWithPostgres())>
# ---------------------------------
# Postgres settings
# ---------------------------------
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.id.new_generator_mappings=false

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=root
spring.datasource.password=secret

    <#if (project.schema)??>
spring.datasource.url=jdbc:postgresql://localhost:5432/${project.schema}
    <#elseif (project.applicationName)??>
spring.datasource.url=jdbc:postgresql://localhost:5432/${project.applicationName}
    <#else>
spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
    </#if>
</#if>
