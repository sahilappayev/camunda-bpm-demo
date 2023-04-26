package com.example.camunda.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class StartProcessResponseDto {

    private Link[] links;
    private String id;
    private int version;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private Boolean ended;
    private Boolean suspended;
    private String tenantId;

    @ToString
    @Getter
    @Setter
    public static class Link {

        private String method;
        private String href;
        private String rel;

    }
}
