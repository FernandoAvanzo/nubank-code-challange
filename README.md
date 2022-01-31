# Nubank Code Challange
Aplicação para calculo de ganho dcapital em compra de e venda de ações, 
as regras de negocio estão definidas no documento com a descrição do problema.

## Detalhes da Implementação
Os detalhes da implementação especificações e informaçẽes adicionais ṕodem ser encontrados na pagina:
- [Technical Challenge Nubank](https://www.notion.so/fernando-avanzo/Technical-Challenge-Nubank-7e545bfcf7a94da9b43ecaa2755a27f6)

## Tecnologias Adotada
A linguagem escolhida para implmentar o teste foi o Kotlin, 
por se tratar de uma linguagem multiparadigma com sintaxe simples e de facil entendimento.
O build e controle de dependencias da aplicação é feito através Gradle, usando notação de kotlin.
Abaixo link de referencia das tecnologias.
 - [Kotlin](https://kotlinlang.org/docs/home.html)
 - [Gradle](https://gradle.com)

## Paradigma empregado
A aplicação foi implementada seguindo os conceitos do [paradigma funcional](https://cs.lmu.edu/~ray/notes/paradigms/)
as variaveis da aplicação são imutaveis, e toda mudança de estado na aplicação ocorre através da aplicação de uma função.
Foi tirado muito proveito dos aspectos funcionais do o kotlin como as [funções de escopo](https://kotlinlang.org/docs/scope-functions.html)

## Tecnica de desenvolvimento empregada: Test Driven Design
Todo a aplicação foi desenvolvimento atravẽs do [TDD](https://www.agilealliance.org/glossary/tdd/#q=~(infinite~false~filters~(postType~(~'page~'post~'aa_book~'aa_event_session~'aa_experience_report~'aa_glossary~'aa_research_paper~'aa_video)~tags~(~'tdd))~searchTerm~'~sort~false~sortDirection~'asc~page~1)).
Os casos de funcionamento entrgues foram usados como ponto de partida para a construção de todos os testes, e atrvés deles
todas as funcçẽos foram implementadas.

## Arquitetura e organização do codigo.
Todo o codigo foi organizado seguindo o principios da [arquietura hexagonal](https://alistair.cockburn.us/hexagonal-architecture/),
porem como o escopo do teste é extramamente simples, foi usado apenas um esboço dela, onde as funções de
`stdin` e `stdout` estão é um pacote adapter apartado do dominio, e as funções que operam as regras, ou seja 
o dominio esta aprtaddo em um pacote só delas, sem depencias de detalhe de leitura e escrita.

## Executando a aplicação
Afim de simplificar a execução da aplicação, foi implementado script bem simples
que realiza o build da aplicação e executa os seis cenarios disponilizados.
Para executar a aplicação, siga os passos abaixos:
 - Abra o terminal do sistema operacional
 - Navegue até a pasta que se encontra os codigos da aplicação.
 - a partir da pasta raiz do projeto e execute no terminal o comando
   - `.\start-app.sh` <br/>
   
 Se tudo ocorrer como esperado deve-se obter ma saida seis listas com o valor do imposto a ser cobrado em cada operação.