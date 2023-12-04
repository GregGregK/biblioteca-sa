CREATE TABLE bibliotecario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    setor VARCHAR(100),
    contato VARCHAR(100),
    created_at TIMESTAMP
);