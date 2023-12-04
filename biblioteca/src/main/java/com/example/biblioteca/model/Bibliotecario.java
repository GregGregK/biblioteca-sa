package com.example.biblioteca.model;


import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "bibliotecario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bibliotecario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String status; // Ativo, Demitido, Ferias
    private String setor;
    private String contato;

    @Column(name = "created_at")
    private LocalDateTime createdAt;



    @PrePersist
    public void prePersist() { createdAt = LocalDateTime.now(); }



}
