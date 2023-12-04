package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Livro.LivroRequestDTO;
import com.example.biblioteca.model.dto.Livro.LivroResponseDTO;
import com.example.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    //Aqui criamos os métodos para o Livro e posteriormente chamamos no método


    //Chamamos o repositório
    private final LivroRepository livroRepository;
    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }




    //Adiciona livro
    public Livro addLivro(LivroRequestDTO data) {
        Livro livro = new Livro();
        livro.setTitle(data.getTitle());
        livro.setAutor(data.getAutor());
        livro.setDescricao(data.getDescricao());
        livro.setISBN(data.getISBN());
        livroRepository.save(livro);
        return livro;
    }


    //Remove livro
    public void removeLivro(Long livroId) {
        livroRepository.deleteById(livroId);
    }

    //Atualiza Livro
    public ResponseEntity<Livro> updateLivro(Long livroId, LivroResponseDTO livroResponseDTO) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o ID: " + livroId));

        if (livroResponseDTO.getAutor() != null) {
            livro.setAutor(livroResponseDTO.getAutor());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (livroResponseDTO.getDescricao() != null) {
            livro.setDescricao(livroResponseDTO.getDescricao());
        }

        if (livroResponseDTO.getISBN() != null) {
            livro.setISBN(livroResponseDTO.getISBN());
        }

        if (livroResponseDTO.getDisponibilidade() != null) {
            livro.setDisponibilidade(livroResponseDTO.getDisponibilidade());
        }

        if (livroResponseDTO.getTitle() != null) {
            livro.setTitle(livroResponseDTO.getTitle());
        }

        livroRepository.save(livro);
        return ResponseEntity.ok(livro);
    }


    //Lista livro
    public List<Livro> livroList() {
        return livroRepository.findAll();
    }


    // Lista por id
    public Livro getLivroById(Long livroId) {
        Optional<Livro> livroOptional = livroRepository.findById(livroId);
        if(livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            return livro;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o id" + livroId);
        }
    }

    // Lista livro por titulo
    public Optional<Livro> getLivroByTitle(String title) {
        Optional<Livro> livro = livroRepository.findByTitleIgnoreCaseContains(title);
        return livro;
    }


    //Altera disponibilidade por id
//    public Livro alterarDisponibilidade(Long id, LivroRequestDTO livroRequestDTO) {
//        Optional<Livro> optionalLivro = livroRepository.findById(id);
//
//        if (optionalLivro.isPresent()) {
//            Livro livro = optionalLivro.get();
//
//            livro.setDisponibilidade(livroRequestDTO.disponibilidade());
//
//            livroRepository.save(livro);
//
//            return livro;
//        } else {
//            throw new IllegalArgumentException("Erro");
//        }
//    }






















}
