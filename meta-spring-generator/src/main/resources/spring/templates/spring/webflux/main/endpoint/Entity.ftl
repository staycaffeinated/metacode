<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("${endpoint.tableName}")
public class ${endpoint.ejbName} {

    /**
     * This is the identifier exposed to the outside world.
     * This is a secure random value with at least 160 bits of entropy, making it difficult for a hacker to guess.
     * This is a unique value in the database. This value can be a positive or negative number.
     *
     * The value is 48 or 49 characters long. Varchar50 is used to obtain an even length
     */
    @Column(value="resource_id")
    private String resourceId;
    
    /**
     * This is the database identifier. Naturally, this value should 
     * never be exposed outside the scope of this service.
     */
    @Id
    private Long id;

    @NotEmpty(message = "Text cannot be empty")
    @Column(value = "text")
    private String text;
}