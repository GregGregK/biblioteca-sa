package com.example.biblioteca.repository;

import com.example.biblioteca.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByStatusIgnoreCaseContains(String status);
}
