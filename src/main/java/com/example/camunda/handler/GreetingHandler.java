package com.example.camunda.handler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.exception.ExternalTaskClientException;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Slf4j
@Component
@ExternalTaskSubscription("Greeting")
public class GreetingHandler implements ExternalTaskHandler {


    @Override
    public void execute(ExternalTask task, ExternalTaskService taskService) {

        log.info("{} External task started: {}", task.getTopicName(), task);

        Map<String, Object> variables = Variables.createVariables();
        Random random = new Random();
        variables.put("greet", random.nextBoolean());

        if (random.nextBoolean()){
            taskService.handleBpmnError(task, "unexpectedError", "Unexpected error occurred!");
            throw new ExternalTaskClientException("Unexpected error occurred!");
        }

        taskService.complete(task, variables);

        log.info("{} External task completed: {}", task.getTopicName(), task);

    }


}
