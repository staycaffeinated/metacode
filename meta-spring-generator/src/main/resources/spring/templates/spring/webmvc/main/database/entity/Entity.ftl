<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="${endpoint.tableName}")
@EqualsAndHashCode(of = {"resourceId"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${endpoint.ejbName} {

    /*
     * This identifier is never exposed to the outside world because
     * database-generated Ids are commonly sequential values that a hacker can easily guess.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * This is the identifier exposed to the outside world.
     * This is a secure random value with at least 160 bits of entropy, making it difficult for a hacker to guess.
     * This is a unique value in the database. This value can be a positive or negative number.
     */
    @Column(name="resource_id", nullable=false)
    private String resourceId;

    @Column(name="text", nullable = false)
    @NotEmpty(message = "Text cannot be empty")
    private String text;
}