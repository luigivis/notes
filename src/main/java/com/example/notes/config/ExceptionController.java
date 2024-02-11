package com.example.notes.config;

import com.example.notes.dto.generic.StandardResponseDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.notes.dto.generic.StandardResponseDto.GenerateHttpResponse;

@Controller
public class ExceptionController implements ErrorController {

  @RequestMapping("/error")
  public ResponseEntity<StandardResponseDto> handleError(HttpServletRequest request) {
    var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    var standardResponse = new StandardResponseDto();
    if (status != null) {
      var statusCode = Integer.parseInt(status.toString());
      standardResponse = new StandardResponseDto(HttpStatus.valueOf(statusCode));
    }
    return GenerateHttpResponse(standardResponse);
  }
}
