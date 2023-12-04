package com.example.biblioteca.model;

import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Aluno")
@Table(name = "Aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Long matricula;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String status;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Aluno(AlunoRequestDTO data) {
        this.nome = data.nome();
        this.matricula = data.matricula();
        this.status = data.status();
    }
}