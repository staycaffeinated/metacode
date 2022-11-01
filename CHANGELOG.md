
## [Unreleased]

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
