
<#if (project.applicationName)??>
spring.application.name=${project.applicationName}
<#else>
spring.application.name=example-service
</#if>
server.port=8080
<#if (project.basePath)??>
spring.webflux.base-path=${project.basePath}
<#else>
spring.webflux.base-path=/
</#if>
spring.main.web-application-type=reactive

# Obfuscate the /actuator endpoint
# Health probes enable a liveness check, and a readiness check.
# Docker containers are commonly deployed via Kubernetes.
# These health probes enable K8S to monitor the health of this service.
# If this service is deployed via K8S, the K8S deployment.yaml should
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

spring.datasource.username=root
spring.datasource.password=secret
<#-- define the jdbc driver -->
<#if (project.reactiveMongo)??>
# Reactive MongoDB
# spring.datasource.driver-class-name=org.postgresql.Driver
</#if>
<#-- define the jdbc url -->
<#if (project.isWithPostgres())>
    <#if (project.schema)??>
spring.datasource.url=jdbc:postgresql://localhost:5432/${project.schema}
    <#else>
spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
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

