INSERT INTO users (username, password, perfil)
VALUES ('admin', '$2b$12$bU5lagN8vMpxiYm.ON6Oz.I90.IHzGlwTof1BWM/FN/.1i89.ooSu', 'ADMINISTRADOR')
ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, perfil)
VALUES ('funcionario', '$2b$12$bU5lagN8vMpxiYm.ON6Oz.I90.IHzGlwTof1BWM/FN/.1i89.ooSu', 'FUNCIONARIO')
ON CONFLICT (username) DO NOTHING;
