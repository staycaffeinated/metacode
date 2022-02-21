databaseChangeLog:
    - changeSet:
        id: tag-change--create-table-${endpoint.tableName}-v1.0
        author: mojo-code-generator
        changes:
            - tagDatabase:
                - tag: create-table--${endpoint.tableName}-v1.0
    - changeSet:
        id: create-table-${endpoint.tableName}-v1.0
        author: mojo-code-generator
        preConditions:
            - onFail: MARK_RAN
                not:
                    tableExists:
                    tableName: ${endpoint.tableName}
                    schemaName: ${endpoint.schema}
        changes:
            - createTable:
                columns:
                    - column:
                        autoIncrement: true
                        constraints:
                            nullable: false
                            primaryKey: true
                            primaryKeyName: ${endpoint.tableName}_pkey
                        name: id
                            type: BIGINT
                    - column:
                        constraints:
                            nullable: false
                        name: resource_id
                        type: BIGINT
                    - column:
                        constraints:
                            nullable: false
                        name: text
                        type: VARCHAR(250)
                        tableName: Appointment
                        schemaName: petclinic
    - changeSet:
        id: create-index-on-${endpoint.tableName}.resource-id
        author: mojo-code-generator
        preConditions:
            - onFail: CONTINUE
            not:
                - indexExists:
                    indexName: ix_${endpoint.tableName}_resource_id
        changes:
            - createIndex:
                tableName: ${endpoint.tableName}
                schemaName: ${endpoint.schema}
                indexName: IX_${endpoint.tableName}_resource_id
                columns:
                    - column:
                        name: resource_id