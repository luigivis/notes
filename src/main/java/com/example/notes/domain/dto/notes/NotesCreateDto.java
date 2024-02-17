package com.example.notes.domain.dto.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NotesCreateDto implements Serializable {

  @Serial private static final long serialVersionUID = 3948281839799747391L;

  @Size(max = 50, message = "The title max size is reached")
  @NotBlank(message = "The title can't be null")
  private String title;

  private String description;

  @NotBlank(message = "The content can't be null")
  private String content;
}
