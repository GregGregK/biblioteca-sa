package com.example.biblioteca.controller;


import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> alunoList() {
        List<AlunoResponseDTO> alunos = alunoService.alunoList();
        return ResponseEntity.ok(alunos);
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/status")
    public ResponseEntity<?> getAlunosByStatus(@RequestParam String status) {
        try {
            List<AlunoResponseDTO> alunos = alunoService.getAlunosByStatus(status);

            if (!alunos.isEmpty()) {
                return ResponseEntity.ok(alunos);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum aluno encontrado com o status: " + status);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAlunoById(@PathVariable Long id) {
        try {
            AlunoResponseDTO alunoResponseDTO = alunoService.getAlunoById(id);
            return ResponseEntity.ok(alunoResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> addAluno(@RequestBody AlunoRequestDTO alunoRequestDTO) {
        try {
            AlunoResponseDTO alunoResponseDTO = alunoService.addAluno(alunoRequestDTO);
            return ResponseEntity.ok(alunoResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAluno(
            @PathVariable Long id,
            @RequestBody AlunoRequestDTO alunoRequestDTO) {
        try {
            AlunoResponseDTO alunoResponseDTO = alunoService.updateAluno(id, alunoRequestDTO);
            return ResponseEntity.ok(alunoResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        try {
            alunoService.removeAluno(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PatchMapping("/alterar-status/{id}")
    public ResponseEntity<AlunoResponseDTO> alterarStatus(@RequestBody AlunoRequestDTO alunoRequestDTO,
                                                          @PathVariable("id") Long id) {
        try {
            Aluno alunoAtualizado = alunoService.alterarStatusPorCodigo(id, alunoRequestDTO);

            if (alunoAtualizado != null) {
                AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO(alunoAtualizado);
                return new ResponseEntity<>(alunoResponseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}


