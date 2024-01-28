CREATE TABLE tb_address
(
    id              VARCHAR(255) NOT NULL,
    street          VARCHAR(255) NOT NULL,
    street_number   VARCHAR(30) NOT NULL,
    neighborhood    VARCHAR(255) NOT NULL,
    complement      VARCHAR(255) NOT NULL,
    city            VARCHAR(255) NOT NULL,
    state           VARCHAR(2) NOT NULL,
    postal_code     VARCHAR(10) NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at      TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY     (id)
);