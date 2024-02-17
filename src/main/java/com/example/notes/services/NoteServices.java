package com.example.notes.services;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.domain.entity.NotesEntity;
import com.example.notes.enums.SessionEnum;
import com.example.notes.repository.NotesRepository;
import com.example.notes.utils.impl.JwtUtilsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServices {
  @Autowired private HttpServletRequest request;
  @Autowired private NotesRepository notesRepository;
  @Autowired private JwtUtilsImpl jwtUtils;
  
  public StandardResponseDto createNote(NotesCreateDto notesCreateDto) {
    var httpSession = request.getSession();
    if (notesCreateDto.getDescription() == null) {
      notesCreateDto.setDescription(
          notesCreateDto
                  .getContent()
                  .substring(0, Math.min(notesCreateDto.getContent().length(), 10))
              + "...");
    }
    var entity  = new NotesEntity(notesCreateDto);
    entity.setUserUuid(httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString());
    var save = notesRepository.save(entity);
    return new StandardResponseDto(HttpStatus.CREATED, save);
  }

  @Cacheable(value = "notes-get", key = "#uuid")
  public StandardResponseDto getContentById(String uuid) {
    var content = notesRepository.getContentById(uuid);
    if (content == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    return new StandardResponseDto(HttpStatus.OK, content);
  }

  @CachePut(value = "notes-get", key = "#uuid")
  public StandardResponseDto updateContentById(String uuid, NotesUpdateDto notesUpdateDto) {
    var response = notesRepository.findById(uuid);
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    var entity = new NotesEntity(uuid, notesUpdateDto);
    entity.setUserUuid(response.get().getUserUuid());
    entity.setCreatedAt(response.get().getCreatedAt());
    var update = notesRepository.save(entity);
    return new StandardResponseDto(HttpStatus.OK, update);
  }

  @CacheEvict(value = "notes-get", key = "#uuid")
  public StandardResponseDto deleteNotesById(String uuid) {
    var response = notesRepository.findById(uuid);
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    notesRepository.deleteById(uuid);
    return new StandardResponseDto(HttpStatus.OK, response);
  }

  public StandardResponseDto listNotes() {
    var httpSession = request.getSession();
    var userUuid = httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString();
    var response = notesRepository.listNotes(userUuid);
    if (response.isEmpty()) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    return new StandardResponseDto(HttpStatus.OK, response);
  }
}
