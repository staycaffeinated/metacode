

## [Unreleased]
* Feature: add database populators to webmvc projects (webflux projects already have them)
* Refactor: improvements to generated code motivated by code analyzer reports

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
