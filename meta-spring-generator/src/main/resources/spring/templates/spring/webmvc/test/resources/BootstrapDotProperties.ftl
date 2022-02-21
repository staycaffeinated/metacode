# bootstrap properties
<#if (project.testcontainers)?? && (project.postgres)??>
embedded.postgresql.enabled=true
embedded.postgresql.dockerImage=postgres:latest
embedded.postgresql.user=postgres
embedded.postgresql.password=root
embedded.postgresql.host=localhost
embedded.postgresql.port=5432
embedded.postgresql.schema=testdb
embedded.postgresql.url=jdbc:tc:postgresql:9.6.8:///testdb?currentSchema=public
</#if>
