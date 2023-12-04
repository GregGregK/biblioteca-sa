package com.example.biblioteca.model.dto.Aluno;

import com.example.biblioteca.model.Aluno;
import lombok.Data;

@Data
public class AlunoResponseDTO{

    private String nome;
    private Long matricula;
    private String status;
    private String contato;

}
