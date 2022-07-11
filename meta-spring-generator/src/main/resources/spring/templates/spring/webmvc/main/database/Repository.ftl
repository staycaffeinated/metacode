<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ${endpoint.entityName}Repository extends JpaRepository<${endpoint.ejbName}, Long> {

    Optional<${endpoint.ejbName}> findByResourceId ( String id );

    /* returns the number of entities deleted */
    Long deleteByResourceId( String id );

    Page<${endpoint.ejbName}> findByText(String text, Pageable pageable);
}

