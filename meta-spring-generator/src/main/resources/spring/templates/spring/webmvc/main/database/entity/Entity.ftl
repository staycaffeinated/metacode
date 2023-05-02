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
<#if endpoint.schema?has_content>
@Table(name="${endpoint.tableName}" scheme="${endpoint.schema}")
<#else>
@Table(name="${endpoint.tableName}")
</#if>
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
    <#if (endpoint.isWithPostgres())>
    @SequenceGenerator(name = "${endpoint.lowerCaseEntityName}_sequence", sequenceName = "${endpoint.lowerCaseEntityName}_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${endpoint.lowerCaseEntityName}_sequence")
    <#else>
    @GeneratedValue
    </#if>
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