package com.example.camunda.controller;

import com.example.camunda.client.model.StartProcessResponseDto;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/process")
@RestController
public class HelloProcessController {


    private final ZeebeClient zeebeClient;
//    private final CamundaClient camundaClient;

//    @PostMapping("/start")
//    public ResponseEntity<?> start(@RequestParam String processKey) {
//
//        StartProcessResponseDto processResponseDto = camundaClient
//                .startProcess(processKey, new StartProcessRequestDto());
//
//        return ResponseEntity.ok().body(processResponseDto);
//    }

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestParam String processKey) {

        final ProcessInstanceResult event =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId(processKey)
                        .latestVersion()
//                        .variables("{\"a\": \"" + UUID.randomUUID().toString() + "\",\"b\": \"" + new Date().toString() + "\"}")
                        .withResult()
                        .send()
                        .join();

        StartProcessResponseDto processResponseDto = new StartProcessResponseDto();
        processResponseDto.setBusinessKey(event.getBpmnProcessId());
        processResponseDto.setDefinitionId(String.valueOf(event.getProcessDefinitionKey()));
        processResponseDto.setCaseInstanceId(String.valueOf(event.getProcessInstanceKey()));
        processResponseDto.setVersion(event.getVersion());

        return ResponseEntity.ok().body(processResponseDto);
    }


}
