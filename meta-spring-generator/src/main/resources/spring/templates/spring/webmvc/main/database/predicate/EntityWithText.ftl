<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Specification for ${endpoint.ejbName} with a given text value
 */
@Getter
public class ${endpoint.entityName}WithText implements Specification<${endpoint.ejbName}> {

    // This is a 'user-defined' UID; feel free to improve it.
    private static final long serialVersionUID = 1L;

    private final String text;

    public ${endpoint.entityName}WithText(final String text) {
        if (!ObjectUtils.isEmpty(text)) {
            this.text = '%' + text.toLowerCase() + '%';
        }
        else this.text = text;
    }

    @Override
    public Predicate toPredicate(Root<${endpoint.ejbName}> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (ObjectUtils.isEmpty(text)) {
            // if no conditions were given for the text value, then accept any text column value
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always; true=no filtering
        }
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("text")), text);
    }
}

