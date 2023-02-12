<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import java.util.Optional;

import ${endpoint.basePackage}.database.CrudAware;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ${endpoint.entityName}Repository
    extends
        JpaRepository<${endpoint.ejbName}, Long>,
        JpaSpecificationExecutor<${endpoint.ejbName}>,
        CrudAware<${endpoint.ejbName}> {

    /* This method needs @Transactional since it's a custom query. See
     * https://stackoverflow.com/questions/39827054/spring-jpa-repository-transactionality
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions
     */
    @Transactional
    /* returns the number of entities deleted */
    Long deleteByResourceId( String id );
}

