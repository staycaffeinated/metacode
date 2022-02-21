<#include "/common/Copyright.ftl">
package ${project.basePackage}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * An UnprocessableEntity exception indicates a well-formed request was
 * received, but could not be successfully processed.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends ResponseStatusException {

    private static final long serialVersionUID = 2711067751568445348L;

    /**
     * Default Constructor
     */
    public UnprocessableEntityException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Constructor
     */
    public UnprocessableEntityException(Throwable throwable) {
       super(HttpStatus.UNPROCESSABLE_ENTITY, "Unable to process the resource (or entity)", throwable);
    }

    /**
     * Constructor with a reason to add to the exception
     * message as explanation.
     *
     * @param reason the associated reason (optional)
     */
    public UnprocessableEntityException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }

    /**
     * Constructor with a reason to add to the exception
     * message as explanation, as well as a nested exception.
     *
     * @param reason the associated reason (optional)
     * @param cause  a nested exception (optional)
     */
    public UnprocessableEntityException(String reason, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason, cause);
    }
}