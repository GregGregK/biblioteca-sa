package com.example.biblioteca.controller;


import com.example.biblioteca.model.Livro;
import com.example.biblioteca.repository.LivroRepository;
import com.example.biblioteca.model.dto.LivroRequestDTO;
import com.example.biblioteca.model.dto.LivroResponseDTO;
import com.example.biblioteca.service.LivroService;
import com.example.biblioteca.service.LivroServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("livro")
@RequiredArgsConstructor
public class LivroController {



    private final LivroService livroService;

  @PreAuthorize("hasRole('PRODUCT_SELECT')")
  @GetMapping
  public List<LivroResponseDTO> listAll(){
      return livroService.listAll();
  }

  @PreAuthorize("hasRole('PRODUCT_CREATE')")
  @PostMapping
  public Livro create(@RequestBody LivroRequestDTO livro)  {
      return livroService.create(livro);
  }


    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping
  public Livro update(Livro livro){
      return livroService.update(livro);

  }

  public void delete(@RequestParam("id") Long id){
      livroService.delete(id);
  }

}
