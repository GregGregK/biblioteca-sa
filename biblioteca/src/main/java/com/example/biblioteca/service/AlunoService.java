package com.example.biblioteca.service;

import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    public AlunoResponseDTO addAluno(AlunoRequestDTO alunoRequestDTO) {

        validateStatus(alunoRequestDTO.status());

        Aluno aluno = new Aluno(alunoRequestDTO);
        Aluno savedAluno;
        try {
            savedAluno = alunoRepository.save(aluno);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao criar aluno. Verifique se todos os campos estão preenchidos corretamente.");
        }
        return new AlunoResponseDTO(savedAluno);
    }

    public void removeAluno(Long alunoID) {
        Optional<Aluno> aluno = alunoRepository.findById(alunoID);
        aluno.ifPresentOrElse(
                a -> {
                    alunoRepository.deleteById(alunoID);
                    logger.log(Level.INFO, "Aluno deletado: {0}", a.getNome());
                },
                () -> logger.log(Level.WARNING, "Tentativa de deletar aluno inexistente com ID: {0}", alunoID)
        );
    }

    public AlunoResponseDTO updateAluno(Long alunoId, AlunoRequestDTO alunoRequestDTO) {
        validateStatus(alunoRequestDTO.status());

        Aluno existingAluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " + alunoId));

        existingAluno.setNome(alunoRequestDTO.nome());
        existingAluno.setMatricula(alunoRequestDTO.matricula());
        existingAluno.setStatus(alunoRequestDTO.status());

        Aluno updateAluno = alunoRepository.save(existingAluno);
        return new AlunoResponseDTO(updateAluno);
    }

    public List<AlunoResponseDTO> alunoList() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public AlunoResponseDTO getAlunoById(Long alunoId) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            return new AlunoResponseDTO(aluno);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + alunoId);
        }
    }

    public List<AlunoResponseDTO> getAlunosByStatus(String status) {
        List<Aluno> alunos = alunoRepository.findByStatusIgnoreCaseContains(status);

        return alunos.stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Aluno alterarStatusPorCodigo(Long id, AlunoRequestDTO alunoRequestDTO) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(id);

        if (optionalAluno.isPresent()) {
            Aluno aluno = optionalAluno.get();

            validateStatus(alunoRequestDTO.status());

            aluno.setStatus(alunoRequestDTO.status());

            alunoRepository.save(aluno);

            return aluno;
        } else {
            return null;
        }
    }


    private void validateStatus(String status) {
        if (status == null || status.trim().isEmpty() || !List.of("Ativo", "Inativo", "Invalido", "Inadimplente").contains(status)) {
            throw new IllegalArgumentException("Status inválido. Permitidos: Ativo, Inativo, Invalido, Inadimplente");
        }
    }
}
