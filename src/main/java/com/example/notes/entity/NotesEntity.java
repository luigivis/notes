package com.example.notes.entity;

import com.example.notes.dto.notes.NotesCreateDto;
import com.example.notes.entity.common.StandardTable;
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
}
