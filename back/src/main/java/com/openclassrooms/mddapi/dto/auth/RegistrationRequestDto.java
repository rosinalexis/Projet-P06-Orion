package com.openclassrooms.mddapi.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegistrationRequestDto {
    @NotNull
    @NotBlank
    @NotEmpty
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 16)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
