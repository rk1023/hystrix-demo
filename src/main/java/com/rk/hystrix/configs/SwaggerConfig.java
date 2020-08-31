package com.rk.hystrix.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Product api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket commonsApiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.rk.hystrix"))
				.build();

	}

}
