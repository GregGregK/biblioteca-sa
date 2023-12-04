package com.example.biblioteca.model.dto.Livro;

public record LivroRequestDTO(String title, String autor, String descricao, Long ISBN, String disponibilidade) {
}