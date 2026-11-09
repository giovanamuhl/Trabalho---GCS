# TicketHub

O TicketHub é um sistema web para compra e gerenciamento de ingressos de eventos, desenvolvido com Spring Boot e Thymeleaf.

---

## Tecnologias

- **Java 17**
- **Spring Boot 3.5**
- **Spring Data JPA** — persistência
- **Thymeleaf** — renderização de páginas no servidor (SSR)
- **H2** — banco de dados em memória 
- **Lombok** — reduz código repetitivo (getters, setters, construtores)
- **Bootstrap 5** — estilização das páginas

---

## Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)** com renderização no servidor:

```
Navegador → Controller → Repository → Banco (H2)
                ↓
           Template (Thymeleaf) → Navegador
```

> O `PedidoController` usa um `PedidoService` para isolar a regra de negócio da compra.

### Estrutura de pastas

```
ticket-hub/
└── usuario/                         ← projeto Spring Boot
    └── src/main/
        ├── java/com/example/ticket_hub/
        │   ├── TicketHubApplication.java   ← ponto de entrada
        │   ├── DataLoader.java             ← popula o banco ao iniciar
        │   ├── config/
        │   │   ├── LoginInterceptor.java   ← bloqueia acesso sem login às rotas de compra
        │   │   └── WebConfig.java          ← registra o interceptor nas rotas protegidas
        │   ├── controller/
        │   │   ├── EventoController.java   ← rotas da tela inicial e busca
        │   │   ├── PedidoController.java   ← rotas de pagamento, confirmação e meus ingressos
        │   │   ├── AuthController.java     ← rotas de login e logout
        │   │   └── AdminController.java    ← rotas do painel administrativo de eventos
        │   ├── service/
        │   │   └── PedidoService.java      ← regra de negócio da compra
        │   ├── repository/
        │   │   ├── EventoRepository.java
        │   │   ├── PedidoRepository.java
        │   │   └── UsuarioRepository.java
        │   ├── model/
        │   │   ├── Evento.java
        │   │   ├── Pedido.java
        │   │   └── Usuario.java
        │   └── enums/
        │       └── FormaPagamento.java     ← PIX, CARTAO_CREDITO, CARTAO_DEBITO, BOLETO
        └── resources/
            ├── application.properties
            ├── templates/                  ← páginas HTML (Thymeleaf)
            │   ├── index.html              ← listagem de eventos
            │   ├── login.html
            │   ├── pagamento.html          ← checkout
            │   ├── meus-ingressos.html
            │   ├── confirmacao.html
            │   └── admin-eventos.html      ← painel administrativo (criar/editar/excluir eventos)
            └── static/
                ├── css/style.css
                ├── css/admin.css
                ├── js/script.js
                └── images/frame.png
```

### Camadas

| Camada | Responsabilidade |
|---|---|
| **Controller** | Recebe a requisição HTTP, chama o repository (ou service) e devolve o nome do template |
| **Service** | Contém regras de negócio — usado apenas em `PedidoService` onde a lógica é mais complexa |
| **Repository** | Acessa o banco de dados via Spring Data JPA |
| **Model** | Representa as tabelas do banco (entidades JPA) |
| **Enums** | Valores fixos do sistema (formas de pagamento) |
| **Templates** | Páginas HTML com tags `th:` que recebem dados do controller |

---

## Banco de dados

### Tabelas

```
EVENTO          PEDIDO          USUARIO
----------      -----------     ----------
id              id              id
titulo          evento_id (FK)  usuario
descricao       nome_comprador  senha
data            email_comprador
local           quantidade
preco           forma_pagamento
capacidade      valor_total
ingressos_disp  data_criacao
imagem
```

---

## Rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/` | Tela inicial com todos os eventos |
| GET | `/buscar?termo=` | Busca eventos por título ou local |
| GET | `/login` | Tela de login |
| POST | `/login` | Processa o login |
| GET | `/logout` | Encerra a sessão |
| GET | `/pagamento/{eventoId}` | Tela de checkout do evento |
| POST | `/pedidos` | Processa a compra |
| GET | `/confirmacao/{pedidoId}` | Tela de confirmação pós-compra |
| GET | `/meus-ingressos` | Lista os pedidos do usuário logado |
| GET | `/admin/eventos` | Painel administrativo — lista eventos e formulário para criar |
| GET | `/admin/eventos/{id}/editar` | Formulário pré-preenchido para editar um evento |
| POST | `/admin/eventos/salvar` | Cria ou atualiza um evento |
| POST | `/admin/eventos/{id}/excluir` | Remove um evento |

> As rotas `/admin/eventos/**` exigem uma sessão logada com o usuário `admin` — sem isso, o usuário é redirecionado para `/login`.
>
> As rotas `/pagamento/{eventoId}`, `/pedidos`, `/confirmacao/{pedidoId}` e `/meus-ingressos` exigem login. O bloqueio é feito pelo `LoginInterceptor` (pasta `config/`), que redireciona para `/login` quando não há sessão. Os pedidos são buscados pelo usuário da sessão, então cada usuário vê apenas os próprios.

---

## Como executar

### Pré-requisitos

- Java 17 instalado
- Maven Wrapper incluído no projeto (não precisa instalar Maven)

### Passos

```bash
# 1. Entrar na pasta do projeto
cd usuario

# 2. Rodar a aplicação
./mvnw spring-boot:run
```

Aguarde a inicialização da aplicação e a mensagem: 
```
Started TicketHubApplication in X seconds
```

### Acessar no navegador

| Recurso | URL |
|---|---|
| Site | `http://localhost:8080` |
| Banco de dados (H2 Console) | `http://localhost:8080/h2-console` |

### Login de teste

| Perfil | Usuário | Senha |
|---|---|---|
| Administrador | `admin` | Definida em `app.admin.senha` no arquivo `application.properties` |
| Cliente | `cliente` | `1234` |

> Ao logar com o usuário `admin`, o sistema redireciona automaticamente para o painel administrativo (`/admin/eventos`). Qualquer outro usuário é redirecionado para a tela inicial (`/`).

### Conexão H2 Console
| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:tickethub` |
| User Name | `SA` |
| Password | *(vazio)* |

> **Atenção:** o banco H2 é em memória — os dados são apagados toda vez que a aplicação para. Isso é esperado em ambiente de desenvolvimento.

---

## Observações
- O pagamento é **simulado** — não há integração com gateway real
- A senha do usuário é armazenada em texto simples (adequado para fins acadêmicos)
- Os dados de teste são inseridos automaticamente pelo `DataLoader` a cada inicialização
