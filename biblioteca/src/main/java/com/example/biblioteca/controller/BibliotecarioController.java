package com.example.biblioteca.controller;


import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.service.BibliotecarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("bibliotecario")
@RequiredArgsConstructor
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;


    @Tag(name = "Busca Bibliotecarios")
    @Operation(summary = "Retorna todos os bibliotecarios")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<?> bibliotecarioList() {
        try {
            List<Bibliotecario> bibliotecarios = bibliotecarioService.bibliotecarioList();
            return new ResponseEntity<>( bibliotecarios, HttpStatus.OK );
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }
    }

    @Tag(name = "Busca por status")
    @Operation(summary = "Retorna bibliotecários pelos seus status")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/{status}")
    public ResponseEntity<?> getBibliotecariosByStatus(@RequestParam String status) {
        try {
            List<Bibliotecario> bibliotecario = bibliotecarioService.getBiblitoecarioByStatus(status);
            return new ResponseEntity<>(bibliotecario, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }
    }


    @Tag(name = "Adiciona bibliotecários")
    @Operation(summary = "Adiciona bibliotecarios ao banco de dados")
    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> addBibliotecario(@RequestBody BibliotecarioRequestDTO bibliotecarioRequestDTO){
        try {
            Bibliotecario bibliotecario = bibliotecarioService.addBibliotecario(bibliotecarioRequestDTO);
            return new ResponseEntity<>(bibliotecario, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name = "Atualiza bibliotecários")
    @Operation(summary = "Atualiza bibliotecários existentes")
    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBibliotecario(
            @PathVariable Long id,
            @RequestBody BibliotecarioResponseDTO bibliotecarioResponseDTO ) {
       try {
           var bibliotecario = bibliotecarioService.updateBibliotecario(id, bibliotecarioResponseDTO);
           return new ResponseEntity<>(bibliotecario, HttpStatus.OK);
       } catch (Exception ex) {
           return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
       }
    }

    @Tag(name = "Deletar bibliotecário")
    @Operation(summary = "Delete bibliotecários existentes")
    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBibliotecario(@PathVariable Long id) {
        try {
            bibliotecarioService.removeBibliotecario(id);
            return new ResponseEntity<>("Bibliotecário removido",  HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }

@Tag(name = "Busca bibliotecário por id")
@Operation(summary = "Busca bibliotecarios pelo seus ids")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBibliotecarioById(@PathVariable Long id) {
        try {
            Bibliotecario bibliotecario = bibliotecarioService.getBibliotecarioById(id);
            return ResponseEntity.ok(bibliotecario);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }







}
