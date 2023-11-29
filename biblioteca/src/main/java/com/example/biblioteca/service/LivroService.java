package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.LivroRequestDTO;
import com.example.biblioteca.model.dto.LivroResponseDTO;

import java.util.List;

public interface LivroService {

    List<LivroResponseDTO> listAll();
    Livro create(LivroRequestDTO livro);
    Livro update(Livro livro);
    void delete(Long id);
}
