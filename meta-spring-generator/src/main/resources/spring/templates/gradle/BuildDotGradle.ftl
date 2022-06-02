plugins {
    id 'org.springframework.boot' version '${project.springBootVersion}'
    id 'io.spring.dependency-management' version '${project.springDependencyManagementVersion}'
    id 'com.coditory.integration-test' version '${project.coditoryPluginVersion}'
    id 'java'
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
<#else>
    <#include "SpringWebMvcDependencies.ftl">
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
    to {
        image = '${project.applicationName}'
        tags = [ '0.0.1' ]
    }
}
