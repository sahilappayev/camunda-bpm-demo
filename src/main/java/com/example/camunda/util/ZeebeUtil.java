package com.example.camunda.util;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZeebeUtil {

    private final ZeebeClient zeebeClient;

    public static void logJob(final ActivatedJob job, Object parameterValue) {
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


    public void publishMessageEvent(Object variables){
        zeebeClient.newPublishMessageCommand()
                .messageName("updateOrderStatus")
                .correlationKey("processId")
                .variables(variables)
                .send()
                .exceptionally(e -> {
                    throw new RuntimeException("Exception occurred while job execution: " + e.getMessage(), e);
                });
    }

}
