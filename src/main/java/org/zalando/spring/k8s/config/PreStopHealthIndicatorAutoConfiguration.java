package org.zalando.spring.k8s.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.spring.k8s.PreStopHealthIndicator;

@Configuration
@ConditionalOnClass({Endpoint.class, AbstractHealthIndicator.class})
public class PreStopHealthIndicatorAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	PreStopHealthIndicator preStopHealthIndicator() {
		return new PreStopHealthIndicator();
	}

}
