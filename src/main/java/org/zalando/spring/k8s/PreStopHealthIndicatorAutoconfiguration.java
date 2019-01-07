package org.zalando.spring.k8s;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AutoConfiguration to create {@link PreStopHealthIndicator} bean. 
 *
 */
@Configuration
@ConditionalOnClass({Endpoint.class, AbstractHealthIndicator.class})
public class PreStopHealthIndicatorAutoconfiguration {

	@Bean
	@ConditionalOnMissingBean
	PreStopHealthIndicator preStopHealhtIndicator() {
		return new PreStopHealthIndicator();
	}
}
