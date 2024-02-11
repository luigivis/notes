package com.example.notes.repository;

import com.example.notes.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesEntity, Long> {
}
