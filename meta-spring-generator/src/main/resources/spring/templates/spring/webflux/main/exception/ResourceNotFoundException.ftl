<#include "/common/Copyright.ftl">
package ${project.basePackage}.exception;

/**
 * Resource not found
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7445467825071886229L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public ResourceNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}