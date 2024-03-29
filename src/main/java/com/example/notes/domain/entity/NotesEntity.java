package com.example.notes.domain.entity;

import com.example.notes.domain.dto.notes.NotesUpdateDto;
import com.example.notes.domain.dto.notes.NotesCreateDto;
import com.example.notes.domain.entity.common.StandardTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notes")
public class NotesEntity extends StandardTable implements Serializable {
  @Serial
  private static final long serialVersionUID = 4354024332827672261L;

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "uuid")
  private String uuid;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @JsonIgnore
  @Column(name = "content")
  private String content;

  @Column(name = "user_uuid")
  private String userUuid;

  @Column(name = "share")
  private Boolean share;

  public NotesEntity(NotesCreateDto notesCreateDto) {
    this.uuid = UUID.randomUUID().toString();
    this.title = notesCreateDto.getTitle();
    this.description = notesCreateDto.getDescription();
    this.content = notesCreateDto.getContent();
    this.share = false;
    this.setCreatedAt(new Date());
  }

  public NotesEntity(String uuid, NotesUpdateDto notesUpdateDto) {
    this.uuid = uuid;
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
