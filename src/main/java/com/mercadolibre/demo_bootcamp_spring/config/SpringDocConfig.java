package com.mercadolibre.demo_bootcamp_spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.mercadolibre.demo_bootcamp_spring.util.ScopeUtils.SCOPE_VALUE;
import static com.mercadolibre.demo_bootcamp_spring.util.ScopeUtils.isLocalScope;
import static java.lang.String.format;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${app.title}") String appName, @Value("${app.description}") String description, @Value("${app.version}") String version) {
		OpenAPI api = new OpenAPI().info(new Info().title(appName)
				.version(version)
				.description(description)
				.contact(new Contact().name("demo-bootcamp-spring")
						.email("javier.cordobes@mercadolibre.com")));

		api.addServersItem(new Server().url(isLocalScope() ? "http://localhost:8080" : format("https://%s_%s.furyapps.io", SCOPE_VALUE, appName))
				.description(format("Scope %s", SCOPE_VALUE)));
		return api;
	}
}