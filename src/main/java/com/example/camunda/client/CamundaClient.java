package com.example.camunda.client;

import com.example.camunda.client.model.StartProcessRequestDto;
import com.example.camunda.client.model.StartProcessResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8080/engine-rest", name = "camunda-client")
public interface CamundaClient {

    @PostMapping(value = "/process-definition/key/{processKey}/start", consumes = MediaType.APPLICATION_JSON_VALUE)
    StartProcessResponseDto startProcess(@PathVariable("processKey") String processKey,
                                         @RequestBody StartProcessRequestDto startProcessRequestDto);

}
