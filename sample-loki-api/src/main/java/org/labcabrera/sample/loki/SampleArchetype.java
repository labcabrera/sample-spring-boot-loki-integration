package org.labcabrera.sample.loki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = { "org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration" })
public class SampleArchetype {

	public static void main(String[] args) {
		SpringApplication.run(SampleArchetype.class, args);
	}

}
