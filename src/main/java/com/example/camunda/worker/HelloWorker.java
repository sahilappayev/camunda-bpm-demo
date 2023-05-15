package com.example.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
        logJob(job, newVariables);
        log.info("Hello job completed!");
    }


    @JobWorker(type = "check-the-variables")
    public void  check(ActivatedJob job){
        log.info("check log: {}", job.getVariablesAsMap());
    }

    private static void logJob(final ActivatedJob job, Object parameterValue) {
        log.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]" +
                        "\n{deadline; {}]\n[headers: {}]\n[variable parameter: {}\n[variables: {}]",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                parameterValue,
                job.getVariables());
    }

}
