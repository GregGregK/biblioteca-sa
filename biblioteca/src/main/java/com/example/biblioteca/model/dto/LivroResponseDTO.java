package com.example.biblioteca.model.dto;

import com.example.biblioteca.model.Livro;
public record LivroResponseDTO(Long id, String title, Long ISBN, String descricao, String autor) {

    public LivroResponseDTO(Livro livro){
        this(livro.getId(), livro.getTitle(), livro.getISBN(), livro.getDescricao(), livro.getAutor());

    }
}
