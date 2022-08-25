:toc: auto

= A Guide to Using this Application

== Developers Guide
=== Prerequisites

* You must have Java 11 or higher installed to compile and run the project
* You must have Gradle 6.7 or higher installed to build the project
* SonarQube is only required for code coverage

=== First Steps

The code generator does not create a Gradle wrapper, so create one:

```[shell]
gradle wrapper
```

==== Compile the Code

```[shell]
./gradlew build
```

==== Build with Code Coverage

Update the ```gradle.properties``` file and
set the `systemProp.sonar` properties to what's appropriate
for your environment.  Then run this command:

```[shell]
./gradlew clean build sonarqube
```

==== Build a Docker Image

```[shell]
./gradlew jibDockerBuild
```

==== Run the application in Docker

```[shell]
# build the docker image, if you haven't already
./gradlew jibDockerBuild

# navigate to the docker-compose subdirectory
cd docker-compose

# run docker-compose to launch the container
docker-compose up -d
```

==== Check for the latest dependency updates
Ben Manes' `versions` plugin is included in the `build.gradle` file.
This plugin checks for new versions of any dependencies. To check, run

```[shell]
./gradlew dependencyUpdates
```

==== Setting Up SonarQube

Before code metrics can be exported to Sonarqube, the `gradle.properties` file
in this project must be updated with configuration values that match your environment.class

A local Sonarqube Docker container can be used. Generally, this command will suffice:

```[shell]
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Besides this default Sonarqube image, another good choice is the Bitnami image:
https://hub.docker.com/r/bitnami/sonarqube[Bitnami Sonarqube on DockerHub]

Once Sonarqube is running, login to https://localhost:9000 using the provider's credentials
(usually admin/admin for Sonarqube's image; admin/bitnami for Bitnami's image).

You will be prompted to change the default password.  Whatever new password you select, update the
`gradle.properties` file with that password.  When that's done, the code metrics from this project can
be exported to the Sonarqube Docker container.

See
https://docs.sonarqube.org/latest/setup/get-started-2-minutes/[Getting Started with Sonarqube in 2 Minutes]
and https://hub.docker.com/_/sonarqube[Running Sonarqube in Docker]

for additional information.


