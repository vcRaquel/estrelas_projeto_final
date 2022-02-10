Projeto Final Estrelas Fora da Caixa - ZUP READERS

Esta aplicação é uma rede social voltada pra interagir sobre livros. Os usuários além de terem liberdade de criarem reviews para as obras que desejarem, podem também interagir com outros, através de comentários e curtidas.

## Regras de Negócio

#### USUÁRIO
- O usuário pode criar uma conta;
- O usuário pode realizar o login na aplicação utilizando sua conta;
- As demais funcionalidades só podem ser acessadas se o usuário tiver realizado o login;
- O Usuário só pode deletar e atualizar seu próprio cadastro;
- O Usuário possui uma lista de livros cadastrados, que é preenchida a cada novo livro que ele postar;
- O Usuário possui uma lista de interesses e a possibilidade de adicionar ou remover livros já cadastrados à essa lista;
- O Usuário possui uma pontuação que é atualizada quando ele comenta em alguma review ou cadastra uma nova;
- Esta pontuação só é aplicada no primeiro comentário que o Usuário fizer em um review;
- A Lista de Usuários pode ser filtrada por nome e ordenada por pontuação.

#### LIVRO
- O livro possui Gênero e Tags;
- O livro possui um Review, com relação OneToOne - Persist;
- O livro possui uma lista de comentários;
- O livro possui os atributos nomeTratado e autorTratado, que padroniza os nomes inseridos. Esses atributos são utilizados nas operações de comparação;
- A exibição dos livros possibilita a filtragem por genero, tags, nome e autor;
- O livro só pode ser removido ou atualizado pelo usuário que o cadastrou.

#### COMENTÁRIO
- O comentário possui o atributo quemComentou que é preenchido automaticamente pelo usuário logado no momento que é comentado;
- O comentário só pode ser atualizado ou deletado pelo usuário que o comentou;
- A pontuação gerada pelo comentário só deve ser aplicada no primeiro comentário realizado pelo Usuario ao review em questão.

#### CURTIDA
- Não é possível curtir mais de uma vez o mesmo recurso;
- Caso essa solicitação aconteça, a curtida será removida;
- Ao curtir, o recurso curtido terá seu atributo curtidas incrementado em 1;
- Se a curtida for removida, esse mesmo atributo é decrementado em 1.

#### REVIEW
- É impossível o cadastro de um livro sem uma review;
- A review persistirá automaticamente no banco de dados no momento que o livro for cadastrado.

## Como Rodar a API localmente

> Pré-requisitos:

- [JDK](https://www.oracle.com/java/technologies/downloads/)
- [IDE Java](https://www.jetbrains.com/pt-br/idea/)
- [MariaDB/MySQL](https://mariadb.org)
- [Postman](https://www.postman.com)

Url da aplicação: localhost:8080

## Tecnologias utilizadas

- JAVA 11
- Springboot
- JPA
- Hibernate
- Swagger
- Maven
- Spring Security
- ModelMapper
- Spring Test
- Lombok
- JWT

## SERVIÇOS

Mapeamento de rotas disponibilizado via Swagger

- Swagger:  localhost:8080/swagger-ui.html 
