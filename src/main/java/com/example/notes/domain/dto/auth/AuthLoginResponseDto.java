package com.example.notes.domain.dto.auth;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthLoginResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2621753788261026184L;

    private String token;
    private StandardResponseDto standardResponseDto;
}
