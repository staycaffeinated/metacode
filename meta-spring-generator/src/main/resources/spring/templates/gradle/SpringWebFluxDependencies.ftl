dependencies {
    annotationProcessor libs.springBootConfigProcessor

    developmentOnly libs.springDevTools

    implementation libs.jacksonDatatypeJsr310
    implementation libs.r2dbc_h2
    implementation libs.springBootStarterDataR2dbc
    implementation libs.springBootStarterAop
    implementation libs.springBootStarterActuator
    implementation libs.springBootStarterWebFlux
    implementation libs.springBootStarterValidation
    implementation libs.problemSpringWebFlux
    implementation libs.problemJacksonDataType

    runtimeOnly libs.h2

    testAnnotationProcessor libs.lombok
    testCompileOnly libs.lombok
    testImplementation (libs.springBootStarterTest){
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation (platform( libs.junitBillOfMaterial ))
    testImplementation libs.junitJupiter
    testImplementation libs.reactorTest
}