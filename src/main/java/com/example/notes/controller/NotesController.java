package com.example.notes.controller;

import com.example.notes.domain.dto.generic.StandardResponseDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.services.NoteServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

import static com.example.notes.domain.dto.generic.StandardResponseDto.GenerateHttpResponse;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController implements Serializable {

  @Serial private static final long serialVersionUID = 4740984250062398249L;

  @Autowired private NoteServices noteServices;

  @GetMapping("/list")
  public ResponseEntity<StandardResponseDto> getNotes() {
    var response = noteServices.listNotes();
    return GenerateHttpResponse(response);
  }

  @PostMapping("/create")
  public ResponseEntity<StandardResponseDto> createNote(
      @Valid @RequestBody NotesCreateDto notesCreateDto) {
    var response = noteServices.createNote(notesCreateDto);
    return GenerateHttpResponse(response);
  }

  @GetMapping("/get/content/{uuid}")
  public ResponseEntity<Object> getContentById(@PathVariable String uuid) {

    var response = noteServices.getContentById(uuid);

    if (response.getHttpStatus().is2xxSuccessful()) {
      var headers = new HttpHeaders();
      headers.setContentType(MediaType.TEXT_PLAIN);
      return ResponseEntity.ok().headers(headers).body(response.getData());
    }

    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }

  @PutMapping("/update/{uuid}")
  public ResponseEntity<StandardResponseDto> updateContentById(
      @RequestBody NotesUpdateDto notesUpdateDto, @PathVariable String uuid) {
    var response = noteServices.updateContentById(uuid, notesUpdateDto);
    return GenerateHttpResponse(response);
  }

  @DeleteMapping("/delete/{uuid}")
  public ResponseEntity<StandardResponseDto> deleteNotesById(@PathVariable String uuid) {
    var response = noteServices.deleteNotesById(uuid);
    return GenerateHttpResponse(response);
  }

  @PutMapping("/change/status/{uuid}")
  public  ResponseEntity<StandardResponseDto>changeStatusShare(@PathVariable String uuid){
    var response = noteServices.changeStatusShare(uuid);
    return  GenerateHttpResponse(response);
  }
}
