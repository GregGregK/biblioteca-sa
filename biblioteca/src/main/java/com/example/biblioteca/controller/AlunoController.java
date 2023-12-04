package com.example.biblioteca.controller;


import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;


    @Tag(name = "Busca alunos")
    @Operation(summary = "Retorna todos os alunos")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public ResponseEntity<?> alunoList() {
        try {
            List<Aluno> alunos = alunoService.alunoList();
            return new ResponseEntity<>(alunos, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }

    }


    @Tag(name = "Busca por status")
    @Operation(summary = "Retorna aluno pelos seus status")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/status")
    public ResponseEntity<?> getAlunosByStatus(@RequestParam String status) {
        try {
            List<Aluno> aluno = alunoService.getAlunosByStatus(status);
            return new ResponseEntity<>(aluno, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_GATEWAY);
        }
    }


    @Tag(name = "Busca por id")
    @Operation(summary = "Busca alunos pelo seu id")
    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAlunoById(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.getAlunoById(id);
            return ResponseEntity.ok(aluno);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }


    @Tag(name = "Adiciona aluno")
    @Operation(summary = "Adiciona alunos no banco de dados")
    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> addAluno(@RequestBody AlunoRequestDTO alunoRequestDTO) {
        try {
            Aluno aluno = alunoService.addAluno(alunoRequestDTO);
            return new ResponseEntity<>(aluno, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }


    @Tag(name = "Atualiza Aluno")
    @Operation(summary = "Atualiza aluno pelo seu id")
    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAluno(
            @PathVariable Long id,
            @RequestBody AlunoResponseDTO alunoResponseDTO) {
        try {
            var aluno = alunoService.updateAluno(id, alunoResponseDTO);
            return new ResponseEntity<>(aluno, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }


    @Tag(name = "Deleta aluno")
    @Operation(summary = "Deleta aluno pelo seu id")
    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long id) {
        try {
            alunoService.removeAluno(id);
            return new ResponseEntity<>("Aluno removido",  HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar dados", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/alterar-status/{id}")
    public ResponseEntity<AlunoResponseDTO> alterarStatus(@RequestBody AlunoResponseDTO alunoResponseDTO,
                                               @PathVariable("id") Long id){
        return new ResponseEntity<>(alunoResponseDTO, HttpStatus.OK);
    }
}




