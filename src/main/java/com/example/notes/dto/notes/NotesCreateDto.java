package com.example.notes.dto.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotesCreateDto {

    @Size(max = 50, message = "The title max size is reached")
    @NotBlank(message = "The title can't be null")
    private String title;

    private String description;

    @NotBlank(message = "The content can't be null")
    private String content;

    @Size(max = 36, message = "The userUuid max size is reached")
    @NotBlank(message = "The userUuid can't be null")
    private String userUuid;
}
