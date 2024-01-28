CREATE TABLE tb_users
(
    id              VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    document        VARCHAR(15) NOT NULL,
    mail            VARCHAR(255) NOT NULL,
    phone           VARCHAR(30) NOT NULL,
    birthday        DATE NOT NULL,
    gender          VARCHAR(50) NOT NULL,
    photo           VARCHAR(255),
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at      TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY     (id)
);