package com.example.notes.dto.notes;

import java.util.Date;

public interface NotesListRequestDto {
  String getId();

  String getTitle();

  String getDescription();

  String getShare();

  Date getCreatedAt();

  Date getUpdatedAt();
}
