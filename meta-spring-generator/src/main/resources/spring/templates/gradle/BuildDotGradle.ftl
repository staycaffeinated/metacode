plugins {
    id 'org.springframework.boot' version '${project.springBootVersion}'
    id 'io.spring.dependency-management' version '${project.springDependencyManagementVersion}'
    id 'jvm-test-suite'
    id 'java'
    id 'java-test-fixtures'
    id 'idea'
    id 'jacoco'
    id 'org.sonarqube' version '${project.sonarqubeVersion}'
    id 'com.github.ben-manes.versions' version '${project.benManesPluginVersion}'
    id 'com.google.cloud.tools.jib' version '${project.jibPluginVersion}'
    id 'com.diffplug.spotless' version '${project.spotlessVersion}'
    id 'io.freefair.lombok' version '${project.lombokPluginVersion}'
}

apply from: "gradle/standard-setup.gradle"      // standard project set-up

apply plugin: 'io.spring.dependency-management'

version='0.0.1'

// --------------------------------------------------------------------------------
// Enable compiling with a specific Java version,
// independent of the developer's current Java version.
// --------------------------------------------------------------------------------
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

// --------------------------------------------------------------------------------
// Configuration
// --------------------------------------------------------------------------------
configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom developmentOnly
    }
    compileOnly {
        extendsFrom annotationProcessor
    }
}

// --------------------------------------------------------------------------------
// Repositories
// --------------------------------------------------------------------------------
repositories {
    mavenCentral()
}


// --------------------------------------------------------------------------------
// Dependencies
// --------------------------------------------------------------------------------
<#if (project.isWebFlux())>
    <#include "SpringWebFluxDependencies.ftl">
<#elseif (project.isWebMvc())>
    <#include "SpringWebMvcDependencies.ftl">
<#elseif (project.isSpringBatch())>
    <#include "SpringBatchDependencies.ftl">
<#else>
    <#include "SpringBootDependencies.ftl">
</#if>


// --------------------------------------------------------------------------------
// Make all tests use JUnit 5
// --------------------------------------------------------------------------------
tasks.withType(Test) {
    useJUnitPlatform()
    testLogging { events "passed", "skipped", "failed" }
}

// --------------------------------------------------------------------------------
// Jib specific configuration for this application
// --------------------------------------------------------------------------------
jib {
    from {
        // There is no specific dependency on the Corretto image.
        // The default OpenJDK image was deprecated, so this image was picked.
        image = 'amazoncorretto:19-alpine3.16'
    }
    to {
        image = '${project.applicationName}'
        tags = [ '0.0.1' ]
    }
}
