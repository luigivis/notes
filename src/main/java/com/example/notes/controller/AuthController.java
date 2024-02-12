package com.example.notes.controller;

import com.example.notes.domain.dto.auth.AuthCreateRequestDto;
import com.example.notes.domain.dto.auth.AuthLoginRequestDto;
import com.example.notes.domain.dto.auth.AuthLoginResponseDto;
import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.enums.HeadersEnum;
import com.example.notes.services.AuthServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.notes.domain.dto.generic.StandardResponseDto.GenerateHttpResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired private HttpServletRequest request;

  @Autowired private AuthServices authServices;

  @PostMapping("/register")
  public ResponseEntity<StandardResponseDto> createNewUser(
      @Valid @RequestBody AuthCreateRequestDto authCreateRequestDto) {
    var response = authServices.registerUser(authCreateRequestDto);
    return GenerateHttpResponse(response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthLoginRequestDto authLoginRequestDto) {
    var response = authServices.login(authLoginRequestDto);

    if (response.getHttpStatus().is2xxSuccessful()) {
      var httpHeader = new HttpHeaders();
      httpHeader.add(HeadersEnum.JWT.getValue(), response.getData().toString());
      return ResponseEntity.status(response.getHttpStatus()).headers(httpHeader).body(response);
    }

    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }

  @GetMapping("/logout")
  public ResponseEntity<StandardResponseDto> logout() {
    var httpSession = request.getSession();
    httpSession.invalidate();
    return GenerateHttpResponse(new StandardResponseDto(HttpStatus.OK));
  }

  @GetMapping("/valid")
  public ResponseEntity<StandardResponseDto> valid() {
    var httpSession = request.getSession();
    if (httpSession.getAttribute(HeadersEnum.JWT.getValue()) == null)
      return GenerateHttpResponse(new StandardResponseDto(HttpStatus.UNAUTHORIZED));
    return GenerateHttpResponse(new StandardResponseDto(HttpStatus.OK));
  }
}
