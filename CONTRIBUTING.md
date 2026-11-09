# Guia de Contribuição — TicketHub

Este documento define como a equipe contribui com o projeto: modelo de ramificação, padrão de commits e fluxo de Pull Requests. Todas as contribuições devem seguir estas regras.

---

## 1. Modelo de ramificação (GitHub Flow)

### Branch principal: `main`

- É o ramo estável do projeto.
- É **protegida**: não é permitido commit ou push direto.
- Todo código que chega nela deve estar funcional e aprovado via Pull Request.

### Branches de trabalho

Toda alteração é feita em uma branch isolada, criada a partir da `main`.

| Tipo | Uso | Formato do nome |
|---|---|---|
| `feature/` | Nova funcionalidade | `feature/issue-<ID>-<descricao-curta>` |
| `fix/` | Correção de defeito | `fix/issue-<ID>-<descricao-curta>` |
| `docs/` | Somente documentação *(exceção)* | `docs/<descricao-curta>` |
| `chore/` | Somente configuração do repositório *(exceção)* | `chore/<descricao-curta>` |

Exemplos:

```
feature/issue-12-rate-limit-login
fix/issue-8-erro-excluir-evento
docs/guia-contribuicao
```

> **Exceção:** branches `docs/` e `chore/` dispensam issue vinculada, pois não alteram o código da aplicação. Elas continuam sujeitas às demais regras (criadas a partir da `main` e integradas via Pull Request com aprovação).

### Criando uma branch

```bash
git checkout main
git pull
git checkout -b feature/issue-12-rate-limit-login
```

---

## 2. Commits semânticos (Conventional Commits v1.0.0)

### Formato

```
<tipo>[(escopo opcional)]: <descrição>

[corpo opcional]

[rodapé opcional]
```

### Regras de escrita

- Descrição com **até 50 caracteres**, em **minúsculas** e **sem ponto final**.
- Verbo no **presente do indicativo**: "adiciona", "corrige", "remove".
- Deixe **uma linha em branco** entre a descrição e o corpo.
- O corpo explica **o quê** e **por quê**, não o como; quebre as linhas em cerca de 72 caracteres.
- Use o rodapé para referenciar issues (`Refs: #12`) ou indicar quebra de compatibilidade (`BREAKING CHANGE: ...`).
- Cada commit deve conter **uma única mudança lógica**.

### Tipos permitidos

| Tipo | Quando usar | Exemplo |
|---|---|---|
| `feat` | Nova funcionalidade | `feat(auth): adiciona rate limit na tela de login` |
| `fix` | Correção de defeito | `fix(evento): corrige erro 500 ao excluir evento` |
| `docs` | Somente documentação | `docs(readme): corrige instrução de inicialização` |
| `style` | Formatação, sem mudar lógica | `style(css): remove seletores obsoletos` |
| `refactor` | Melhora a estrutura, sem mudar comportamento | `refactor(pedido): extrai validação para método` |
| `test` | Adição ou correção de testes | `test(service): adiciona testes do PedidoService` |
| `chore` | Build, configuração ou dependências | `chore(deps): atualiza versão do Spring Boot` |

### Exemplo completo

```
fix(session): renova ID de sessão após autenticação

Evita ataques de fixação de sessão, gerando um novo ID
sempre que o usuário faz login com sucesso.

Refs: #15
```

---

## 3. Pull Requests

1. Envie a branch para o repositório remoto:
   ```bash
   git push -u origin <nome-da-branch>
   ```
2. Abra um Pull Request com destino à `main`.
3. Na descrição, explique as alterações e vincule as issues resolvidas com `Closes #X` ou `Fixes #Y`.
4. Aguarde a **aprovação de pelo menos um revisor** da equipe.
5. Faça o merge com **"Create a merge commit"**, para preservar o histórico das ramificações.

### Conflitos de merge

Ao resolver um conflito, registre um comentário no Pull Request informando:

- quais arquivos tiveram conflito;
- o que cada lado havia alterado;
- qual versão foi mantida e por quê.
