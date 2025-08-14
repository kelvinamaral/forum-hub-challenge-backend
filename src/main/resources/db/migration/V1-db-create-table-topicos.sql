CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensagem VARCHAR(500) NOT NULL UNIQUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(100) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);
