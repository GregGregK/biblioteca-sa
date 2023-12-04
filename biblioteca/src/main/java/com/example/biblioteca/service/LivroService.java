package com.example.biblioteca.service;

import com.example.biblioteca.model.Bibliotecario;
import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.Bibliotecario.BibliotecarioRequestDTO;
import com.example.biblioteca.model.dto.Livro.LivroRequestDTO;
import com.example.biblioteca.model.dto.Livro.LivroResponseDTO;
import com.example.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public LivroResponseDTO addLivro(LivroRequestDTO livroRequestDTO) {
        try {
            Livro livro = new Livro(livroRequestDTO);
            Livro savedLivro = livroRepository.save(livro);
            return new LivroResponseDTO(savedLivro);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao criar livro. Verifique se todos os campos estão preenchidos", e);
        }
    }


    //Remove livro
    public void removeLivro(Long livroId) {
        try {
            livroRepository.deleteById(livroId);
            System.out.println("Livro removido com sucesso!");
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Livro não encontrado com o ID: " + livroId);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Ocorreu um erro ao remover o livro: " + e.getMessage());
        }
    }

    //Atualiza Livro
    public LivroResponseDTO updateLivro(Long livroId, LivroRequestDTO livroRequestDTO) {
        Livro existingLivro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + livroId));

        existingLivro.setTitle(livroRequestDTO.title());
        existingLivro.setISBN(livroRequestDTO.ISBN());
        existingLivro.setDescricao(livroRequestDTO.descricao());
        existingLivro.setAutor(livroRequestDTO.autor());
        existingLivro.setDisponibilidade(livroRequestDTO.disponibilidade());

        Livro updatedLivro = livroRepository.save(existingLivro);
        return new LivroResponseDTO(updatedLivro);
    }


    //Lista livro
    public List<LivroResponseDTO> livroList() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(LivroResponseDTO::new)
                .collect(Collectors.toList());
    }


    // Lista por id
    public LivroResponseDTO getLivroById(Long livroId) {
        Optional<Livro> livroOptional = livroRepository.findById(livroId);
        if(livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            return new LivroResponseDTO(livro);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o id" + livroId);
        }
    }

    // Lista livro por titulo
    public LivroResponseDTO getLivroByTitle(String title) {
        Optional<Livro> livroOptional = livroRepository.findByTitleIgnoreCaseContains(title);

        return livroOptional
                .map(livro -> new LivroResponseDTO(livro))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com o Título:" + title));
    }


    //Altera disponibilidade por id
    public Livro alterarDisponibilidade(Long id, LivroRequestDTO livroRequestDTO) {
        Optional<Livro> optionalLivro = livroRepository.findById(id);

        if (optionalLivro.isPresent()) {
            Livro livro = optionalLivro.get();

            livro.setDisponibilidade(livroRequestDTO.disponibilidade());

            livroRepository.save(livro);

            return livro;
        } else {
            throw new IllegalArgumentException("Erro");
        }
    }






















}
