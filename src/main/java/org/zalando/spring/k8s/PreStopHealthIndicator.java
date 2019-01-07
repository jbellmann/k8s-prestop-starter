package org.zalando.spring.k8s;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Healthindicator exposed as actuator endpoint ('/preStop') to switch to {@link Status#OUT_OF_SERVICE} if
 * invoked via http.
 * 
 * @author jbellmann
 */
@Slf4j
@Component
@Endpoint(id = "preStop")
public class PreStopHealthIndicator extends AbstractHealthIndicator {

	private static final int DEFAULT_TIMEOUT = 26;
	private Status status = Status.UP;

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		builder.status(status);
	}

	/**
	 * Executed if actuator endpoint ('/preStop') will be invoked.</br>
	 * 
	 * Timeout is {@link #DEFAULT_TIMEOUT}.
	 */
	@ReadOperation
	public Map<String, String> invokePrestop() {
		return invokePrestopInternal(DEFAULT_TIMEOUT);
	}

	/**
	 * Executed if actuator endpoint ('/preStop/{timeout_in_seconds}') will be invoked.</br>
	 * 
	 * Timeout has to be an integer value.
	 */
	@ReadOperation
	public Map<String, String> invokePrestopWithTime(@Selector int time) {
		return invokePrestopInternal(time);
	}

	protected Map<String, String> invokePrestopInternal(int time) {
		try {
			status = Status.OUT_OF_SERVICE;
			log.debug("STATUS SWITCHED TO '{}', SLEEP FOR '{}' SECONDS  ...", status.getCode(), time);
			TimeUnit.SECONDS.sleep(time);
			log.debug("AFTER SLEEPING ...");
		} catch (InterruptedException e) {
			log.warn("preStop interrupted", e);
			Thread.currentThread().interrupt();
		}
		return Collections.singletonMap("status", status.getCode());
	}
}
