CREATE TABLE livro (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       ISBN BIGINT,
                       descricao TEXT,
                       autor VARCHAR(255),
                       disponibilidade VARCHAR(50)
);
