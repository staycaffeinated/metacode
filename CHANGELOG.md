
## [Unreleased]

* Improvements 
  * Added jakarta.persistence-api library to WebFlux and WebMvc build.gradle's


## [6.0.0] - 2022-12-31

* Features:
  * Upgrade to Spring Boot 3, Spring Framework 6.
    Generated code now targets Spring Framework 6 and Spring Boot 3.
    Naturally, this required that some other dependencies be updated.
    With Spring Framework 6, be aware that the 'jakarta' namespace replaces the 'javax'
    namespace; for example, 'javax.persistence' is now 'jakarta.persistence'.
  * Changed the SecureRandomSeries to produce alphanumeric resource Ids instead
    of all-numeric resource Ids (the method that produces the all-numeric values
    is still in the SecureRandomSeries class). This led to refactoring some
    other classes.
  * Added additional Kafka related libraries to dependencies.gradle:
    * Apache Kafka
    * Apache Kafka Client
    * Spring Cloud Starter Stream Kafka
    * Spring Cloud Binder Kakfa Streams


* Maintenance:
  * Updated these libraries and plugins:
    * spring gradle plugin
    * lombok gradle plugin
    * spring cloud starter
    * reactor test
    * test containers
    * r2dbc:h2


## [5.5.0] - 2022-11-24

* Maintenance:
  * Updated these libraries to their latest versions:
    * ben manes gradle plugin
    * spring cloud starter
    * test containers
    * liquibase core
    * postgres jdbc driver

* Features:
  * Added some basic Kafka libraries to the dependencies.gradle file. The goal is
    to make it easier to start a Kafka application. The build.gradle file is not affected.


## [5.4.0] - 2022-11-09

* Fixes:
  * Metacode allowed the user to create a resource named 'Public', which is a reserved
    word in Java. During code generation, the 'Public' resource name gets mapped to a
    package named 'public' (for example, say, "acme.cinema.public"), which leads to compile-time errors.
    This is fixed.

* Refactor
  * Use Amazon Corretto as the base Docker image used by the Jib plugin.
    The default base image, OpenJDK, has been deprecated; using a supported
    base image makes more sense.
  * Pin the snakeyaml version to fix known CVEs. SpringBoot currently pulls in
    snakeyaml:1.30 which has a known CVE. We've pinned the version to 1.33 (the
    latest version at this time). This change can be found at the bottom
    of the gradle/dependencies.gradle file.

* Maintenance
  * Bumped Reactor Test library to v3.5.0

## [5.3.0] - 2022-11-01

* Fixes:
  * Fixed an issue that allowed a stack trace to leak into the response from a Webflux endpoint

## [5.2.0] - 2022-10-28

* Docs:
  * The help message now prints the full hierarchy of commands instead of only
    printing the next depth of commands.

* Maintenance
  * Update to latest version: Sonarqube and Jib Gradle plugins

## [5.1.1] - 2022-10-22

* Maintenance:
  * Update to latest versions of Spring Boot, TestContainers, JUnit Jupiter, and
    various Gradle plugins

## [5.1.0] - 2022-10-07

* Fixes:
  * Fixed issues that prevented Spring Batch applications from running out-of-the-box

* Maintenance:
  * Bump the dependency versions consumed by the generated code

## [5.0.0] - 2022-09-17

* Features
  * Added code generator for Spring Batch applications

* Fixes:
  * Fixed Liquibase changelog templates for Spring WebMVC code

* Refactor
  * The package structure for Spring WebMVC and Spring Webflux applications
    was changed.  Classes concerning persistence have been moved into a
    package named ```database```.  This change simplifies the code found within
    the ```endpoint``` packages and makes the classes concerned with persistence
    more obvious.

  * The generated entity bean classes now contain
    ```equals``` and ```hashCode``` methods
  * For Spring Boot projects, a place-holder unit test is now generated

* Maintenance:
  * Bump the dependency versions consumed by the generated code


## [4.0.0] - 2022-08-24

* Features
  * Generate an integration test for the Service class under each endpoint
  * Generate an integration test for the Repository class under each endpoint
  * Improve AbstractIntegrationTest class

* Maintenance
  * Bumped versions of SpringBoot Gradle plugin, Reactor-test, and Lombok Gradle plugin.
    These are consumed by the generated code.

* Fixes
  * Generate a unit test folder for spring-boot projects

## [3.0.0] - 2022-07-15

* Refactor
  * When generating a project using the `spring-boot` template,
    * the SecureRandomSeries class is no longer there
    * the LocalDateConverter class is no longer there
    * an application.properties file is now present
    * the build.gradle dependencies stanza is cleaner
  * In Spring WebMvc and Spring WebFlux projects, the RestfulResource
    interface was renamed to ResourceIdTrait, and the `stereotype`
    package was renamed to `trait`. This is a breaking change _if_
    you run `create endpoint` within a project created with a previous
    version of Metacode. Specifically, compile errors will occur that
    have to be fixed manually.
  * Renaming of generated classes:
    * The EntityBean class to simply, Entity. For example,
      the generated code will now have PetEntity.java instead of PetEntityBean.java
    * The BeanToResourceConverter is now EntityToPojoConverter
    * The ResourceToBeanConverter is now PojoToEntityConverter
  * In Spring WebFlux and Spring WebMvc projects, the package structure
    is improved.
* Maintenance
  * Bump the dependency versions consumed by the generated code
* Bugs
  * Disallow creating endpoints within a spring-boot project template
    (endpoints only make sense with spring-webmvc and spring-webflux projects)

## [2.0.0] - 2022-07-02
* Features
  * Add property to hold CORS `allowed origins` pattern.
    The property can be set with an environment variable
    or in the application.yml/properties file.
* Refactored
  * The generated class, `Constants.java` is renamed to `SpringProfiles.java`.
    Within that class, the constants `PROFILE_IT` and `PROFILE_TEST` are renamed
    to `INTEGRATION_TEST` and `TEST`, respectively.
* Maintenance
  * Bump the dependency versions consumed by the generated code

## [1.2.1] - 2022-06-27
* Bug
  * Added 'User' to the list of disallowed words. Hibernate generates invalid SQL
    when the table name is 'User'; the SQL error prevents the corresponding database
    table from being created, which subsequently causes integration tests to fail

## [1.2.0] - 2022-06-25
* Maintenance
  * Bump the dependency versions consumed by the generated code
  * Transition a deprecated Spring class to its replacement

## [1.1.0] - 2022-06-01

* Bugs:
  * Added property for Coditory integration plugin
* Maintenance:
  * Bump the dependency versions consumed by the generated code
* Feature:
  * In build.gradle, only one tag is now assigned to the docker image.

## [1.0.0] - 2022-05-17

A minimum, lovable product has been achieved.

* Docs:
  * Improved the README
  * Added a LICENSE file
* Refactor:
  * Renamed the `--support` command-line option to `--add`
  * Added explicit version for PostgreSQL library instead of letting the Spring Gradle plugin decide the version
* Bugs:
  * Fixed spelling error in a ValidationMessages.properties file

## [0.2.0] - 2022-04-23
* Feature: Add database populators to webmvc projects (webflux projects already have them)
* Refactor: Improvements to generated code motivated by code analyzer reports

## [0.1.10] - 2022-04-19
* Bug: version was not showing when using '--version' option

## [0.1.9] - 2022-04-18
* Bug: forgot to generate ValidationMessages.properties in webflux projects

## [0.1.8] - 2022-04-15
* Generated code uses latest dependency versions
* Refactor Resource IDs to have 160-bit entropy
* Generate a LocalDateConverter to demonstrate handling LocalDate query parameters

## [0.1.7] - 2022-04-01
* Bug fix: Spring WebMvc projects were producing Spring WebFlux code

## [0.1.6] - 2022-03-27
* Generated code uses latest dependency versions
* Fix compile errors in generated code
* Generate artifacts for Postgres and TestContainers when creating a Spring WebFlux project

## [0.1.5] - 2022-03-23
* Generate basic Spring Webflux project
* Improve generated test classes for better code coverage

## [0.1.4] - 2022-03-22
* Generate Postgres, TestContainers, and Liquibase artifacts for Spring WebMvc projects
* Improve generated test classes for better code coverage
* Resolve code smells in generated code

## [0.1.3] - 2022-03-15
* Internal bug fixes and improvements
* Bug fix: bad URL requests in Spring WebFlux applications returned stack trace in the response

## [0.1.2] - 2022-03-15
* Generate a basic Spring WebMvc project
* Bug fix: endpoint URLs were incorrect

## [0.1.1] - 2022-03-15
* Internal bug fixes
* Changed the content of the copyright header that appears in generated source code
* Bug fix: ensure endpoint routes begin with front-slash
* Various bug fixes in generated code

## [0.1.0] - 2022-01-19
* Generate a basic Spring WebMvc project

## [0.0.0] - 2022-01-18
- Initial code commit
