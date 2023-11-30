package com.example.biblioteca.controller;


import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.LivroRequestDTO;
import com.example.biblioteca.model.dto.LivroResponseDTO;
import com.example.biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> livroList() {
        List<LivroResponseDTO> livros = livroService.livroList();
        return ResponseEntity.ok(livros);
    }

    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> addLivro(@RequestBody LivroRequestDTO livroRequestDTO) {
        LivroResponseDTO livroResponseDTO = livroService.addLivro(livroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroResponseDTO);
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> updateLivro(
            @PathVariable Long id,
            @RequestBody LivroRequestDTO livroRequestDTO) {
        LivroResponseDTO livroResponseDTO = livroService.updateLivro(id, livroRequestDTO);
        return ResponseEntity.ok(livroResponseDTO);
    }

    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroService.removeLivro(id);
        return ResponseEntity.noContent().build();
    }
    //teste
}
