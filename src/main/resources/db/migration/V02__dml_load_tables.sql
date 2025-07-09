-- Usar o schema correto
SET search_path TO ms_estoque;

-- Inserir estoque
INSERT INTO estoque (sku, quantidade, data_criacao, data_ultima_alteracao) VALUES
    ('blusa-verde-m', 10, CURRENT_TIMESTAMP, NULL),
    ('calca-jeans-40', 50, CURRENT_TIMESTAMP, NULL),
    ('calca-jeans-42', 1000, CURRENT_TIMESTAMP, NULL),
    ('camisa-azul-p', 100, CURRENT_TIMESTAMP, NULL),
    ('camisa-azul-pp', 1, CURRENT_TIMESTAMP, NULL);