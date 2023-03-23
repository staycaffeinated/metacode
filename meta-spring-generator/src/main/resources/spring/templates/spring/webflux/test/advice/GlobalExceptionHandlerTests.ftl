<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;

import ${project.basePackage}.exception.ResourceNotFoundException;
import ${project.basePackage}.exception.UnprocessableEntityException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests of the GlobalExceptionHandler
 */
class GlobalExceptionHandlerTests {

    private GlobalExceptionHandler exceptionHandlerUnderTest;

    @BeforeEach
    public void setUp() {
        exceptionHandlerUnderTest = new GlobalExceptionHandler();
    }

    @Test
    void whenUnprocessableEntityException_expectHttpStatusIsUnprocessableEntity() {
        // when
        var publisher = exceptionHandlerUnderTest .handleUnprocessableEntityException(new UnprocessableEntityException("test"));

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p -> assertThat(p.getStatus().getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value()))
            .verifyComplete();
    }

    @Test
    void whenResourceNotFoundException_expectHttpStatusIsNotFound() {
        // when
        var publisher = exceptionHandlerUnderTest.handleResourceNotFound(new ResourceNotFoundException("test"));

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p -> assertThat(p.getStatus().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()))
            .verifyComplete();
    }

    @Test
    void whenNumberFormatExceptionException_expectHttpStatusIsUnprocessableEntity() {
        // when
        var publisher = exceptionHandlerUnderTest.handleNumberFormatException(new NumberFormatException("test"));

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p -> assertThat(p.getStatus().getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value()))
            .verifyComplete();
    }

    @Test
    void verifyHandleMethodReturnsNull() {
        var serverWebExchange = Mockito.mock(ServerWebExchange.class);
        var publisher = exceptionHandlerUnderTest.handle(serverWebExchange, new Throwable());

        assertThat(publisher).isNull();
    }

    @Test
    void whenConstraintValidation_expectBadRequest() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Set<ConstraintViolation<?>> mockViolations = fakeViolations();
        when(exception.getConstraintViolations()).thenReturn(mockViolations);

        var publisher = exceptionHandlerUnderTest.handleJakartaConstraintViolationException(exception);

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p ->
                assertThat(Objects.requireNonNull(p.getStatus())
                   .getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()))
            .verifyComplete();
    }

    private Set<ConstraintViolation<?>> fakeViolations() {
        ConstraintViolation<?> v1 = Mockito.mock(ConstraintViolation.class);
        ConstraintViolation<?> v2 = Mockito.mock(ConstraintViolation.class);
        when(v1.getMessage()).thenReturn("Violation One");
        when(v2.getMessage()).thenReturn("Violation Two");
        return Set.of(v1, v2);
    }
}