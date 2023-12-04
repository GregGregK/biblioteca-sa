package com.example.biblioteca.service;


import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioResponseDTO;
import com.example.biblioteca.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService {


    //Aqui nessa classe service criamos todos os métodos para o Bibliotecário e posteriormente os chamamos no Controller



    //Chama o repositório
    private final BibliotecarioRepository bibliotecarioRepository;
    @Autowired
    public BibliotecarioService(BibliotecarioRepository bibliotecarioRepository) {this.bibliotecarioRepository = bibliotecarioRepository; }

    //Adiciona Bibliotecario
    public Bibliotecario addBibliotecario(BibliotecarioRequestDTO data) {
        Bibliotecario bibliotecario = new Bibliotecario();
        bibliotecario.setNome(data.getNome());
        bibliotecario.setSetor(data.getSetor());
        bibliotecarioRepository.save(bibliotecario);
        return bibliotecario;
    }


    //Remove Bibliotecario
    public void removeBibliotecario(Long bibliotecarioId) {
        bibliotecarioRepository.deleteById(bibliotecarioId);
    }

    //Atualiza Bibliotecario
    public ResponseEntity<Bibliotecario> updateBibliotecario(Long bibliotecarioId, BibliotecarioResponseDTO bibliotecarioResponseDTO) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findById(bibliotecarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bibliotecario não encontrado com o ID: " + bibliotecarioId));

        if (bibliotecarioResponseDTO.getNome() != null) {
            bibliotecario.setNome(bibliotecarioResponseDTO.getNome());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (bibliotecarioResponseDTO.getSetor() != null) {
            bibliotecario.setSetor(bibliotecarioResponseDTO.getSetor());
        }

        if (bibliotecarioResponseDTO.getStatus() != null) {
            bibliotecario.setStatus(bibliotecarioResponseDTO.getStatus());
        }
        if (bibliotecarioResponseDTO.getContato() != null) {
            bibliotecario.setContato(bibliotecarioResponseDTO.getContato());
        }

        bibliotecarioRepository.save(bibliotecario);
        return ResponseEntity.ok(bibliotecario);
    }


    //Lista bibliotecario
    public List<Bibliotecario> bibliotecarioList() {
        return bibliotecarioRepository.findAll();
    }


    // Lista por id
    public Bibliotecario getBibliotecarioById(Long bibliotecarioId) {
        Optional<Bibliotecario> bibliotecarioOptional = bibliotecarioRepository.findById(bibliotecarioId);
        if(bibliotecarioOptional.isPresent()) {
            Bibliotecario bibliotecario = bibliotecarioOptional.get();
            return bibliotecario;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bibliotecario não encontrado com o id" + bibliotecarioId);
        }
    }

    // Lista bibliotecario por status
    public List<Bibliotecario> getBiblitoecarioByStatus(String status) {
        List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findByStatusIgnoreCaseContains(status);
        return bibliotecarios;
    }


    //Método para validarmos status
    private void validateStatus(String status) {
        if (status == null || status.trim().isEmpty() || !List.of("Ativo", "Demitido", "Ferias").contains(status)) {
            throw new IllegalArgumentException("Status inválido. Permitidos: Ativo, Demitido, Ferias");
        }
    }


}
