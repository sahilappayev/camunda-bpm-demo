//package com.example.camunda.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
//import org.camunda.bpm.client.task.ExternalTask;
//import org.camunda.bpm.client.task.ExternalTaskHandler;
//import org.camunda.bpm.client.task.ExternalTaskService;
//import org.camunda.bpm.engine.variable.VariableMap;
//import org.camunda.bpm.engine.variable.Variables;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@ExternalTaskSubscription("HelloWorld")
//public class HelloHandler implements ExternalTaskHandler {
//
//
//    @Override
//    public void execute(ExternalTask task, ExternalTaskService taskService) {
//
//        log.info("{} External task started: {}", task.getTopicName(), task);
//
//        VariableMap variables = Variables.createVariables();
//        variables.put("retVal", "Hello World!");
//
//        taskService.complete(task, variables);
//
//        log.info("{} External task completed: {}", task.getTopicName(), task);
//
//    }
//
//
//}
