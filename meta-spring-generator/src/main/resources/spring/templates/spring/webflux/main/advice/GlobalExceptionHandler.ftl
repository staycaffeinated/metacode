<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;

import lombok.extern.slf4j.Slf4j;
import ${project.basePackage}.exception.ResourceNotFoundException;
import ${project.basePackage}.exception.UnprocessableEntityException;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import reactor.core.publisher.Mono;

/**
 * Handles turning exceptions into RFC-7807 problem/json responses,
 * so instead of an exception and its stack trace leaking back
 * to the client, an RFC-7807 problem description is returned instead.
 */
@SuppressWarnings("unused")
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler implements ProblemHandling, ErrorWebExceptionHandler {

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Mono<Problem> handleUnprocessableEntityException(UnprocessableEntityException exception) {
        return problemDescription("The request cannot be processed", exception);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public Mono<Problem> handleResourceNotFound(ResourceNotFoundException exception) {
	    return problemDescription("Resource not found", exception, Status.NOT_FOUND);
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public Mono<Problem> handleNumberFormatException(NumberFormatException exception) {
	    return problemDescription("Bad request: request contains an invalid parameter", exception);
	}

    /**
     * Catch anything not caught by other handlers
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return null;
    }

    /**
     * Build a Problem/JSON description with HttpStatus: 422 (unprocessable entity)
     */
    private Mono<Problem> problemDescription(String title, Throwable throwable) {
        return problemDescription(title, throwable, Status.UNPROCESSABLE_ENTITY);
    }

    private Mono<Problem> problemDescription(String title, Throwable throwable, Status status) {
        Problem problem = ProblemSummary.builder().status(status).detail(throwable.getMessage()).title(title).build();
  	    return Mono.just(problem);
    }
}

