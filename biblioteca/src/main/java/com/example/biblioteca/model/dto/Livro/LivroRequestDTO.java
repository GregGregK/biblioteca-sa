package com.example.biblioteca.model.dto.Livro;


import lombok.Data;

@Data
public class LivroRequestDTO {


    private String title;
    private String autor;
    private String descricao;
    private Long ISBN;

}