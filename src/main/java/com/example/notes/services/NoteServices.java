package com.example.notes.services;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.domain.entity.NotesEntity;
import com.example.notes.repository.NotesRepository;
import com.example.notes.utils.impl.JwtUtilsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServices {
  @Autowired private HttpServletRequest request;
  @Autowired private NotesRepository notesRepository;
  @Autowired private JwtUtilsImpl jwtUtils;
  
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

  public StandardResponseDto getContentById(Long id) {
    var content = notesRepository.getContentById(id);
    if (content == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    return new StandardResponseDto(HttpStatus.OK, content);
  }

  public StandardResponseDto updateContentById(Long id, NotesUpdateDto notesUpdateDto) {
    var response = notesRepository.findById(id);
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    var entity = new NotesEntity(id, notesUpdateDto);
    entity.setUserUuid(response.get().getUserUuid());
    entity.setCreatedAt(response.get().getCreatedAt());
    var update = notesRepository.save(entity);
    return new StandardResponseDto(HttpStatus.OK, update);
  }

  public StandardResponseDto deleteNotesById(Long id) {
    var response = notesRepository.findById(id);
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    notesRepository.deleteById(id);
    return new StandardResponseDto(HttpStatus.OK, response);
  }

  public StandardResponseDto listNotes() {
    var response = notesRepository.listNotes();
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    return new StandardResponseDto(HttpStatus.OK, response);
  }
}
