DROP TABLE IF EXISTS ${endpoint.entityName};
CREATE TABLE ${endpoint.entityName}
(
    ID          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    RESOURCE_ID LONG,
    TEXT        VARCHAR(255) NOT NULL,
    CREATED_ON  TIMESTAMP NOT NULL DEFAULT NOW(),
    UPDATED_ON  TIMESTAMP
);