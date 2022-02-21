<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests of GlobalExceptionHandler
 *
 * Borrowed ideas from:
 *  https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/test/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandlerTests.java
 *
 */
class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler exceptionHandlerUnderTest = new GlobalExceptionHandler();

    private final MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/");

    private final MockHttpServletResponse servletResponse = new MockHttpServletResponse();
    
    /**
     * Test the condition the server raised an EntityNotFoundException
     */
    @Test
    void onEntityNotFoundException_shouldReturnBadRequest() {
        EntityNotFoundException ex = new EntityNotFoundException("some entity");
        ResponseEntity<Problem> response = exceptionHandlerUnderTest.handleEntityNotFound(ex);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // Check the problem body for a status field matching the http status code
        assertThat(response.getBody().getStatus()).isEqualTo(Status.BAD_REQUEST);
    }


    /**
     * Test the condition the server raised a DataIntegrityViolation
     */
    @Test
    void onDataIntegrityViolationException_shouldReturnBadRequest() {
        Set<ConstraintViolation<String>> violations = new HashSet<>();
        ConstraintViolationException cause = new ConstraintViolationException(violations);
        DataIntegrityViolationException ex = new DataIntegrityViolationException("my data integrity violation", cause);

        ResponseEntity<Problem> response = exceptionHandlerUnderTest.handleDataIntegrityViolationException(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getStatus()).isEqualTo(Status.BAD_REQUEST);
        assertThat(response.getBody().getTitle()).isNotEmpty();
    }

    /**
     * Test the condition the server raised a MethodArgumentTypeMismatchException
     */
    @Test
    void onMethodArgumentTypeMismatchException_shouldReturnBadRequest() {
        WebRequest webRequest = mock(WebRequest.class);
        Class<?> stubResponse = String.class;
        MethodParameter parameter = mock(MethodParameter.class);

        // getParameterType() returns Class<?>, so the syntax shown is needed.
        // See https://stackoverflow.com/questions/16890133/cant-return-class-object-with-mockito
        Mockito.<Class<?>>when(parameter.getParameterType()).thenReturn(stubResponse);
        when(parameter.getParameterName()).thenReturn("firstName");

        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("parameterName");
        when(ex.getMessage()).thenReturn("argument type mismatch");

        // See the note above about why we use this syntax on this line
        Mockito.<Class<?>>when(ex.getRequiredType()).thenReturn(stubResponse);
        when(ex.getValue()).thenReturn("-badValue-");
        when(ex.getParameter()).thenReturn(parameter);

        ResponseEntity<Problem> response = exceptionHandlerUnderTest.handleMethodArgumentTypeMismatch(ex, webRequest);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Test the condition the server raised a SQLException
     */
    @Test
    void onSQLException_shouldReturnBadRequest() {
        WebRequest mockWebRequest = mock(WebRequest.class);
        SQLException ex = mock(SQLException.class);
        when(ex.getMessage()).thenReturn("my sql exception message");
        when(ex.getSQLState()).thenReturn("sql state");
        when(ex.getErrorCode()).thenReturn(1234);
        ResponseEntity<Problem> response = exceptionHandlerUnderTest.handleSQLException(ex, mockWebRequest);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getStatus()).isEqualTo(Status.BAD_REQUEST);
        assertThat(response.getBody().getTitle()).isNotEmpty();
    }

}
