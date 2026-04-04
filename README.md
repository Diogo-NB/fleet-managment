# web2

Sistema de gestão de frota de veículos - controle de movimentações, funcionários e relatórios.

## Stack

- Java 21 + Spring Boot 4
- Thymeleaf (renderização server-side)
- Tailwind CSS 4
- PostgreSQL 17
- Flyway (migrations)

## Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/)
- Java 21
- Node.js + npm
- Maven Wrapper incluso (`./mvnw`)

## Configuração

### 1. Subir o banco de dados

```bash
docker compose up -d
```

### 2. Gerar o keystore SSL

Executar uma única vez na raiz do projeto:

```bash
keytool -genkeypair -alias web2 -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore src/main/resources/keystore.p12 \
  -validity 3650 -storepass changeit \
  -dname "CN=localhost, OU=Dev, O=Dev, L=Dev, S=Dev, C=BR"
```

### 3. Compilar o CSS

```bash
npm install && npm run build:css
```

### 4. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

O Flyway executa as migrations e os seeds automaticamente na inicialização.

## Acessando a aplicação

Abra: **https://localhost:8443**

O certificado é autoassinado - o browser vai exibir um aviso de segurança. Clique em "Avançado" e prossiga mesmo assim.

## Usuários de teste

| Usuário       | Senha   | Perfil                                     |
| ------------- | ------- | ------------------------------------------ |
| `admin`       | `admin` | Administrador (acesso total)               |
| `funcionario` | `admin` | Funcionário (visualização + movimentações) |

## Desenvolvimento - watch do CSS

Em um terminal separado, enquanto a aplicação estiver rodando:

```bash
npm run watch:css
```

## Variáveis de ambiente

As configurações do banco podem ser sobrescritas via variáveis de ambiente:

| Variável      | Padrão                                      |
| ------------- | ------------------------------------------- |
| `DB_URL`      | `jdbc:postgresql://localhost:5432/postgres` |
| `DB_USERNAME` | `postgres`                                  |
| `DB_PASSWORD` | `postgres`                                  |
