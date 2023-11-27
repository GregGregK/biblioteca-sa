package com.example.biblioteca.controller;


import com.example.biblioteca.livro.Livro;
import com.example.biblioteca.livro.LivroRepository;
import com.example.biblioteca.livro.LivroRequestDTO;
import com.example.biblioteca.livro.LivroResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("livro")
public class LivroController {


    @Autowired
    private LivroRepository repository;

    @CrossOrigin(origins= "*", allowedHeaders = "*")
    @GetMapping
    public List<LivroResponseDTO> getAll(){
    List<LivroResponseDTO> livroList = repository.findAll().stream().map(LivroResponseDTO::new).toList();
    return livroList;
    }


    @CrossOrigin(origins= "*", allowedHeaders = "*")
    @PostMapping
    public void saveLivro(@RequestBody LivroRequestDTO data){
        Livro livroData = new Livro(data);
        repository.save(livroData);
        return;

    }

}
