package com.example.biblioteca.model.dto.Bibliotecario;

import com.example.biblioteca.model.Bibliotecario;

public record BibliotecarioResponseDTO(Long id, String nome, String status, String setor) {

    public BibliotecarioResponseDTO(Bibliotecario bibliotecario){
        this(bibliotecario.getId(), bibliotecario.getNome(), bibliotecario.getStatus(), bibliotecario.getSetor());
    }


}
