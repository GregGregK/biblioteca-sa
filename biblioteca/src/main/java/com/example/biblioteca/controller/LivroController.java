package com.example.biblioteca.controller;


import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.model.dto.Livro.LivroRequestDTO;
import com.example.biblioteca.model.dto.Livro.LivroResponseDTO;
import com.example.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.*;


@RestController
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @Tag(name = "Listar livros")
    @Operation(summary = "Lista todos os livros disponiveis no BD")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<?> livroList() {
    try {
        List<Livro> livros = livroService.livroList();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    } catch (Exception ex){
        return new ResponseEntity<>("Erro ao processar os dados", HttpStatus.BAD_GATEWAY);
    }
    }


    @Tag(name = "Busca por id")
    @Operation (summary = "Lista o livro pelo seu id primário")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/buscaPorId/{id}")
    public ResponseEntity<?> getLivrobyId(@PathVariable Long id) {
        Livro livro = livroService.getLivroById(id);
        return ResponseEntity.ok(livro);
    }

    @Tag(name = "Busca por titulo")
    @Operation(summary = "Retorna os livros pelo seu titulo")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/titulo")
    public ResponseEntity<?> getLivroByTitle(@RequestParam String title) {
        try {
            Optional<Livro> livro = livroService.getLivroByTitle(title);
            return new ResponseEntity<>(livro, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }
    }

    @Tag(name = "Adicionar livros")
    @Operation(summary = "Adiciona livros ao banco de dados")
    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> addLivro(@RequestBody LivroRequestDTO livroRequestDTO) {
        try {
            Livro livro = livroService.addLivro(livroRequestDTO);
            return new ResponseEntity<>(livro, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }
    }

    @Tag(name = "Atualiza livros")
    @Operation(summary = "Atualiza livros existentes")
    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivro(
            @PathVariable Long id,
            @RequestBody LivroResponseDTO livroResponseDTO) {
        try {
            var livro = livroService.updateLivro(id, livroResponseDTO);
            return new ResponseEntity<>(livro, HttpStatus.OK);
        } catch (Exception ex) {
            return  new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }

    }


    @Tag(name = "Deletar Livros")
    @Operation(summary = "Deleta livros existentes pelo seu id")
    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroService.removeLivro(id);
        return ResponseEntity.noContent().build();
    }




}
