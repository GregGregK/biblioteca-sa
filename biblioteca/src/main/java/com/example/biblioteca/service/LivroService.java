package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.LivroRequestDTO;
import com.example.biblioteca.model.dto.LivroResponseDTO;
import com.example.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public LivroResponseDTO addLivro(LivroRequestDTO livroRequestDTO) {
        Livro livro = new Livro(livroRequestDTO);
        Livro savedLivro = livroRepository.save(livro);
        return new LivroResponseDTO(savedLivro);
    }

    public void removeLivro(Long livroId) {
        livroRepository.deleteById(livroId);
    }

    public LivroResponseDTO updateLivro(Long livroId, LivroRequestDTO livroRequestDTO) {
        Livro existingLivro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + livroId));

        existingLivro.setTitle(livroRequestDTO.title());
        existingLivro.setISBN(livroRequestDTO.ISBN());
        existingLivro.setDescricao(livroRequestDTO.descricao());
        existingLivro.setAutor(livroRequestDTO.autor());

        Livro updatedLivro = livroRepository.save(existingLivro);
        return new LivroResponseDTO(updatedLivro);
    }

    public List<LivroResponseDTO> livroList() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(LivroResponseDTO::new)
                .collect(Collectors.toList());
    }
}
