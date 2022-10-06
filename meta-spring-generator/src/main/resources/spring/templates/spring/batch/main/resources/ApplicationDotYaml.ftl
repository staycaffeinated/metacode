server:
  port: 8080
<#if (project.basePath)??>
  servlet.context-path: ${project.basePath}
<#else>
  servlet.context-path: /
</#if>

spring:
  main:
    banner-mode: "off"
    allow-circular-references: false
<#if (project.applicationName)??>
  application.name: ${project.applicationName}
<#else>
  application.name: batch-service
</#if>
  batch:
    jdbc:
        # The schema has to be defined, otherwise Batch cannot determine the database type.
        # This is the SQL script SpringBatch runs to create its own tables, like
        # BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_JOB_EXECUTION_PARAMS, etc.
        # See github.com/spring-projects/spring-batch/issues/1026
        schema: classpath:org/springframework/batch/core/schema-postgresql.sql

        # This property encourages Spring to create the spring-batch-specific database tables
        initialize-schema: always
  datasource:
<#if (project.isWithPostgres())>
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/postgres
    # These are example credentials; change them
    username: postgres
    password: postgres
  jpa:
    # Vendor list is found here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/vendor/Database.html
    vendor: POSTGRESQL
    properties:
      hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect
  sql:
    init:
      # If you want this Batch application to create its own schema, you can do something like this:
      # schema-location: classpath:database/postgresql/create-schema.sql
</#if>
logging:
  level:
    root: INFO
    org.springframework.batch: INFO
  management:
    endpoint:
      health:
        probes:
          enabled: true
    endpoints:
      web:
        base-path: /_internal
