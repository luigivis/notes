package com.example.notes.domain.dto.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NotesUpdateDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 7221455010625923929L;
  
  @Size(max = 50, message = "The title max size is reached")
  @NotBlank(message = "The title can't be null")
  private String title;

  @NotBlank(message = "The description can't be null")
  private String description;

  @NotBlank(message = "The content can't be null")
  private String content;
}
