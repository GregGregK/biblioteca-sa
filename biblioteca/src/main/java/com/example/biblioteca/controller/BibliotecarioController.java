package com.example.biblioteca.controller;


import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.service.BibliotecarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<List<BibliotecarioResponseDTO>> bibliotecarioList() {
        List<BibliotecarioResponseDTO> bibliotecarios = bibliotecarioService.bibliotecarioList();
        return ResponseEntity.ok(bibliotecarios);
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/{status}")
    public ResponseEntity<?> getBibliotecariosByStatus(@RequestParam String status) {
        try {
            List<BibliotecarioResponseDTO> bibliotecarios = bibliotecarioService.getBibliotecarioByStatus(status);

            if (!bibliotecarios.isEmpty()) {
                return ResponseEntity.ok(bibliotecarios);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> addBibliotecario(@RequestBody BibliotecarioRequestDTO bibliotecarioRequestDTO){
        try {
            BibliotecarioResponseDTO bibliotecarioResponseDTO = bibliotecarioService.addBibliotecario(bibliotecarioRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bibliotecarioRequestDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBibliotecario(
            @PathVariable Long id,
            @RequestBody BibliotecarioRequestDTO bibliotecarioRequestDTO ) {
        try {
            BibliotecarioResponseDTO bibliotecarioResponseDTO = bibliotecarioService.updateBibiliotecario(id, bibliotecarioRequestDTO);
            return ResponseEntity.ok(bibliotecarioResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBibliotecario(@PathVariable Long id) {
        try {
            bibliotecarioService.removeBibliotecario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBibliotecarioById(@PathVariable Long id) {
        try {
            BibliotecarioResponseDTO bibliotecarioResponseDTO = bibliotecarioService.getBibliotecariobyId(id);
            return ResponseEntity.ok(bibliotecarioResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PatchMapping("/alterar-status/{id}")
    public ResponseEntity<BibliotecarioResponseDTO> alterarStatus(@RequestBody BibliotecarioRequestDTO bibliotecarioRequestDTO,
                                                          @PathVariable("id") Long id) {
        try {
            // Utilize o método alterarStatusPorCodigo no serviço, passando o AlunoRequestDTO
            Bibliotecario bibliotecarioAtualizado = bibliotecarioService.alterarStatusPorCodigo(id, bibliotecarioRequestDTO);

            if (bibliotecarioAtualizado != null) {
                // Converta o Aluno para AlunoResponseDTO se necessário
                BibliotecarioResponseDTO bibliotecarioResponseDTO = new BibliotecarioResponseDTO(bibliotecarioAtualizado);
                return new ResponseEntity<>(bibliotecarioResponseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }






}
