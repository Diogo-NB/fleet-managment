INSERT INTO veiculo (modelo, placa, ano, km, status) VALUES
    ('Honda Civic', 'ABC1234', 2022, 15000, 'DISPONIVEL'),
    ('Toyota Corolla', 'XYZ5678', 2021, 32000, 'DISPONIVEL'),
    ('Ford Ka', 'DEF9012', 2020, 48000, 'CONSERTO'),
    ('Chevrolet Onix', 'GHI3456', 2023, 5000, 'DISPONIVEL')
ON CONFLICT (placa) DO NOTHING;

INSERT INTO funcionario (nome, contato) VALUES
    ('Carlos Silva', '(11) 99999-0001'),
    ('Ana Souza', '(11) 99999-0002'),
    ('Pedro Lima', '(11) 99999-0003');
