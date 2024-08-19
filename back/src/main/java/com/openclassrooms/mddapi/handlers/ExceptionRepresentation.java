package com.openclassrooms.mddapi.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionRepresentation {

    @JsonProperty("message")
    private String errorMessage;
    //@JsonIgnore
    private String errorSource;
    //@JsonIgnore
    private Set<String> validationErrors;
}