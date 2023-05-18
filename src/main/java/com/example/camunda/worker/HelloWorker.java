package com.example.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloWorker {

    @JobWorker(type = "hello", autoComplete = false)
    public void hello(JobClient client, ActivatedJob job) {
        log.info("Hello job started!");

//        Map<String, Object> variables = job.getVariablesAsMap();

        Map<String, String> newVariables = new HashMap<>();
        newVariables.put("newPin", "1234XXX");

        client.newCompleteCommand(job.getKey())
                .variables(newVariables)
                .send()
                .exceptionally(e -> {
                    throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
                });
        log.info("Hello job completed!");
    }




}
