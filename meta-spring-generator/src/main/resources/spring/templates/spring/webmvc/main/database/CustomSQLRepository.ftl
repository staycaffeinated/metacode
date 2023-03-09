<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * A composite of the desired repository APIs, with additional custom methods.
 */
@NoRepositoryBean
public interface CustomRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * Find T by its resourceId
     *
     * @param resourceId the identifier
     * @return an Optional containing either T or empty.
     */
    Optional<T> findByResourceId(String resourceId);

    /**
     * Returns the number of entities deleted
     *
     * This method needs @Transactional since it's a custom query. See
     * https://stackoverflow.com/questions/39827054/spring-jpa-repository-transactionality
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions
     */
    @Transactional
    Long deleteByResourceId(String id);
}



