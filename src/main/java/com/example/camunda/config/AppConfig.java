package com.example.camunda.config;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@Deployment(resources = "classpath:bpmn/**/*.bpmn")
public class AppConfig {
}
