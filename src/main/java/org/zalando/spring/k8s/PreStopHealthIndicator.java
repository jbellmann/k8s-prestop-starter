package org.zalando.spring.k8s;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * Healthindicator exposed as actuator endpoint ('/preStop') to switch to {@link Status#OUT_OF_SERVICE} if
 * invoked via http.
 * 
 * @author jbellmann
 */
@Slf4j
public class PreStopHealthIndicator  extends AbstractHealthIndicator implements org.springframework.boot.actuate.endpoint.Endpoint<Map<String,String>> {

	public static final String ID = "preStop";
	private static final int DEFAULT_TIMEOUT = 26;
	private Status status = Status.UP;

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		builder.status(status);
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

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Map<String, String> invoke() {
		invokePrestopInternal(DEFAULT_TIMEOUT);
		return Collections.singletonMap("status", status.getCode());
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isSensitive() {
		return false;
	}
}
