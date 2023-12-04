package com.example.biblioteca.service;

import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final Logger logger = Logger.getLogger(AlunoService.class.getName());
    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    //Adiciona Aluno
    public Aluno addAluno(AlunoRequestDTO data) {
        Aluno aluno = new Aluno();
        aluno.setNome(data.getNome());
        aluno.setMatricula(data.getMatricula());
        aluno.setContato(data.getContato());
        alunoRepository.save(aluno);
        return aluno;
    }

    //Deleta Aluno
    public void removeAluno(Long alunoID) {
        alunoRepository.deleteById(alunoID);
    }

    //Atualiza Aluno
    public ResponseEntity<Aluno> updateAluno(Long alunoId, AlunoResponseDTO alunoResponseDTO) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + alunoId));

        if (alunoResponseDTO.getNome() != null) {
            alunoResponseDTO.setNome(alunoResponseDTO.getNome());
        }

        if (alunoResponseDTO.getMatricula() != null) {
            aluno.setMatricula(alunoResponseDTO.getMatricula());
        }
        if (alunoResponseDTO.getContato() != null) {
            aluno.setContato(alunoResponseDTO.getContato());
        }


        alunoRepository.save(aluno);
        return ResponseEntity.ok(aluno);
    }


    //Lista Aluno
    public List<Aluno> alunoList() {
        return alunoRepository.findAll();
    }

    public Aluno getAlunoById(Long alunoId) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            return aluno;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o id" + alunoId);
        }
    }

    public List<Aluno> getAlunosByStatus(String status) {
        List<Aluno> alunos = alunoRepository.findByStatusIgnoreCaseContains(status);
        return alunos;
    }


    private void validateStatus(String status) {
        if (status == null || status.trim().isEmpty() || !List.of("Ativo", "Inativo", "Invalido", "Inadimplente").contains(status)) {
            throw new IllegalArgumentException("Status inválido. Permitidos: Ativo, Inativo, Invalido, Inadimplente");
        }
    }
}
