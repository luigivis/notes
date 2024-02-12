package com.example.notes.repository;

import com.example.notes.domain.dto.notes.NotesListRequestDto;
import com.example.notes.domain.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepository extends JpaRepository<NotesEntity, Long> {

  @Query(
      value = "select id, title, description, share, created_at as createdAt, updated_at as updatedAt from notes",
      nativeQuery = true)
  List<NotesListRequestDto> listNotes();

  @Query(value = "select content from notes where id = ?", nativeQuery = true)
  String getContentById(Long id);
}
