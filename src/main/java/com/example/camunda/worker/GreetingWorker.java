package com.example.camunda.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.CustomHeaders;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class GreetingWorker {


    private final ZeebeClient zeebeClient;

    @JobWorker(type = "greeting", autoComplete = false)
    public void greet(JobClient client, ActivatedJob job) {
        log.info("Greeting job started!");

        Map<String, Object> variables = new HashMap<>();
        Random random = new Random();
        variables.put("greet", random.nextBoolean());
        variables.put("pin", "XXX1234");
        variables.put("oldProcessId", "XXX1234old");
        variables.put("processId", "XXX1234new");

        if (random.nextBoolean()) {
            client.newThrowErrorCommand(job.getKey())
                    .errorCode("unexpectedError")
                    .errorMessage("Unexpected error occurred!").send()
                    .exceptionally(e -> {
                        throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
                    });
            throw new RuntimeException("Unexpected error occurred!");
        }

        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .exceptionally(e -> {
                    throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
                });

        log.info("Greeting job completed!");
    }


    @JobWorker(type = "message-event")
    public void messageEvent(ActivatedJob job, @CustomHeaders Map<String, String> headers) {
        log.info("messageEvent started with: {}", headers);
        boolean isOld = Boolean.parseBoolean(headers.get("isOld"));

        Map<String, Object> variables = job.getVariablesAsMap();
        Object processId = isOld ? variables.get("oldProcessId") : variables.get("processId");

//        throw new ZeebeBpmnError("", "");


        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("status", "cancel");
        variablesMap.put("cancelId", processId);
        variablesMap.put("errorCode", variables.get("errorCode"));


        zeebeClient.newPublishMessageCommand()
                .messageName("updateOrderStatus")
                .correlationKey("processId")
                .variables(variablesMap)
                .send()
                .exceptionally(e -> {
                    throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
                });
    }






}
