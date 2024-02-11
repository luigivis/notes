package com.example.notes.controller;

import com.example.notes.dto.generic.StandardResponseDto;
import com.example.notes.dto.notes.NotesCreateDto;
import com.example.notes.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.notes.dto.generic.StandardResponseDto.GenerateHttpResponse;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
  @Autowired private NoteServices noteServices;

  @PostMapping("/create")
  public ResponseEntity<StandardResponseDto> createNote(
      @RequestBody NotesCreateDto notesCreateDto) {
    var response = noteServices.createNote(notesCreateDto);
    return GenerateHttpResponse(response);
  }
}
