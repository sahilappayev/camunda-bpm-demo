package com.example.camunda.controller;

import com.example.camunda.client.CamundaClient;
import com.example.camunda.client.model.StartProcessRequestDto;
import com.example.camunda.client.model.StartProcessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/process/hello")
@RestController
public class HelloProcessController {


    private final CamundaClient camundaClient;

    @PostMapping("/start")
    public ResponseEntity<?> start() {

        StartProcessResponseDto processResponseDto = camundaClient
                .startProcess("SayHello", new StartProcessRequestDto());

        return ResponseEntity.ok().body(processResponseDto);
    }


}
