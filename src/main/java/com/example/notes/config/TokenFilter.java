package com.example.notes.config;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.enums.HeadersEnum;
import com.example.notes.utils.impl.JwtUtilsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenFilter implements Filter {

  @Autowired private HttpServletRequest request;
  @Autowired private JwtUtilsImpl jwtUtils;
  @Autowired private ObjectMapper objectMapper;

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    var httpSession = request.getSession();

    if (validUri(request.getRequestURI())) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    //if (httpSession.getAttribute(HeadersEnum.JWT.getValue()) == null) {
    //  responseUnauthorized(servletResponse);
    //  return;
    //}
//
    //var token = httpSession.getAttribute(HeadersEnum.JWT.getValue()).toString();
    //if (jwtUtils.isTokenExpired(token)) {
    //  responseUnauthorized(servletResponse);
    //  return;
    //}
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean validUri(String uri) {
    return uri.contains("/api/v1/auth")
        || uri.contains("swagger-ui")
        || uri.contains("/v3/api-docs")
        || uri.equals("/favicon.ico");
  }

  private void responseUnauthorized(ServletResponse servletResponse) throws IOException {
    var httpResponse = (HttpServletResponse) servletResponse;

    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    var standardDto = new StandardResponseDto(HttpStatus.UNAUTHORIZED);
    httpResponse.getWriter().write(objectMapper.writeValueAsString(standardDto));
  }
}
