package com.example.biblioteca.repository;

import com.example.biblioteca.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    List<Bibliotecario> findByStatusIgnoreCaseContains(String status);

}
