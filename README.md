# Serviço web Pizzaria zeff

![GitHub](https://img.shields.io/github/license/kleberkaerk/web-service-pizzaria-zeff)

<br>

# Descrição

Esta aplicação é um serviço web para uma simulação de pizzaria. Ela atende a requisições vindas do projeto [pizzaria-zeff-front-v1](https://github.com/kleberkaerk/pizzaria-zeff-front-v1), e processa uma resposta para o mesmo.

<br>

# Funcionalidades

* Cadastra usuários
* Autentica usuários
* Efetua o cancelamento da compra do usuário
* Efetua a finalização de compras
* Efetua o cancelamento da finalização de compras
* Efetua a entrega de compras
* Busca as compras de um usuário
* Busca os pedidos da pizzaria
* Busca as compras que já foram entregues
* Efetua a exclusão de compras
* Controla o estoque de produtos
* Cadastra produtos
* Efetua atualização em classificação de preço de produto
* Efetua atualização em preço de produto
* Busca produtos por nome
* Busca produtos em promoção
* Busca todos os produtos com estoque
* Busca produtos por tipo
* Busca todos os produtos
* Efetua a exclusão de produtos
* Cadastra endereços para um usuário
* Busca os endereços de um usuário
* Efetua a exclusão de endereços
* Efetua a simulação de uma venda

<br>

# Documentação swagger

Para ler a documentação swagger da aplicação, acesse [documentação-swagger](http://ec2-54-196-35-87.compute-1.amazonaws.com:8080/swagger-ui/index.html).

<br>

# Tecnologias utilizadas

1. Java 17
2. Maven
3. Spring Boot
4. Docker

<br>

# Execução 

Para executar a aplicação localmente siga os seguintes passos:

* Navegue até o diretório raiz do projeto (onde se encontra o arquivo docker-compose).
* Abra um terminal e execute o comando <code>docker-compose up</code>.
* Abra outro terminal e execute o comando <code>mvn clean package spring-boot:run</code>.

<br>

# Testes unitários

Para executar os testes unitários siga os seguintes passos:

* Navegue até o diretório raiz do projeto (onde se encontra o arquivo docker-compose).
* Abra um terminal e execute o comando <code>docker-compose up</code>.
* Abra outro terminal e execute o comando <code>mvn test</code>.

<br>

# Autor

[<img src="./imagem.jpg" width=115><br><sub>Kleber Kaerk</sub>](https://github.com/kleberkaerk)