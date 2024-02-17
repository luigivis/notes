package com.example.notes.domain.dto.notes;

import java.io.Serializable;
import java.util.Date;

public interface NotesListRequestDto extends Serializable {
  String getId();

  String getTitle();

  String getDescription();

  String getShare();

  Date getCreatedAt();

  Date getUpdatedAt();
}
