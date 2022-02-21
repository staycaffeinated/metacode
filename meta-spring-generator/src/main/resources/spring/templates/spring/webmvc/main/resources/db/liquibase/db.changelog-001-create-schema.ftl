databaseChangeLog:
    - changeSet:
        id: tag-schema--to-be-fixed--version-1.0
        author: mojo-code-generator
        changes:
            - tagDatabase:
                - tag: schema_version_1.0
    - changeSet:
        id: create-schema--to-be-fixed--version-1.0
        author:  mojo-code-generator
        changes:
            - sql:
                sql: create schema widgets

