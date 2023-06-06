package com.example.camunda.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZeebeEventProcessInstanceValue {

    String bpmnProcessId;
    String elementId;
    String bpmnElementType;
    long processInstanceKey;
    long parentProcessInstanceKey;

}
