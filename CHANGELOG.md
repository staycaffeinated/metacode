

## [Unreleased]
* Features
  * Add property to hold CORS `allowed origins` pattern.
    The property can be set with an environment variable
    or in the application.yml/properties file.


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
