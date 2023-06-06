package com.example.camunda.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZeebeEventVariableValue {

    long processInstanceKey;
    String name;
    String value;

}
