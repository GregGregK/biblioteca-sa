package com.example.biblioteca.model.dto.Livro;

import lombok.Data;

@Data
public class LivroResponseDTO {

    private String title;
    private String autor;
    private String descricao;
    private Long ISBN;
    private String disponibilidade;
}
