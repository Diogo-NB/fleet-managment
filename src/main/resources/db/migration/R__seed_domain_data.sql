INSERT INTO veiculo (modelo, placa, ano, km, status) VALUES
    ('Honda Civic',    'ABC1234', 2022, 15000, 'DISPONIVEL'),
    ('Toyota Corolla', 'XYZ5678', 2021, 32000, 'DISPONIVEL'),
    ('Ford Ka',        'DEF9012', 2020, 48000, 'CONSERTO'),
    ('Chevrolet Onix', 'GHI3456', 2023,  5000, 'DISPONIVEL')
ON CONFLICT (placa) DO NOTHING;

INSERT INTO funcionario (nome, contato)
SELECT 'Carlos Silva', '(11) 99999-0001' WHERE NOT EXISTS (SELECT 1 FROM funcionario WHERE nome = 'Carlos Silva');
INSERT INTO funcionario (nome, contato)
SELECT 'Ana Souza', '(11) 99999-0002' WHERE NOT EXISTS (SELECT 1 FROM funcionario WHERE nome = 'Ana Souza');
INSERT INTO funcionario (nome, contato)
SELECT 'Pedro Lima', '(11) 99999-0003' WHERE NOT EXISTS (SELECT 1 FROM funcionario WHERE nome = 'Pedro Lima');
