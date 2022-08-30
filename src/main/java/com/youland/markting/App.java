package com.youland.markting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.youland")
@EnableScheduling
public class App {
	public static void main(String[] args) {
		//ltang: Enabling auto restart on code change if needed
		//https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html#using-boot-devtools-livereload
		System.setProperty("spring.devtools.restart.enabled", "false");

		SpringApplication.run(App.class, args);
	}
}
