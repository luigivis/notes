package com.example.notes.controller;

import com.example.notes.dto.generic.StandardResponseDto;
import com.example.notes.dto.notes.NotesCreateDto;
import com.example.notes.dto.notes.NotesUpdateDto;
import com.example.notes.services.NoteServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.notes.dto.generic.StandardResponseDto.GenerateHttpResponse;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

  @Autowired private ObjectMapper objectMapper;

  @Autowired private NoteServices noteServices;

  @PostMapping("/create")
  public ResponseEntity<StandardResponseDto> createNote(
      @Valid @RequestBody NotesCreateDto notesCreateDto) {
    var response = noteServices.createNote(notesCreateDto);
    return GenerateHttpResponse(response);
  }

  @GetMapping("/get/{id}/content")
  public ResponseEntity<Object> getContentById(@PathVariable Long id) {

    var response = noteServices.getContentById(id);

    if (response.getHttpStatus().is2xxSuccessful()) {
      var headers = new HttpHeaders();
      headers.setContentType(MediaType.TEXT_PLAIN);
      return ResponseEntity.ok().headers(headers).body(response.getData());
    }

    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<StandardResponseDto> updateContentById(
      @RequestBody NotesUpdateDto notesUpdateDto, @PathVariable Long id) {
    var response = noteServices.updateContentById(id, notesUpdateDto);
    return GenerateHttpResponse(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<StandardResponseDto> deleteNotesById(@PathVariable Long id) {
    var response = noteServices.deleteNotesById(id);
    return GenerateHttpResponse(response);
  }

  @GetMapping("/list")
  public ResponseEntity<StandardResponseDto> listNotes() {
    var response = noteServices.listNotes();
    return GenerateHttpResponse(response);
  }
}
