
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

<#-- define the jdbc driver -->
<#if (project.reactiveMongo)??>
# Reactive MongoDB
# spring.datasource.driver-class-name=org.postgresql.Driver
</#if>
<#-- define the jdbc url -->
<#if (project.isWithPostgres())>
    <#if (project.schema)??>
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/postgres${project.schema}
    <#else>
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/postgres
    </#if>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.r2dbc.username=postgres
spring.r2dbc.password=postgres
<#else>
    <#if (project.schema)??>
spring.r2dbc.url=r2dbc:h2:mem:///${project.schema}
    <#elseif (project.applicationName)??>
spring.r2dbc.url=r2dbc:h2:mem:///${project.applicationName}
    <#else>
spring.r2dbc.url=r2dbc:h2:mem:///testdb
    </#if>
spring.r2dbc.username=root
spring.r2dbc.password=secret
</#if>

