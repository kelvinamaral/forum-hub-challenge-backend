CREATE TABLE respostas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem VARCHAR(500) NOT NULL,
    topico_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    autor_id BIGINT NOT NULL,
    solucao BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
