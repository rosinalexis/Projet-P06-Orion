package com.openclassrooms.mddapi.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    
    @NotNull
    @NotBlank
    @NotEmpty
    private String login;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 16)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}