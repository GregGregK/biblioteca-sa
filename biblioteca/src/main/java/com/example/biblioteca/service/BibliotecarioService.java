package com.example.biblioteca.service;


import com.example.biblioteca.model.Aluno;
import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.dto.Aluno.AlunoRequestDTO;
import com.example.biblioteca.model.dto.Aluno.AlunoResponseDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BibliotecarioService {


    //Aqui nessa classe service criamos todos os métodos para o Bibliotecário e posteriormente os chamamos no Controller



    //Chama o repositório
    private final BibliotecarioRepository bibliotecarioRepository;
    @Autowired
    public BibliotecarioService(BibliotecarioRepository bibliotecarioRepository) {this.bibliotecarioRepository = bibliotecarioRepository; }


    //Adiciona bibliotecário
    public BibliotecarioResponseDTO addBibliotecario(BibliotecarioRequestDTO bibliotecarioRequestDTO) {

        validateStatus(bibliotecarioRequestDTO.status());

        Bibliotecario bibliotecario = new Bibliotecario(bibliotecarioRequestDTO);
        Bibliotecario savedBibliotecario;

        try{
            savedBibliotecario = bibliotecarioRepository.save(bibliotecario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao criar bibliotecario. Verifique se todos os campos estão preenchidos");
        }

        return new BibliotecarioResponseDTO(savedBibliotecario);
    }


    //Remove bibliotecário
    public void removeBibliotecario(Long bibliotecarioID) {
        try {
            Optional<Bibliotecario> bibliotecario = bibliotecarioRepository.findById(bibliotecarioID);

            if (bibliotecario.isPresent()) {
                bibliotecarioRepository.deleteById(bibliotecarioID);
                System.out.println("Bibliotecario removido com sucesso!");
            } else {
                System.out.println("Bibliotecario não encontrado com o ID: " + bibliotecarioID);
            }

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao remover o bibliotecario: " + e.getMessage());
        }
    }

    //Atualiza o bibliotecário
    public BibliotecarioResponseDTO updateBibiliotecario(Long bibliotecarioID, BibliotecarioRequestDTO bibliotecarioRequestDTO) {
        validateStatus(bibliotecarioRequestDTO.status());

        Bibliotecario existingBibliotecario = bibliotecarioRepository.findById(bibliotecarioID)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID:" + bibliotecarioID));
        existingBibliotecario.setNome(bibliotecarioRequestDTO.nome());
        existingBibliotecario.setSetor(bibliotecarioRequestDTO.setor());
        existingBibliotecario.setStatus(bibliotecarioRequestDTO.status());

        Bibliotecario updateBibliotecario = bibliotecarioRepository.save(existingBibliotecario);
        return new BibliotecarioResponseDTO(updateBibliotecario);
    }


    //Lista o bibliotecário
    public List<BibliotecarioResponseDTO> bibliotecarioList() {
        List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findAll();
        return bibliotecarios.stream()
                .map(BibliotecarioResponseDTO::new)
                .collect(Collectors.toList());
    }


    //Lista o bibliotecário por status
    public List<BibliotecarioResponseDTO> getBibliotecarioByStatus(String status) {
        List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findByStatusIgnoreCaseContains(status);

        return bibliotecarios.stream()
                .map(BibliotecarioResponseDTO::new)
                .collect(Collectors.toList());
    }


    //Chama o bibliotecário por id
    public BibliotecarioResponseDTO getBibliotecariobyId(Long bibliotecarioId) {
        Optional<Bibliotecario> bibliotecarioOptional = bibliotecarioRepository.findById(bibliotecarioId);
        if (bibliotecarioOptional.isPresent()) {
            Bibliotecario  bibliotecario = bibliotecarioOptional.get();
            return new BibliotecarioResponseDTO(bibliotecario);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bibliotecario não encontrado com o ID: " + bibliotecarioId);
        }
    }

    //Altera apenas o status através do id
    public Bibliotecario alterarStatusPorCodigo(Long id, BibliotecarioRequestDTO bibliotecarioRequestDTO) {
        Optional<Bibliotecario> optionalBibliotecario = bibliotecarioRepository.findById(id);

        if (optionalBibliotecario.isPresent()) {
            Bibliotecario bibliotecario = optionalBibliotecario.get();

            validateStatus(bibliotecarioRequestDTO.status());

            bibliotecario.setStatus(bibliotecarioRequestDTO.status());

            bibliotecarioRepository.save(bibliotecario);

            return bibliotecario;
        } else {
            throw new IllegalArgumentException("Status inválido: Permitidos: Ativo, Demitido, Ferias ");
        }
    }

    //Método para validarmos status
    private void validateStatus(String status) {
        if (status == null || status.trim().isEmpty() || !List.of("Ativo", "Demitido", "Ferias").contains(status)) {
            throw new IllegalArgumentException("Status inválido. Permitidos: Ativo, Demitido, Ferias");
        }
    }


}
