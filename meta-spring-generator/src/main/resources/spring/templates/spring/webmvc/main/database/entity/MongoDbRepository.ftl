<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public interface PetRepository
    extends
        MongoRepository<${endpoint.documentName}, String>,
        PagingAndSortingRepository<${endpoint.documentName}, String> {

    @Query("{resourceId:?}")
    Optional<${endpoint.documentName}> findByResourceId(String resourceId);

    @Query("{text:?}")
    List<${endpoint.documentName}> findByText(String text);

    Page<${endpoint.documentName}> findByTextContainingIgnoreCase(String title, Pageable pageable);
}
