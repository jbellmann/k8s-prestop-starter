package org.zalando.spring.k8s;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

public class PreStopHealthIndicatorTest {

	@Test
	public void testPreStopHealthIndicator() {
		PreStopHealthIndicator indicator = new PreStopHealthIndicator();
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	public void testPreStopHealthIndicatorPreStopInvokedWithTime() {
		PreStopHealthIndicator indicator = new PreStopHealthIndicator();
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		indicator.invokePrestopWithTime(5);
		health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

	@Test
	public void testPreStopHealthIndicatorPreStopInvokedWithDefaultTime() {
		PreStopHealthIndicator indicator = new PreStopHealthIndicator();
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		indicator.invokePrestop();
		health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

}
