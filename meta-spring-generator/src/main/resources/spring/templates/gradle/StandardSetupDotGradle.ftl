apply from: "gradle/dependencies.gradle"
apply from: "gradle/docker.gradle"
// Be aware that the testing suites must be defined
// before sonarqube can reference them.
apply from: "gradle/testing-suites.gradle"
apply from: "gradle/sonarqube.gradle"
apply from: "gradle/lint.gradle"
apply from: "gradle/spotless.gradle"