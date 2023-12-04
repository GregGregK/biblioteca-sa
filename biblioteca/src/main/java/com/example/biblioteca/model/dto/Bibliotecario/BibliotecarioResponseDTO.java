package com.example.biblioteca.model.dto.Bibliotecario;

import com.example.biblioteca.model.Bibliotecario;
import lombok.Data;


@Data
public class BibliotecarioResponseDTO{


    private Long id;
    private String nome;
    private String status;
    private String setor;
    private String contato;


}
