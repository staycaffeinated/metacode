dependencies {
    annotationProcessor libs.springBootConfigProcessor

    developmentOnly libs.springDevTools

    implementation libs.springBootStarterWeb
    implementation libs.springBootStarterBatch
    implementation libs.springBootStarterDataJpa

<#if (project.isWithPostgres())>
    runtimeOnly libs.postgresql
</#if>

<#if (project.isWithTestContainers())>
    testImplementation platform( libs.testContainersBom )
    testImplementation libs.testContainersJupiter
  <#if (project.isWithPostgres())> <#-- if (testcontainers && postgres) -->
    testImplementation libs.testContainersPostgres
  </#if>
</#if>

    testImplementation (libs.springBootStarterTest){
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation (platform( libs.junitBillOfMaterial ))
    testImplementation (libs.junitJupiter)
    testImplementation libs.springBatchTest
}