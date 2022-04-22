// --------------------------------------------------------------------------------
// Jacoco and Sonarqube configuration
// --------------------------------------------------------------------------------
test {
    finalizedBy jacocoTestReport
}

jacocoTestReport {
    dependsOn test
    dependsOn integrationTest
    reports {
        xml.enabled(true)   // sonarqube needs xml format
        html.enabled(true)  // for our local viewing pleasure
    }
}

sonarqube {
    properties {
        // Exclude: exceptions, tests, configurations, the main application class, and classes auto-completed by Lombok
        property 'sonar.coverage.exclusions', '**/*Exception.java,**/*Test*.java,**/*IT.java,**/RestfulResource.java,**/*Application.java,**/ServletInitializer.java,**/ResourceIdentity.java,**/*Config.java,**/*Configuration.java,**/*Initializer.java'
    }
}
