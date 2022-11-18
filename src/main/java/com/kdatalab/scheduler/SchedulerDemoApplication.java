package com.kdatalab.scheduler;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchedulerDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SchedulerDemoApplication.class, args);
	}

}
