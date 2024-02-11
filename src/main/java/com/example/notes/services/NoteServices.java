package com.example.notes.services;

import com.example.notes.dto.generic.StandardResponseDto;
import com.example.notes.dto.notes.NotesCreateDto;
import com.example.notes.entity.NotesEntity;
import com.example.notes.repository.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServices {
  @Autowired private NotesRepository notesRepository;

  public StandardResponseDto createNote(NotesCreateDto notesCreateDto) {
    if (notesCreateDto.getDescription() == null) {
      notesCreateDto.setDescription(
          notesCreateDto
                  .getContent()
                  .substring(0, Math.min(notesCreateDto.getContent().length(), 10))
              + "...");
    }
    var save = notesRepository.save(new NotesEntity(notesCreateDto));
    return new StandardResponseDto(HttpStatus.CREATED, save);
  }
}
