package com.example.notes.domain.entity;

import com.example.notes.domain.entity.common.StandardTable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UsersEntity extends StandardTable implements Serializable {
  @Serial
  private static final long serialVersionUID = 2309636893737196450L;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "uuid")
  private String uuid;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "status")
  private Boolean status;
}
