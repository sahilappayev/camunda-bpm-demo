package com.example.camunda;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@Deployment(resources = {"classpath:bpmn/sayHelloZeebe.bpmn"})
public class CamundaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaDemoApplication.class, args);
	}

}
