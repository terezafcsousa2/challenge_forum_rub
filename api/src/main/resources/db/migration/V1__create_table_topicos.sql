CREATE TABLE topicos (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         titulo VARCHAR(100) NOT NULL,
                         mensagem VARCHAR(100) NOT NULL,
                         data_criacao DATE NOT NULL,
                         estado ENUM('RESOLVIDO', 'NAO_RESOLVIDO') NOT NULL,
                         autor VARCHAR(100) NOT NULL,
                         curso VARCHAR(100) NOT NULL,

                         PRIMARY KEY (id),
                         UNIQUE (titulo, mensagem)
);
