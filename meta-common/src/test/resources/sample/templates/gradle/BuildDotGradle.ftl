apply from: "gradle/standard-setup.gradle"      // standard project set-up

apply plugin: 'io.spring.dependency-management'

version='0.0.1'

// --------------------------------------------------------------------------------
// Enforce Java version
// --------------------------------------------------------------------------------
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}


