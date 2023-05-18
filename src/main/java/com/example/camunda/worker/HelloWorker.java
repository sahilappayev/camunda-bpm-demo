package com.example.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.CustomHeaders;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
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


    @JobWorker(type = "check-the-variables")
    public UserDto check(ActivatedJob job, @CustomHeaders Map<String, String> headers) {
        log.info("check job started!");

        UserDto userDto = UserDto.builder()
                .name("Sahil")
                .surname("Appayev")
                .birthday(LocalDate.now())
                .accounts(new String[]{"account123", "account456"})
                .build();

        logJob(job, headers);
        log.info("check log: {}", job.getVariablesAsMap());
        return userDto;
    }

    @JobWorker(type = "check-context")
    public void checkContext(ActivatedJob job, @VariablesAsType ContextDto contextDto) {
        log.info("checkContext job started with: {}", contextDto);
        logJob(job, null);
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

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDto {
        private String name;
        private String surname;
        private LocalDate birthday;
        private String[] accounts;
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class ContextDto {
        private Boolean greet;
        private String pin;
        private String oldProcessId;
        private String processId;
        private String name;
        private String surname;
        private LocalDate birthday;
        private String[] accounts;
    }

}
