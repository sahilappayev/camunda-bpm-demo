package com.example.camunda.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZeebeEventJobValue {

    String bpmnProcessId;
    String elementId;
    Long processInstanceKey;
    Map<String, Object> variables;
    String errorMessage;
    String errorCode;

}
