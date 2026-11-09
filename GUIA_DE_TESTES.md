# Guia de Testes Manuais - TicketHub

## 1. Objetivo

Este guia reúne cenários de testes manuais para validar as principais funcionalidades do TicketHub, incluindo a compra e o gerenciamento de ingressos para eventos.

## 2. Cenários de teste

### Teste 1 - Compra de ingressos
- Acessar a funcionalidade de compra de ingressos.
- Selecionar um evento disponível.
- Informar uma quantidade válida de ingressos.
- Verificar se o sistema permite prosseguir com a compra.

### Teste 2 - Quantidade inválida de ingressos
- Tentar realizar uma compra com quantidade zero.
- Repetir o teste com quantidade negativa.
- Resultado esperado: o sistema deve rejeitar quantidades inválidas e não alterar indevidamente o estoque.

### Teste 3 - Comprovante de compra
- Realizar uma compra de teste.
- Acessar a página Meus Ingressos.
- Verificar se as informações da compra são apresentadas corretamente.
- Conferir se a forma de pagamento é exibida.

### Teste 4 - Exclusão de eventos
- Acessar a área administrativa de eventos.
- Selecionar a opção de excluir um evento.
- Verificar se o sistema solicita confirmação.
- Cancelar a confirmação e verificar se a exclusão não ocorre.

### Teste 5 - Validação do email
- Tentar realizar uma compra informando um email inválido.
- Resultado esperado: o sistema deve rejeitar o endereço inválido.

### Teste 6 - Busca de eventos
- Buscar um evento existente pelo título e depois pelo local.
- Buscar um termo que não corresponda a nenhum evento.
- Resultado esperado: a busca retorna os eventos correspondentes e, quando não houver resultado, exibe a mensagem "Nenhum evento encontrado".
### Teste 7 - Eventos encerrados
- Acessar a tela inicial e verificar que eventos com data anterior à atual não são exibidos.
- Buscar pelo nome de um evento encerrado.
- Tentar acessar diretamente a URL de pagamento de um evento encerrado e finalizar a compra.
- Resultado esperado: o evento não aparece na listagem nem na busca, e a compra é recusada com a mensagem "Este evento já aconteceu e não está mais à venda."

## 3. Registro dos resultados

Para cada teste executado, registrar:
- Data da execução.
- Responsável pelo teste.
- Resultado obtido.
- Situação: aprovado ou reprovado.
- Número da issue relacionada, quando aplicável.

## 4. Observações

Os cenários descritos neste documento são orientações para execução futura. A inclusão de um cenário não significa que o teste já foi executado ou aprovado.
