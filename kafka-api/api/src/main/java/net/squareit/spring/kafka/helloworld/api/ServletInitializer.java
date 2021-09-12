package net.squareit.spring.kafka.helloworld.api;

import net.squareit.spring.kafka.helloworld.SpringKafkaHelloworldApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringKafkaHelloworldApplication.class);
	}

}
