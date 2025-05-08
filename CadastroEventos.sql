CREATE DATABASE IF NOT EXISTS sistema_ingressos;
USE sistema_ingressos;

-- Criação da tabela de ingressos
CREATE TABLE IF NOT EXISTS ingresso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento VARCHAR(100) NOT NULL,
    data_evento DATE NOT NULL,
    valor_base DOUBLE NOT NULL,
    tipo ENUM('NORMAL', 'VIP', 'MEIA') NOT NULL,
    detalhe_evento VARCHAR(100) NOT NULL
);

select * from ingresso;