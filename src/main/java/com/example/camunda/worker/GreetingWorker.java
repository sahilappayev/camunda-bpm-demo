package com.example.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
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


//    private final ZeebeClient zeebeClient;

    @JobWorker(type = "greeting", autoComplete = false)
    public void greet(JobClient client, ActivatedJob job) {
        log.info("Greeting job started!");

        Map<String, Object> variables = new HashMap<>();
        Random random = new Random();
        variables.put("greet", random.nextBoolean());
        variables.put("pin", "XXX1234");

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

//        zeebeClient.newPublishMessageCommand()
//                .messageName("test")
//                .correlationKey(correlationKey)
//                .send()
//                .exceptionally(e -> {
//                    throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
//                });
    }



}
