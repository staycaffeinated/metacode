ext {
    versions = [
        apacheKafka            : '${project.apacheKafkaVersion}',         // Apache's Kafka libraries
        assertJ                : '${project.assertJVersion}',             // Assertion library for test cases
        h2                     : '${project.h2Version}',
        junitSystemRules       : '${project.junitSystemRulesVersion}',    // JUnit extensions
        junit                  : '${project.junitVersion}',               // JUnit Jupiter
        liquibase              : '${project.liquibaseVersion}',           // Database schema initialization & evolution
        lombok                 : '${project.lombokVersion}',              // Lombok annotation processor
        log4j                  : '${project.log4jVersion}',               // Logging
        mockito                : '${project.mockitoVersion}',             // Mocking library
        postgresql             : '${project.postgresqlVersion}',          // PostgreSQL library
        r2dbcH2                : '${project.r2dbc_h2Version}',            // r2dbc-h2
        r2dbcSpi               : '${project.r2dbc_spiVersion}',           // r2dbc-spi
        springBoot             : '${project.springBootVersion}',          // Spring Boot
        springCloud            : '${project.springCloudVersion}',         // Spring Cloud
        problemJacksonDataType : '${project.problemJacksonDataTypeVersion}',  // Zalando's Jackson DataType
        problemSpringWeb       : '${project.problemSpringWebVersion}',    // Zalando's Problem API
        reactorTest            : '${project.reactorTestVersion}',         // reactor-test library
        testContainers         : '${project.testContainersVersion}',      // Test containers for integration testing
        truth                  : '${project.truthVersion}'                // Google's assertion library
        ]


<#noparse>
    libs = [
        apacheKafka                     : "org.apache.kafka:kafka_2.13:$versions.apacheKafka",
        apacheKafkaClients              : "org.apache.kafka:kafka-clients:$versions.apacheKafka",
        assertJ                         : "org.assertj:assertj-core:$versions.assertJ",
        h2                              : "com.h2database:h2:$versions.h2",
        jacksonDatatypeJsr310           : "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
        junitBillOfMaterial             : "org.junit:junit-bom:$versions.junit",
        junitJupiter                    : "org.junit.jupiter:junit-jupiter",
        junitPlatformRunner             : "org.junit.platform:junit-platform-runner",

        // See https://stefanbirkner.github.io/system-rules/
        junitSystemRules                : "com.github.stefanbirkner:system-rules:$versions.junitSystemRules",
        kafkaClients                    : "org.apache.kafka:kafka-clients",
        kafkaStreams                    : "org.apache.kafka:kafka-streams",
        kafkaStreamsTest                : "org.apache.kafka:kafka-streams-test-utils",
        log4j                           : "org.apache.logging:log4j:log4j-core:$versions.log4j",
        lombok                          : "org.projectlombok:lombok:$versions.lombok",
        liquibaseCore                   : "org.liquibase:liquibase-core:$versions.liquibase",
        mockito                         : "org.mockito:mockito-core:$versions.mockito",
        r2dbcH2                         : "io.r2dbc:r2dbc-h2:$versions.r2dbcH2",
        r2dbcSpi                        : "io.r2dbc:r2dbc-spi:$versions.r2dbcSpi",
        postgresql                      : "org.postgresql:postgresql:$versions.postgresql",
        problemSpringWeb                : "org.zalando:problem-spring-web-starter:$versions.problemSpringWeb",
        problemSpringWebFlux            : "org.zalando:problem-spring-webflux:$versions.problemSpringWeb",
        problemJacksonDataType          : "org.zalando:jackson-datatype-problem:$versions.problemJacksonDataType",
        reactorTest                     : "io.projectreactor:reactor-test:$versions.reactorTest",
        truth                           : "com.google.truth:truth:$versions.truth",

        // Spring's dependency management plugin will auto-resolve the Spring library versions
        springBootConfigProcessor       : "org.springframework.boot:spring-boot-configuration-processor",
        springBootStarterActuator       : "org.springframework.boot:spring-boot-starter-actuator",
        springBootStarterAop            : "org.springframework.boot:spring-boot-starter-aop",
        springBootStarterBatch          : "org.springframework.boot:spring-boot-starter-batch",
        springBootStarterWeb            : "org.springframework.boot:spring-boot-starter-web",
        springBootStarterWebFlux        : "org.springframework.boot:spring-boot-starter-webflux",
        springBootStarterDataJpa        : "org.springframework.boot:spring-boot-starter-data-jpa",
        springBootStarterDataR2dbc      : "org.springframework.boot:spring-boot-starter-data-r2dbc",
        springBootStarterTest           : "org.springframework.boot:spring-boot-starter-test",
        springBootStarterValidation     : "org.springframework.boot:spring-boot-starter-validation",
        springBatchTest                 : "org.springframework.batch:spring-batch-test",
        springCloud                     : "org.springframework.cloud:spring-cloud-starter:$versions.springCloud",
        springCloudBinderKafkaStreams   : "org.springframework.cloud:spring-cloud-stream-binder-kafka-streams:$versions.springCloud",
        springCloudStarterSteamKafka    : "org.springframework.cloud:spring-cloud-starter-stream-kafka:$versions.springCloud",
        springDevTools                  : "org.springframework.boot:spring-boot-devtools",
        springKafka                     : "org.springframework.kafka:spring-kafka",
        springKafkaTest                 : "org.springframework.kafka:spring-kafka-test",

        testContainersBom               : "org.testcontainers:testcontainers-bom:$versions.testContainers",
        testContainersKafka             : "org.testcontainers:kafka",
        testContainersPostgres          : "org.testcontainers:postgresql",
        testContainersJupiter           : "org.testcontainers:junit-jupiter"
        ]
}
</#noparse>
ext['junit-jupiter.version'] = '${project.junitVersion}'
ext['snakeyaml.version'] = '1.33'  // force this upgrade to fix known CVEs