<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.springframework.context.ApplicationEvent;

/**
 * ${endpoint.entityName} events
 */
 @SuppressWarnings({"Java:1102"})
public class ${endpoint.entityName}Event extends ApplicationEvent {

	public static final String CREATED = "CREATED";
	public static final String UPDATED = "UPDATED";
	public static final String DELETED = "DELETED";

	private static final long serialVersionUID = 9152086626754282698L;

	private final String eventType;

	public ${endpoint.entityName}Event(String eventType, ${endpoint.pojoName} resource) {
		super(resource);
		this.eventType = eventType;
	}

	public String getEventType() {
		return eventType;
	}

}