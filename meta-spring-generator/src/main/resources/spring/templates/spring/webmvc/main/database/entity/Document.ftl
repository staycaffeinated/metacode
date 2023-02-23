<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import jakarta.validation.constraints.NotEmpty;

@Document("${endpoint.tableName}")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${endpoint.documentName} {
    private static final String COLLECTION_NAME = "${endpoint.tableName}"

    static String collectionName() {
        return COLLECTION_NAME;
    }

    /*
     * This identifier is never exposed to the outside world because
     * database-generated Ids are commonly sequential values that a hacker can easily guess.
     */
    @Id
    private String id;

    /**
     * This is the identifier exposed to the outside world.
     * This is a secure random value with at least 160 bits of entropy, making it difficult for a hacker to guess.
     * This is a unique value in the database. This value can be a positive or negative number.
     */
    private String resourceId;

    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${endpoint.documentName} that = (${endpoint.documentName}) o;
        return Objects.equals(resourceId, that.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId);
    }
}