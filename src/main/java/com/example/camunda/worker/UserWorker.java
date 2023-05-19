package com.example.camunda.worker;

import com.example.camunda.util.ZeebeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
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

import java.time.LocalDate;
import java.util.Map;

import static com.example.camunda.util.ZeebeUtil.logJob;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserWorker {

    private final ZeebeUtil zeebeUtil;
    private final ObjectMapper objectMapper;

    @JobWorker(type = "check-the-variables")
    public UserDto check(ActivatedJob job, @CustomHeaders Map<String, String> headers) {
        log.info("check job started!");

        UserDto userDto = UserDto.builder()
                .name("Sahil")
                .surname("Appayev")
                .birthday(LocalDate.of(1995, 10, 20))
                .accounts(new String[]{"account123", "account456"})
                .address(Address.builder().country("USA").city("NYC").name("New York").build())
                .build();

        logJob(job, headers);
        log.info("check log: {}", job.getVariablesAsMap());
        return userDto;
    }

    @JobWorker(type = "check-context")
    public void checkContext(ActivatedJob job, @VariablesAsType ContextDto contextDto) {
        log.info("checkContext job started with: {}", contextDto);
        log.info("ObjectMapper: {}", objectMapper.getClass().getSimpleName());
        Address address = objectMapper.convertValue(job.getVariablesAsMap().get("address"), Address.class);
        Object addressName = job.getVariablesAsMap().get("address.name");
        log.info("Address: {} \n Address Name: {}", address, addressName);
        logJob(job, null);
    }


    @JobWorker(type = "get-full-name")
    public UserInfo getUserInfo(@VariablesAsType ContextDto contextDto) {
        log.info("getUserInfo job started with: {}", contextDto);
        return UserInfo.builder()
                .fullName(contextDto.getName() + " " + contextDto.getSurname())
                .age(LocalDate.now().minusYears(contextDto.getBirthday().getYear()).getYear())
                .build();
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

        private Address address;
    }


    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address {
        private String country;
        private String city;
        private String name;
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
        private String fullName;
        private int age;
        private String[] accounts;
    }

    @ToString
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String fullName;
        private int age;
    }


}
