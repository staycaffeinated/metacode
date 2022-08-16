<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Specification for ${endpoint.ejbName} with a given text value
 */
public class ${endpoint.entityName}WithText implements Specification<${endpoint.ejbName}> {

    // This is a 'user-defined' UID; feel free to have your IDE inject a better value
    private static final long serialVersionUID = 1L;

    private final String text;

    public ${endpoint.entityName}WithText(final String text) {
        this.text = text;
    }

    @Override
    public Predicate toPredicate(Root<${endpoint.ejbName}> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (ObjectUtils.isEmpty(text)) {
            // if no conditions were given for the text value, then accept any text column value
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always; true=no filtering
        }
        // To make this a case-insensitive predicate, both the value
        // from the database and the value from the query string are converted
        // to lower-case. If case-sensitivity is desired, then change this accordingly.
        var dbValue = criteriaBuilder.lower(root.get("text"));
        String textValue = this.text.toLowerCase();
        return criteriaBuilder.equal(dbValue, textValue);
    }
}

