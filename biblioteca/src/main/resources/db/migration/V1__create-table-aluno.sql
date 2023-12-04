CREATE TABLE aluno (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL,
                       matricula BIGINT NOT NULL,
                       created_at TIMESTAMP,
                       status VARCHAR(255),
                       contato VARCHAR(255)
);
