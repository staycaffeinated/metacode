<#include "/common/Copyright.ftl">
package ${project.basePackage}.advice;

import lombok.Builder;
import lombok.Data;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

/**
 * A POJO containing Problem information. Zalando's concrete implementations of
 * the Problem interface extend ThrowableProblem, which extends RuntimeException.
 * The side effect of using a Throwable in the response body is: the stack trace
 * ends up leaking back to the caller. To avoid that side effect, this POJO is
 * used to return Problem information in error responses.
 */
@Builder
@Data
public class ProblemSummary implements Problem {
    private String title;
    private Status status;
    private String detail;
}