<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;


import lombok.extern.slf4j.Slf4j;
import ${project.basePackage}.exception.ResourceNotFoundException;
import ${project.basePackage}.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.zalando.problem.StatusType;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests of the GlobalExceptionHandler
 */
@Slf4j
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
            .consumeNextWith(p -> assertThat(Objects.equals(p.getStatus().getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY)))
            .verifyComplete();
    }

    @Test
    void whenResourceNotFoundException_expectHttpStatusIsNotFound() {
        // when
        var publisher = exceptionHandlerUnderTest.handleResourceNotFound(new ResourceNotFoundException("test"));

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p -> assertThat(Objects.equals(p.getStatus().getStatusCode(), HttpStatus.NOT_FOUND)))
            .verifyComplete();
    }

    @Test
    void whenNumberFormatExceptionException_expectHttpStatusIsUnprocessableEntity() {
        // when
        var publisher = exceptionHandlerUnderTest.handleNumberFormatException(new NumberFormatException("test"));

        // then
        StepVerifier.create(publisher).expectSubscription()
            .consumeNextWith(p -> assertThat(Objects.equals(p.getStatus().getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY)))
            .verifyComplete();
    }
}