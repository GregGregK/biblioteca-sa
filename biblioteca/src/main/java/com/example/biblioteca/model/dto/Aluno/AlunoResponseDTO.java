package com.example.biblioteca.model.dto.Aluno;

import com.example.biblioteca.model.Aluno;

public record AlunoResponseDTO(Long id, String nome, Long matricula, String status) {

    public AlunoResponseDTO(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getMatricula(), aluno.getStatus());
    }
}
