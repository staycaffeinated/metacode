dependencies {
    annotationProcessor libs.springBootConfigProcessor

    developmentOnly libs.springDevTools

    implementation libs.springBootStarterActuator
    implementation libs.springBootStarterWeb
    implementation libs.springBootStarterDataJpa
    implementation libs.problemSpringWeb
    implementation libs.problemJacksonDataType
<#if (project.isWithLiquibase)??>
    implementation libs.liquibaseCore
</#if>

<#if (project.isWithPostgres)??>
    runtimeOnly libs.postgresql
<#else>
    runtimeOnly libs.h2
</#if>

<#if (project.isWithTestContainers)??>
    testImplementation libs.springCloud
    testImplementation platform( libs.testContainersBom )
    testImplementation libs.testContainersJupiter
    <#if (project.isWithPostgres)??> <#-- if (testcontainers && postgres) -->
    testImplementation libs.testContainersPostgres
    </#if>
<#else>
    <#-- if testcontainers aren't in use, default to using H2 to enable -->
    <#-- out-of-the-box tests to work until a QA DB is set up by the developer. -->
    testRuntimeOnly libs.h2
</#if>

    testImplementation (libs.springBootStarterTest){
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation (platform( libs.junitBillOfMaterial ))
    testImplementation (libs.junitJupiter)
}