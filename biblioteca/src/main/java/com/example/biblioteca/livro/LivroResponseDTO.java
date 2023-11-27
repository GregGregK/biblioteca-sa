package com.example.biblioteca.livro;


//    private Long id;
//    private String title;
//    private Long ISBN;
//    private String descricao;
//    private String autor;
public record LivroResponseDTO(Long id, String title, Long ISBN, String descricao, String autor) {

    public LivroResponseDTO(Livro livro){
        this(livro.getId(), livro.getTitle(), livro.getISBN(), livro.getDescricao(), livro.getAutor());

    }
}
