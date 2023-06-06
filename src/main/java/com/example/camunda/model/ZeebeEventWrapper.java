package com.example.camunda.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class ZeebeEventWrapper {

    private String intent;
    private String valueType;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "valueType",
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(value = {
            @JsonSubTypes.Type(value = ZeebeEventVariableValue.class, name = "VARIABLE"),
            @JsonSubTypes.Type(value = ZeebeEventJobValue.class, name = "JOB"),
            @JsonSubTypes.Type(value = ZeebeEventProcessInstanceValue.class,
                    name = "PROCESS_INSTANCE"),
            @JsonSubTypes.Type(value = ZeebeEventIncidentValue.class, name = "INCIDENT")
    })
    private Object value;

}
