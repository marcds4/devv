package com.example.devFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@SpringBootApplication(scanBasePackages = "com.example.devFlow")

public class DevFlowApplication implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	public static void main(String[] args) {
		SpringApplication.run(DevFlowApplication.class, args);
	}

	@Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(8082);
    }

}
