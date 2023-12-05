package com.whattsApp.whatsappClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.whattsApp.whatsappClone")
public class WhatsappCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsappCloneApplication.class, args);
	}

}
