<#include "/common/Copyright.ftl">

package ${endpoint.packageName};


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * This class publishes events in a single executor thread with events added
 * to a BlockingQueue and emitted by FluxSink.
 */
@Component
@Slf4j
public class ${endpoint.entityName}EventPublisher implements ApplicationListener<${endpoint.entityName}Event>, Consumer<FluxSink<${endpoint.entityName}Event>> {

	private final Executor executor;
	private final BlockingQueue<${endpoint.entityName}Event> queue;

	${endpoint.entityName}EventPublisher() {
		this.executor = Executors.newSingleThreadExecutor();
		this.queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void onApplicationEvent(@NonNull ${endpoint.entityName}Event event) {
		boolean success = this.queue.offer(event);
        if (!success) {
            log.info("Unable to add this event to the queue: {}", event);
        }
	}

	@Override
    @SuppressWarnings("java:S2142") // S2142 is a false positive
	public void accept(FluxSink<${endpoint.entityName}Event> sink) {
		this.executor.execute(() -> {
			while (true) {
				try {
					${endpoint.entityName}Event event = queue.take();
					sink.next(event);
				} catch (InterruptedException e) {
					ReflectionUtils.rethrowRuntimeException(e);
				}
			}
		});
	}
}