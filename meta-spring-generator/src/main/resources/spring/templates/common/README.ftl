
# Application

## Table of Contents

[Application Users: Getting Started](## Application Users: Getting Started)
[Developers: Getting Started](## Developers: Getting Started)
[Setting Up your Environment](### Setting up your environment)
[Setting up Sonarqube](#### Setting up Sonarqube)
[First Steps](### First Steps)
[Compile](#### Compile)
[Compile with Code Coverage](#### Compile and get code coverage)
[Build Docker Image](#### Build Docker Image)
[Run Locally in Docker](#### Run the application in Docker (locally))
[Check for Latest Dependency Versions](#### Check for latest library versions)

## Application Users: Getting Started

* Overview

This section discusses how end users use a deployed instance
of this application. (This section is filled in by you, the developer).class


## Developers: Getting Started

### Setting up your environment

* You must have Java 11 or higher installed
* You must have Gradle 6.7 or higher installed to build Docker images
* Sonarqube is only required for code coverage reports.

#### Setting up Sonarqube

Before code metrics can be exported to Sonarqube, the `gradle.properties` file
in this project must be updated with configuration values that match your environment.class

A local Sonarqube Docker container can be used. Generally, this command will suffice

```docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest```

Once Sonarqube is running, log in to `https://localhost:9000` using the default credentials `admin/admin`.
You will be prompted to change the default password.  Whatever new password you select, update the
`gradle.properties` file with that password.  When that's done, the code metrics from this project can
be exported to the Sonarqube Docker container.

See [Getting Started with Sonarqube in 2 Minutes](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/)
and [Running Sonarqube in Docker](https://hub.docker.com/_/sonarqube)
for additional information.

### First Steps

The code generator does not create a Gradle wrapper, so create one:

```gradle wrapper```

#### Compile

```./gradlew build```

#### Compile and get code coverage

```./gradlew clean build jacocoTestReport sonarqube```

#### Build Docker Image

```./gradlew bootBuildImage```

#### Run the application in Docker (locally)

```
./gradlew bootBuildImage
cd docker-compose
docker-compose up -d
```

A simple docker-compose file is written into the docker-compose directory.
The explicit version of the application is stated. The `latest` version keyword
is not used becuase that causes Docker to search the global Docker registry
for the latest version.  By indicating the specific version of this application,
Docker checks the local cache of images first.

#### Check for latest library versions

```./gradlew dependencyUpdates```

