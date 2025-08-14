CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);
