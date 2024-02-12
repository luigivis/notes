package com.example.notes.services;

import com.example.notes.domain.dto.auth.AuthCreateRequestDto;
import com.example.notes.domain.dto.auth.AuthLoginRequestDto;
import com.example.notes.domain.dto.auth.AuthLoginResponseDto;
import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.domain.entity.common.StandardTable;
import com.example.notes.enums.HeadersEnum;
import com.example.notes.repository.UserRepository;
import com.example.notes.utils.impl.JwtUtilsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class AuthServices {

  @Autowired private JwtUtilsImpl jwtUtils;
  @Autowired private HttpServletRequest request;
  @Autowired private UserRepository userRepository;

  public StandardResponseDto registerUser(AuthCreateRequestDto authCreateRequestDto) {
    userRepository.insertNewUser(
        authCreateRequestDto.getUsername(),
        BCrypt.hashpw(authCreateRequestDto.getPassword(), BCrypt.gensalt()));
    return new StandardResponseDto(HttpStatus.CREATED);
  }

  public StandardResponseDto login(AuthLoginRequestDto authLoginRequestDto) {
    var httpSession = request.getSession();
    var user = userRepository.findByUsername(authLoginRequestDto.getUsername());
    if (user == null) return new StandardResponseDto(HttpStatus.NOT_FOUND);
    if (!BCrypt.checkpw(authLoginRequestDto.getPassword(), user.getPassword())){
      return new StandardResponseDto(HttpStatus.UNAUTHORIZED);
    }
    var token = jwtUtils.generateToken(user.getUsername());
    httpSession.setAttribute(HeadersEnum.JWT.getValue(), token);
    return new StandardResponseDto(HttpStatus.OK, token);
  }
}
