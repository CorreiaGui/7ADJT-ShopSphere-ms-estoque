-- Criação do schema
CREATE SCHEMA IF NOT EXISTS ms_estoque;

-- Garante que tudo será criado dentro do schema ms-estoque
SET search_path TO ms_estoque;

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de estoque
CREATE TABLE estoque (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sku VARCHAR(255) NOT NULL,
    quantidade BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP
);