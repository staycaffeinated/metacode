<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import lombok.NonNull;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ${endpoint.entityName}Service {

    private final ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    private final ConversionService conversionService;

    /*
     * Constructor
     */
    @Autowired
    public ${endpoint.entityName}Service(${endpoint.entityName}Repository ${endpoint.entityVarName}Repository,
                                        ConversionService conversionService)
    {
        this.${endpoint.entityVarName}Repository = ${endpoint.entityVarName}Repository;
        this.conversionService = conversionService;
    }

    /*
     * findAll
     */
    public List<${endpoint.pojoName}> findAll${endpoint.entityName}s() {
        List<${endpoint.ejbName}> resultSet = ${endpoint.entityVarName}Repository.findAll();
        return resultSet.stream().map(ejb -> conversionService.convert(ejb,${endpoint.pojoName}.class)).collect(Collectors.toList());
    }

    /**
     * findByResourceId
     */
    public Optional<${endpoint.pojoName}> find${endpoint.entityName}ByResourceId(Long id) {
        Optional<${endpoint.ejbName}> optional = ${endpoint.entityVarName}Repository.findByResourceId ( id );
        return optional.map(ejb -> conversionService.convert(ejb, ${endpoint.pojoName}.class));
    }

    /*
     * findByText
     */
    public List<${endpoint.pojoName}> findByText(@NonNull String text, @Min(value=0) int pageNumber, @Min(value=20) int pageSize) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(text).ascending());
            Page<${endpoint.ejbName}> resultSet = ${endpoint.entityVarName}Repository.findByText(text, pageable);
            return resultSet.stream()
                            .map(ejb -> conversionService.convert(ejb, ${endpoint.pojoName}.class))
                            .collect(Collectors.toList());
    }

    /**
     * Persists a new resource
     */
    public ${endpoint.pojoName} create${endpoint.entityName}( @NonNull @Validated(OnCreate.class) ${endpoint.pojoName} resource ) {
        resource.setResourceId ( SecureRandomSeries.nextLong() );
        ${endpoint.ejbName} entityBean = Objects.requireNonNull(conversionService.convert (resource, ${endpoint.ejbName}.class ));
        entityBean = ${endpoint.entityVarName}Repository.save ( entityBean );
        return conversionService.convert(entityBean, ${endpoint.pojoName}.class);
    }

    /**
     * Updates an existing resource
     */
    public Optional<${endpoint.pojoName}> update${endpoint.entityName}( @NonNull @Validated(OnUpdate.class) ${endpoint.pojoName} resource ) {
        Optional<${endpoint.ejbName}> optional = ${endpoint.entityVarName}Repository.findByResourceId ( resource.getResourceId() );
        if ( optional.isPresent() ){
            ${endpoint.ejbName} entityBean = optional.get();
            // Copy all mutable fields of the resource into the entity bean
            entityBean.setText(resource.getText());
            // persist the changes
            entityBean = ${endpoint.entityVarName}Repository.save(entityBean);
            return Optional.of( Objects.requireNonNull(conversionService.convert(entityBean, ${endpoint.pojoName}.class)));
        }
        return Optional.empty();
    }

    /**
     * delete
     */
    public void delete${endpoint.entityName}ByResourceId(@NonNull Long id) {
        ${endpoint.entityVarName}Repository.deleteByResourceId(id);
    }
}
