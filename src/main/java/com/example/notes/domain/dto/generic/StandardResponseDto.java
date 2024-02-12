/**
 * Represents a standard response data transfer object (DTO) used for API responses. This class
 * includes information such as status code, description, HTTP status, and optional data payload.
 *
 * @author com.example.notes.dto.generic
 * @version 1.0
 */
package com.example.notes.domain.dto.generic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** A data transfer object for standard API responses. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponseDto {

  /** The numeric status code representing the outcome of the API request. */
  private int code;

  /** A brief description providing additional context about the API response. */
  private String description;

  /** The HTTP status associated with the API response. */
  @JsonIgnore private HttpStatus httpStatus;

  /** An optional data payload that can be included in the API response. */
  private Object data;

  /**
   * Generates an HTTP response entity using the provided StandardResponseDto.
   *
   * @param dto The StandardResponseDto to be included in the response entity.
   * @return ResponseEntity containing the provided StandardResponseDto and its associated HTTP
   *     status.
   */
  public static ResponseEntity<StandardResponseDto> GenerateHttpResponse(StandardResponseDto dto) {
    return ResponseEntity.status(dto.getHttpStatus()).body(dto);
  }

  /**
   * Creates a new StandardResponseDto with the specified code, description, and HTTP status.
   *
   * @param code The numeric status code.
   * @param description A brief description providing additional context.
   * @param httpStatus The HTTP status associated with the response.
   */
  public StandardResponseDto(int code, String description, HttpStatus httpStatus) {
    this.code = code;
    this.description = description;
    this.httpStatus = httpStatus;
  }

  /**
   * Creates a new StandardResponseDto with the HTTP status provided.
   *
   * @param httpStatus The HTTP status associated with the response.
   */
  public StandardResponseDto(HttpStatus httpStatus) {
    this.code = httpStatus.value();
    this.description = httpStatus.getReasonPhrase();
    this.httpStatus = httpStatus;
  }

  /**
   * Creates a new StandardResponseDto with the specified HTTP status and data payload.
   *
   * @param httpStatus The HTTP status associated with the response.
   * @param data An optional data payload to be included in the response.
   */
  public StandardResponseDto(HttpStatus httpStatus, Object data) {
    this.code = httpStatus.value();
    this.description = httpStatus.getReasonPhrase();
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
