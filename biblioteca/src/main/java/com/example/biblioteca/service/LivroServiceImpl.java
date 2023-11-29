package com.example.biblioteca.service;

import com.example.biblioteca.model.Livro;
import com.example.biblioteca.model.dto.LivroRequestDTO;
import com.example.biblioteca.model.dto.LivroResponseDTO;
import com.example.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    @Override
    public List<LivroResponseDTO> listAll() {
        List<LivroResponseDTO> livroList = livroRepository.findAll().stream().map(LivroResponseDTO::new).toList();
        return livroList;
    }

    @Override
    public Livro create(LivroRequestDTO data) {
        Livro livroData = new Livro(data);
        return livroRepository.save(livroData);

    }

    @Override
    public Livro update(Livro livro) {
        if(livro.getId() == null ){
            throw new RuntimeException("Para atuallizar é preciso informar o id");
        }
        return livroRepository.save(livro);
    }

    @Override
    public void delete(Long id) {
        livroRepository.deleteById(id);
    }
}
