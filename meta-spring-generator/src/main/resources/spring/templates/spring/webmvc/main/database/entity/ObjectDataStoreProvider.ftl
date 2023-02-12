<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * ${endpoint.entityName} DataStore provider. This extends the CrudDataStore to inherit the
 * implementations of the CRUD operations, and implements ${endpoint.entityName}DataStore to enable
 * auto-wiring wherever a ${endpoint.entityName}DataStore is needed.
 */
@Component
public class ${endpoint.entityName}DataStoreProvider extends CrudDataStore<${endpoint.entityName}, ${endpoint.ejbName}> implements ${endpoint.entityName}DataStore {

    /**
     * Constructor
     *
     * @param repository
     *            to handle I/O to the database
     * @param ejbToPojoConverter
     *            to convert EJBs to POJOs
     * @param pojoToEntityConverter
     *            to convert POJOs to EJBs
     * @param secureRandom
     *            to generate resourceIds
     */
    public ${endpoint.entityName}DataStoreProvider(CrudAware<${endpoint.ejbName}> repository,
                                 Converter<${endpoint.ejbName},${endpoint.entityName}> ejbToPojoConverter,
                                 Converter<${endpoint.entityName}, ${endpoint.ejbName}> pojoToEntityConverter,
                                 SecureRandomSeries secureRandom)
    {
         super(repository, ejbToPojoConverter, pojoToEntityConverter, secureRandom);
    }

    @Override
    protected Optional<${endpoint.ejbName}> findItem(${endpoint.entityName} domainObj) {
        return repository().findByResourceId(domainObj.getResourceId());
    }

    /**
     * Copies the applicable fields of the domain object, {@code from}, into the
     * database record, {@code to}.
     */
    @Override
    protected void applyBeforeUpdateSteps(${endpoint.entityName} from, ${endpoint.ejbName} to) {
        to.setText(from.getText());
    }

    /**
     * Copies the applicable fields of the domain object, {@code from}, into the
     * database record, {@code to}.
     */
    @Override
    protected void applyBeforeInsertSteps(${endpoint.entityName} from, ${endpoint.ejbName} to) {
        to.setResourceId(super.nextResourceId());
        this.applyBeforeUpdateSteps(from, to);
    }

    /**
     * Returns a Page of ${endpoint.entityName} items that have the given {@code text}
     */
    public Page<${endpoint.entityName}> findByText(@NonNull Optional<String> text, Pageable pageable) {
        Specification<${endpoint.ejbName}> where = Specification.where(new ${endpoint.entityName}WithText(text.orElse("")));
        Page<${endpoint.ejbName}> resultSet = repository().findAll(where, pageable);
        List<${endpoint.entityName}> list = resultSet.stream().map(converterToPojo()::convert).toList();
        return new PageImpl<>(list, pageable, list.size());
    }
}




