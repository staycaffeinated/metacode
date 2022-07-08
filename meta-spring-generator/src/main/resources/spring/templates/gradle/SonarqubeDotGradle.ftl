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

def ignoredClasses =
        '**/Exception.java,' +
        '**/*Test*.java,' +
        '**/*IT.java,' +
        '**/ResourceIdTrait.java,' +
        '**/ResourceIdentity.java,' +
        '**/*Application.java,' +
        '**/*TablePopulator.java,' +
        '**/*Config.java,' +
        '**/*Configuration.java,' +
        '**/*Initializer.java,' +
        '**/*Exception.java';


sonarqube {
    properties {
        property 'sonar.coverage.exclusions', ignoredClasses
    }
}
