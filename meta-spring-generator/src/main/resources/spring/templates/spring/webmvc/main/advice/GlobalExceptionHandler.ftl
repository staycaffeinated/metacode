<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;

import ${project.basePackage}.exception.UnprocessableEntityException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.sql.SQLException;

/**
* Handles turning exceptions into RFC-7807 problem/json responses,
* so instead of an exception and its stack trace leaking back
* to the client, an RFC-7807 problem description is returned instead.
*/
@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Problem> handleUnprocessableRequestException(UnprocessableEntityException exception) {
        return problemDescription("The request cannot be processed", exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Problem> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return problemDescription("The request contains invalid data", exception);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    public ResponseEntity<Problem> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return problemDescription("The requested entity was not found", ex);
    }

    /**
 * Handles SQL exception.
 *
 * @param ex      Exception
 * @param request WebRequest
 * @return ResponseEntity
 */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Problem> handleSQLException(SQLException ex, WebRequest request) {
        String message = String.format("Database Error: %s : %s ", ex.getErrorCode(), ex.getLocalizedMessage());
        return problemDescription(message, ex, Status.SERVICE_UNAVAILABLE);
    }


    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return a ResponseEntity with a body containing the problem description
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Problem> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = "Invalid parameter in the request";

        // If the value and class are provided, write a detailed message
        Object value = ex.getValue();
        Class<?> klass = ex.getRequiredType();
        if (value != null && klass != null)
            message = String.format("The parameter '%s' with value '%s' could not be converted to type '%s'", ex.getName(), value, klass.getSimpleName());

        return problemDescription(message, ex);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @return the ApiError object
     */
    protected ResponseEntity<Problem> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String error = String.format("The parameter '%s' is missing", ex.getParameterName());
        return problemDescription (error, ex, Status.UNPROCESSABLE_ENTITY);
    }


    /**
     * Build a Problem/JSON description with HttpStatus: 422 (unprocessable entity)
     *
     * @param throwable the exception received by the handler
     * @return a ResponseEntity with a body containing the problem description
     */
    private ResponseEntity<Problem> problemDescription(String title, Throwable throwable) {
        return problemDescription(title, throwable, Status.UNPROCESSABLE_ENTITY);
    }

    /**
     * Build a Problem/JSON description with the given http status
     *
     * @param throwable the exception received by the handler
     * @return a ResponseEntity with a body containing the problem descriptionn
     */
    private ResponseEntity<Problem> problemDescription(String title, Throwable throwable, Status status) {
        Problem problem = Problem.builder()
                .withStatus(status)
                .withDetail(throwable.getMessage())
                .withTitle(title)
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }
}

