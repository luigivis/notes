package com.example.notes.dto.notes;

import lombok.Data;

@Data
public class NotesCreateDto {
    private String title;
    private String description;
    private String content;
    private String userUuid;
}
