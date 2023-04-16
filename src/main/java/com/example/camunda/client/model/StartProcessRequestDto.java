package com.example.camunda.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class StartProcessRequestDto {

    List<Objects> variables;
    String businessKey;


//    @Getter
//    @Setter
//    @ToString
//    public static class Entry {
//        String value;
//        String type;
//    }

}
