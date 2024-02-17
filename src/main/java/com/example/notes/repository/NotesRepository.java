package com.example.notes.repository;

import com.example.notes.domain.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepository extends JpaRepository<NotesEntity, String> {

  @Query(value = "select * from notes WHERE user_uuid = ?", nativeQuery = true)
  List<NotesEntity> listNotes(String userUuid);

  @Query(value = "select content from notes where uuid = ?", nativeQuery = true)
  String getContentById(String uuid);
}
