<#if (endpoint.isWithPostgres())>
CREATE TABLE if not exists public.${endpoint.entityName}
(
    ID          serial primary key,
    RESOURCE_ID VARCHAR(50),
    TEXT        VARCHAR(255) NOT NULL,
    CREATED_ON  TIMESTAMP NOT NULL DEFAULT NOW(),
    UPDATED_ON  TIMESTAMP
);
<#else>
/* an H2 schema */
DROP TABLE IF EXISTS ${endpoint.entityName};
CREATE TABLE ${endpoint.entityName}
(
    ID          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    RESOURCE_ID VARCHAR(50),
    TEXT        VARCHAR(255) NOT NULL,
    CREATED_ON  TIMESTAMP NOT NULL DEFAULT NOW(),
    UPDATED_ON  TIMESTAMP
);
</#if>