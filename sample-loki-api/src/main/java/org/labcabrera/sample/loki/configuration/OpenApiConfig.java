package org.labcabrera.sample.loki.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI(
			@Value("${springdoc.info.title:Application API}") String title,
			@Value("${springdoc.info.description:API description}") String description,
			@Value("${springdoc.info.version:0.0.0}") String version,
			@Value("${springdoc.info.contact.name:}") String contactName,
			@Value("${springdoc.info.contact.email:}") String contactEmail
	) {
		Info info = new Info()
				.title(title)
				.description(description)
				.version(version);

		if (contactName != null && !contactName.isBlank()) {
			Contact contact = new Contact();
			contact.setName(contactName);
			if (contactEmail != null && !contactEmail.isBlank()) {
				contact.setEmail(contactEmail);
			}
			info.setContact(contact);
		}

		return new OpenAPI().info(info);
	}

	@Bean
	public ApiInfo apiInfoBean(
			@Value("${springdoc.info.title:Application API}") String title,
			@Value("${springdoc.info.description:API description}") String description,
			@Value("${springdoc.info.version:0.0.0}") String version
	) {
		return new ApiInfo(title, description, version);
	}
}