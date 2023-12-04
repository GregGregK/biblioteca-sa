package com.example.biblioteca.controller;


import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.model.dto.Livro.LivroRequestDTO;
import com.example.biblioteca.model.dto.Livro.LivroResponseDTO;
import com.example.biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
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

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> livroList() {
        List<LivroResponseDTO> livros = livroService.livroList();
        return ResponseEntity.ok(livros);
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/buscaPorId/{id}")
    public ResponseEntity<?> getLivrobyId(@PathVariable Long id) {
        try {
            LivroResponseDTO livroResponseDTO = livroService.getLivroById(id);
            return ResponseEntity.ok(livroResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/titulo")
    public ResponseEntity<?> getLivroByTitle(@RequestParam String title) {
        try {
            LivroResponseDTO livroResponseDTO = livroService.getLivroByTitle(title);
            return ResponseEntity.ok(livroResponseDTO);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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


    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PatchMapping("/alterar-disponibilidade/{id}")
    public ResponseEntity<LivroResponseDTO> alterarDisponibilidade(@RequestBody LivroRequestDTO livroRequestDTO,
                                                                  @PathVariable("id") Long id) {
        try {
            // Utilize o método alterarStatusPorCodigo no serviço, passando o AlunoRequestDTO
            Livro livroAtualizado = livroService.alterarDisponibilidade(id, livroRequestDTO);

            if (livroAtualizado != null) {
                // Converta o Aluno para AlunoResponseDTO se necessário
                LivroResponseDTO livroResponseDTO = new LivroResponseDTO(livroAtualizado);
                return new ResponseEntity<>(livroResponseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
