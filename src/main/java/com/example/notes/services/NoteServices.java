package com.example.notes.services;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.domain.entity.NotesEntity;
import com.example.notes.enums.SessionEnum;
import com.example.notes.repository.NotesRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServices {
  @Autowired private HttpServletRequest request;
  @Autowired private NotesRepository notesRepository;

  public StandardResponseDto createNote(NotesCreateDto notesCreateDto) {
    var httpSession = request.getSession();
    if (notesCreateDto.getDescription() == null) {
      notesCreateDto.setDescription(
          notesCreateDto
                  .getContent()
                  .substring(0, Math.min(notesCreateDto.getContent().length(), 10))
              + "...");
    }
    var entity = new NotesEntity(notesCreateDto);
    entity.setUserUuid(httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString());
    var save = notesRepository.save(entity);
    return new StandardResponseDto(HttpStatus.CREATED, save);
  }

  public StandardResponseDto getContentById(String uuid) {
    var httpSession = request.getSession();
    var userUuid = "";
    if (httpSession.getAttribute(SessionEnum.USER_UUID.name()) != null) {
      userUuid = httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString();
    }

    var content = notesRepository.findNoteByUuid(uuid);
    if (content == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    if (content.getUserUuid().equals(userUuid)) {
      return new StandardResponseDto(HttpStatus.OK, content.getContent());
    }
    if (content.getShare()) {
      return new StandardResponseDto(HttpStatus.OK, content.getContent());
    }
    return new StandardResponseDto(HttpStatus.UNAUTHORIZED);
  }

  @CachePut(value = "notes-get", key = "#uuid")
  public StandardResponseDto updateContentById(String uuid, NotesUpdateDto notesUpdateDto) {
    var httpSession = request.getSession();
    var userUuid = httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString();
    var notes = notesRepository.findNoteByUuidAndUserUuid(uuid, userUuid);
    if (notes == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    var entity = new NotesEntity(uuid, notesUpdateDto);
    entity.setUserUuid(notes.getUserUuid());
    entity.setCreatedAt(notes.getCreatedAt());
    var update = notesRepository.save(entity);
    return new StandardResponseDto(HttpStatus.OK, update);
  }

  @CacheEvict(value = "notes-get", key = "#uuid")
  public StandardResponseDto deleteNotesById(String uuid) {
    var httpSession = request.getSession();
    var userUuid = httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString();
    var notes = notesRepository.findNoteByUuidAndUserUuid(uuid, userUuid);
    if (notes == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    notesRepository.deleteById(uuid);
    return new StandardResponseDto(HttpStatus.OK, notes);
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

  public StandardResponseDto changeStatusShare(String uuid) {
    var httpSession = request.getSession();
    var userUuid = httpSession.getAttribute(SessionEnum.USER_UUID.name()).toString();
    var notes = notesRepository.findNoteByUuidAndUserUuid(uuid, userUuid);
    if (notes == null) {
      return new StandardResponseDto(HttpStatus.NOT_FOUND);
    }
    notes.setShare(!notes.getShare());
    notesRepository.save(notes);
    return new StandardResponseDto(HttpStatus.OK);
  }
}
