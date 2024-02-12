package com.example.notes.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthLoginRequestDto {

    @Size(min = 4, max = 50, message = "Username has to be between 4 and 50 characters")
    @NotBlank(message = "Username can't be null")
    private String username;

    @Size(min = 4, max = 50, message = "Password has to be between 4 and 50 characters")
    @NotBlank(message = "Password can't be null")
    private String password;

}
