package com.example.notes.domain.dto.auth;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import lombok.Data;

@Data
public class AuthLoginResponseDto {
    private String token;
    private StandardResponseDto standardResponseDto;
}
