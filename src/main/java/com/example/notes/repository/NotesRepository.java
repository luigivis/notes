package com.example.notes.repository;

import com.example.notes.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotesRepository extends JpaRepository<NotesEntity, Long> {

    @Query(value = "select content from notes where id = ?", nativeQuery = true)
    String getContentById(Long id);
}
