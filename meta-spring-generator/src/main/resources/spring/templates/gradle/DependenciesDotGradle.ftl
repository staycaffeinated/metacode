ext {
    versions = [
        assertJ                : '${project.assertJVersion}',             // Assertion library for test cases
        junitSystemRules       : '${project.junitSystemRulesVersion}',    // JUnit extensions
        junit                  : '${project.junitVersion}',               // JUnit Jupiter
        liquibase              : '${project.liquibaseVersion}',           // Database schema initialization & evolution
        lombok                 : '${project.lombokVersion}',              // Lombok annotation processor
        log4j                  : '${project.log4jVersion}',               // Logging
        mockito                : '${project.mockitoVersion}',             // Mocking library
        r2dbc_h2               : '${project.r2dbc_h2Version}',             // r2dbc-h2
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
        assertJ                     : "org.assertj:assertj-core:$versions.assertJ",
        h2                          : "com.h2database:h2",
        jacksonDatatypeJsr310       : "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
        junitBillOfMaterial         : "org.junit:junit-bom:$versions.junit",
        junitJupiter                : "org.junit.jupiter:junit-jupiter",
        junitPlatformRunner         : "org.junit.platform:junit-platform-runner",

        // See https://stefanbirkner.github.io/system-rules/
        junitSystemRules            : "com.github.stefanbirkner:system-rules:$versions.junitSystemRules",

        log4j                       : "org.apache.logging:log4j:log4j-core:$versions.log4j",
        lombok                      : "org.projectlombok:lombok:$versions.lombok",
        liquibaseCore               : "org.liquibase:liquibase-core:$versions.liquibase",
        mockito                     : "org.mockito:mockito-core:$versions.mockito",
        r2dbc_h2                    : "io.r2dbc:r2dbc-h2:$versions.r2dbc_h2",
        postgresql                  : "org.postgresql:postgresql",
        problemSpringWeb            : "org.zalando:problem-spring-web-starter:$versions.problemSpringWeb",
        problemSpringWebFlux        : "org.zalando:problem-spring-webflux:$versions.problemSpringWeb",
        problemJacksonDataType      : "org.zalando:jackson-datatype-problem:$versions.problemJacksonDataType",
        reactorTest                 : "io.projectreactor:reactor-test:$versions.reactorTest",
        truth                       : "com.google.truth:truth:$versions.truth",

        // Spring's dependency management plugin will auto-resolve the Spring library versions
        springBootConfigProcessor   : "org.springframework.boot:spring-boot-configuration-processor",
        springBootStarterActuator   : "org.springframework.boot:spring-boot-starter-actuator",
        springBootStarterAop        : "org.springframework.boot:spring-boot-starter-aop",
        springBootStarterWeb        : "org.springframework.boot:spring-boot-starter-web",
        springBootStarterWebFlux    : "org.springframework.boot:spring-boot-starter-webflux",
        springBootStarterDataJpa    : "org.springframework.boot:spring-boot-starter-data-jpa",
        springBootStarterDataR2dbc  : "org.springframework.boot:spring-boot-starter-data-r2dbc",
        springBootStarterTest       : "org.springframework.boot:spring-boot-starter-test",
        springBootStarterValidation : "org.springframework.boot:spring-boot-starter-validation",
        springCloud                 : "org.springframework.cloud:spring-cloud-starter:$versions.springCloud",
        springDevTools              : "org.springframework.boot:spring-boot-devtools",

        testContainersBom           : "org.testcontainers:testcontainers-bom:$versions.testContainers",
        testContainersPostgres      : "org.testcontainers:postgresql",
        testContainersJupiter       : "org.testcontainers:junit-jupiter"
        ]
}
</#noparse>