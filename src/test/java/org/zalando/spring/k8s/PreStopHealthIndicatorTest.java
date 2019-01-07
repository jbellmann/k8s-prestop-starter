package org.zalando.spring.k8s;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

public class PreStopHealthIndicatorTest {

	private PreStopHealthIndicator indicator;

	@Before
	public void setUp() {
		indicator = new PreStopHealthIndicator();
		assertThat(indicator.health().getStatus()).isEqualTo(Status.UP);
		Assertions.assertThat(indicator.isEnabled()).isTrue();
		assertThat(indicator.isSensitive()).isFalse();
		assertThat(indicator.getId()).isEqualTo(PreStopHealthIndicator.ID);
	}

	@Test
	public void testPreStopHealthIndicator() {
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	public void testPreStopHealthIndicatorPreStopInvokedWithTime() {
		indicator.invokePrestopInternal(5);
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

	@Test
	public void testPreStopHealthIndicatorPreStopInvokedWithDefaultTime() {
		indicator.invoke();
		Health health = indicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

}
