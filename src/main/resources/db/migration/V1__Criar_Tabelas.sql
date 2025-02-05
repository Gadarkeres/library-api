CREATE TABLE USUARIOS (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    data_cadastro DATE NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE LIVROS (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

CREATE TABLE EMPRESTIMOS (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES USUARIOS(id),
    livro_id INT NOT NULL REFERENCES LIVROS(id),
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    status_emprestimo VARCHAR(20)
);
