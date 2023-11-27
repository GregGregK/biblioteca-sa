package com.example.biblioteca.livro;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "livros")
@Entity(name = "livros")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long ISBN;
    private String descricao;
    private String autor;

    public Livro(LivroRequestDTO data){
        this.title = data.title();
        this.ISBN = data.ISBN();
        this.descricao = data.descricao();
        this.autor = data.autor();
    }

}
