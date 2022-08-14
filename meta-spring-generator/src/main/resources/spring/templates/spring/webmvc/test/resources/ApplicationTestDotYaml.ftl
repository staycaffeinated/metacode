spring:
    config:
        activate:
            on-profile: test

<#if (project.isWithPostgres())>
    datasource:
        driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
    <#noparse>
        url: jdbc:tc:postgresql:9.6.8:///testdb?currentSchema=public
        username: postgres
        password: postgres
        initialization-mode: embedded
    </#noparse>
    jpa:
        database: POSTGRESQL
        properties:
            hibernate:
                dialect: org.hibernate.dialect.PostgreSQLDialect
                id:
                    new_generator_mappings: false
        show-sql: true
        generate-ddl: true
<#else> <#-- H2 configuration -->
    datasource:
        driver-class-name: org.h2.Driver
        url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
        username: sa
        password: password
        initialization-mode: embedded
    jpa:
        database-platform: org.hibernate.dialect.H2Dialect
        generate-ddl: true
</#if>
    h2:
        console:
            enabled: true
            path: /h2-console
            settings:
                trace: false
                web-allow-others: false
