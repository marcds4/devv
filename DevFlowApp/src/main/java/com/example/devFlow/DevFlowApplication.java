package com.example.devFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.devFlow")

public class DevFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevFlowApplication.class, args);
	}

}
