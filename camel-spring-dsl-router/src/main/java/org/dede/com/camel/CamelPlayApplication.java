package org.dede.com.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:camel-context.xml")
public class CamelPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelPlayApplication.class, args);
	}

}
