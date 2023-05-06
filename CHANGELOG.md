
## [Unreleased]

### Added 
* Previous versions used the Coditory plugin to configure integration tests. 
  That plugin has been replaced with Gradle's 'jvm-test-suite' plugin. With
  this change, integration tests are now found in the 'src/integrationTest/java'
  folder.  A suite was also added for performance tests, if you want to leverage that.   

* Support for database schemas has been added. If your tables need to be contained
  within a specific schema, use the command line option '-S [schema] or --schema [schema]'. 
  If you use schemas, its suggested to also use TestContainers, since Metacode will
  create the schema within the test container (Hibernate does not do that automatically), 
  so integration tests will make queries against the table within the schema.
  

### Fixed
* When generating Spring WebMVC projects that used Postgres and TestContainers, 
  the integration tests were not always behaving as expected.  Those generated tests
  have been refactored to use an explicity PostgresContainerTests class which, so far,
  has proven to be more dependable. 

* With the updated Problem library, the generated GlobalExceptionHandler
  does not need a handler for jakarta.validjation.ConstraintViolation; the Problem library 
  now supports it.  Its worth noting the Problem library's handler _will_ return
  a stack trace in the response, which is a bad practice for client-facing applications,
  since stack traces are considered an information leak. 

* The Spotless plugin is used for code formatting. In the Gradle files, a dependency was
  added between the 'check' task and the 'spotlessApply' task, so that running 'gradlew check'
  automatically triggers 'spotlessApply'. 

### Maintenance
* Updated various libraries and plugins:
** Spring Boot
** Lombok
** JUnit Jupiter
** Problem Spring Web
** TestContainers


## [7.0.0] - 2023-03-28

### Added
* A DataStore class has been added to encapsulate the business rules around
  persistence.  The DataStore API deals in Domain objects. Behind the scenes,
  the DataStore handles the EntityBeans and Repositories that enable reading
  and writing to the database. The Service components have also been refactored
  to interface with the DataStore instead of the Repository. The DataStore is
  only available in the spring-webmvc templates in this first release.

* Added MongoDB support for spring-webmvc projects. Test containers can also be
  used with MongoDB, but that's still an 'early-access' option, as there is a
  known problem that's still being investigated.

* Introduced an interface class to define the methods of implemented by the Service class.
  This helps enforce a separation-of-concerns between the controller and service
  classes.

### Maintenance

* Updated these libraries and plugins:
  ** AssertJ
  ** Ben Manes gradle plugin
  ** Lombok
  ** PostgreSQL driver
  ** SonarQube plugin
  ** Spotless plugin
  ** Spring Boot
  ** Spring Cloud

## [6.1.0] - 2023-01-21

### Added
  * Added jakarta.persistence-api library to WebFlux and WebMvc build.gradle files.
    This fixes a compile-time warning.
  * Webflux projects now generate integration tests when using PostgreSQL and test containers.
    The integration tests spin up an instance of the web application and an in-memory instance of
    a PostgreSQL database using test containers.

### Maintenance
  * Updated these libraries and plugins:
    * junit
    * reactor test
    * coditory integration test plugin
    * spotless plugin


## [6.0.0] - 2022-12-31

### Added
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


### Maintenance
  * Updated these libraries and plugins:
    * spring gradle plugin
    * lombok gradle plugin
    * spring cloud starter
    * reactor test
    * test containers
    * r2dbc:h2


## [5.5.0] - 2022-11-24

### Added
  * Added some basic Kafka libraries to the dependencies.gradle file. The goal is
    to make it easier to start a Kafka application. The build.gradle file is not affected.

### Maintenance
  * Updated these libraries to their latest versions:
    * ben manes gradle plugin
    * spring cloud starter
    * test containers
    * liquibase core
    * postgres jdbc driver

## [5.4.0] - 2022-11-09

### Fixed
  * Metacode allowed the user to create a resource named 'Public', which is a reserved
    word in Java. During code generation, the 'Public' resource name gets mapped to a
    package named 'public' (for example, say, "acme.cinema.public"), which leads to compile-time errors.
    This is fixed.

### Changed
  * Use Amazon Corretto as the base Docker image used by the Jib plugin.
    The default base image, OpenJDK, has been deprecated; using a supported
    base image makes more sense.
  * Pin the snakeyaml version to fix known CVEs. SpringBoot currently pulls in
    snakeyaml:1.30 which has a known CVE. We've pinned the version to 1.33 (the
    latest version at this time). This change can be found at the bottom
    of the gradle/dependencies.gradle file.

### Maintenance
  * Bumped Reactor Test library to v3.5.0

## [5.3.0] - 2022-11-01

### Fixed
  * Fixed an issue that allowed a stack trace to leak into the response from a Webflux endpoint

## [5.2.0] - 2022-10-28

### Changed
  * The help message now prints the full hierarchy of commands instead of only
    printing the next depth of commands.

### Maintenance
  * Update to latest version: Sonarqube and Jib Gradle plugins

## [5.1.1] - 2022-10-22

### Maintenance
  * Update to latest versions of Spring Boot, TestContainers, JUnit Jupiter, and
    various Gradle plugins

## [5.1.0] - 2022-10-07

### Fixed
  * Fixed issues that prevented Spring Batch applications from running out-of-the-box

### Maintenance
  * Bump the dependency versions consumed by the generated code

## [5.0.0] - 2022-09-17

### Added
  * Added code generator for Spring Batch applications

### Fixed
  * Fixed Liquibase changelog templates for Spring WebMVC code

### Changed
  * The package structure for Spring WebMVC and Spring Webflux applications
    was changed.  Classes concerning persistence have been moved into a
    package named ```database```.  This change simplifies the code found within
    the ```endpoint``` packages and makes the classes concerned with persistence
    more obvious.

  * The generated entity bean classes now contain
    ```equals``` and ```hashCode``` methods
  * For Spring Boot projects, a place-holder unit test is now generated

### Maintenance
  * Bump the dependency versions consumed by the generated code


## [4.0.0] - 2022-08-24

### Added
  * Generate an integration test for the Service class under each endpoint
  * Generate an integration test for the Repository class under each endpoint
  * Improve AbstractIntegrationTest class

### Maintenance
  * Bumped versions of SpringBoot Gradle plugin, Reactor-test, and Lombok Gradle plugin.
    These are consumed by the generated code.

### Fixed
  * Generate a unit test folder for spring-boot projects

## [3.0.0] - 2022-07-15

### Changed
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

### Maintenance
  * Bump the dependency versions consumed by the generated code

### Fixed
  * Disallow creating endpoints within a spring-boot project template
    (endpoints only make sense with spring-webmvc and spring-webflux projects)

## [2.0.0] - 2022-07-02

### Added
  * Add property to hold CORS `allowed origins` pattern.
    The property can be set with an environment variable
    or in the application.yml/properties file.
### Changed
  * The generated class, `Constants.java` is renamed to `SpringProfiles.java`.
    Within that class, the constants `PROFILE_IT` and `PROFILE_TEST` are renamed
    to `INTEGRATION_TEST` and `TEST`, respectively.
### Maintenance
  * Bump the dependency versions consumed by the generated code

## [1.2.1] - 2022-06-27
### Fixed
  * Added 'User' to the list of disallowed words. Hibernate generates invalid SQL
    when the table name is 'User'; the SQL error prevents the corresponding database
    table from being created, which subsequently causes integration tests to fail

## [1.2.0] - 2022-06-25
### Maintenance
  * Bump the dependency versions consumed by the generated code
  * Transition a deprecated Spring class to its replacement

## [1.1.0] - 2022-06-01
### Added
* In build.gradle, only one tag is now assigned to the docker image.
### Fixed
  * Added property for Coditory integration plugin
### Maintenance:
  * Bump the dependency versions consumed by the generated code

## [1.0.0] - 2022-05-17

A minimum, lovable product has been achieved.

### Added
  * Improved the README
  * Added a LICENSE file

### Changed
  * Renamed the `--support` command-line option to `--add`
  * Added explicit version for PostgreSQL library instead of letting the Spring Gradle plugin decide the version
  * Fixed spelling error in a ValidationMessages.properties file

## [0.2.0] - 2022-04-23

### Added
* Add database populators to webmvc projects (webflux projects already have them)
### Changed
* Improvements to generated code motivated by code analyzer reports

## [0.1.10] - 2022-04-19
### Fixed
* Version was not showing when using '--version' option

## [0.1.9] - 2022-04-18
### Fixed
* Forgot to generate ValidationMessages.properties in webflux projects

## [0.1.8] - 2022-04-15
### Added
* Generated code uses latest dependency versions
* Generate a LocalDateConverter to demonstrate handling LocalDate query parameters
### Changed
* Resource IDs to have 160-bit entropy

## [0.1.7] - 2022-04-01
### Fixed
* Spring WebMvc projects were producing Spring WebFlux code

## [0.1.6] - 2022-03-27
### Added
* Generated code uses latest dependency versions
* Generate artifacts for Postgres and TestContainers when creating a Spring WebFlux project
### Fixed
* Resolved compile errors in generated code

## [0.1.5] - 2022-03-23
### Added
* Generate basic Spring Webflux project
### Changed
* Improve generated test classes for better code coverage

## [0.1.4] - 2022-03-22
### Added
* Generate Postgres, TestContainers, and Liquibase artifacts for Spring WebMvc projects
### Changed
* Improve generated test classes for better code coverage
### Fixed
* Resolve code smells in generated code

## [0.1.3] - 2022-03-15
### Fixed
* Internal bug fixes and improvements
* Bug fix: bad URL requests in Spring WebFlux applications returned stack trace in the response

## [0.1.2] - 2022-03-15
### Added
* Generate a basic Spring WebMvc project
### Fixed
* The endpoint URLs were incorrect

## [0.1.1] - 2022-03-15
### Fixed
* Internal bug fixes
* Bug fix: ensure endpoint routes begin with front-slash
* Various bug fixes in generated code
### Changed
* Changed the content of the copyright header that appears in generated source code

## [0.1.0] - 2022-01-19
### Added
* Generate a basic Spring WebMvc project

## [0.0.0] - 2022-01-18
### Added
* Initial code commit
