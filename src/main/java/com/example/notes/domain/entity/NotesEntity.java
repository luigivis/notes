package com.example.notes.domain.entity;

import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.entity.common.StandardTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notes")
public class NotesEntity extends StandardTable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "content")
  private String content;

  @Column(name = "user_uuid")
  private String userUuid;

  @Column(name = "share")
  private Boolean share;

  public NotesEntity(NotesCreateDto notesCreateDto) {
    this.title = notesCreateDto.getTitle();
    this.description = notesCreateDto.getDescription();
    this.content = notesCreateDto.getContent();
    this.userUuid = notesCreateDto.getUserUuid();
    this.share = false;
    this.setCreatedAt(new Date());
  }

  public NotesEntity(Long id, NotesUpdateDto notesUpdateDto) {
    this.id = id;
    this.title = notesUpdateDto.getTitle();
    this.description = notesUpdateDto.getDescription();
    this.content = notesUpdateDto.getContent();
    this.share = false;
    this.setUpdatedAt(new Date());
  }

  @PreUpdate
  public void setUpdatedAt() {
    this.setUpdatedAt(new Date());
  }
}
