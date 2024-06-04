# Microservices de gerenciamento de usuários e notificação

<p align="left">
  <img alt="RabittMQ" src="https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white" />
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-brightgreen?logo=spring&style=flat-square" />
  <img alt="Postgresql" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
</p>

Este projeto consiste em dois microserviços, um de gestão de usuario e outro de notificação

## Design Patterns Utilizados
- Repository Pattern
- Publisher-Subscriber (pub/sub)

## Pré-requisitos

Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- Maven 3.9.5
- JDK 17
- Docker

## Iniciando...
Executo o seguinte comando para ter o projeto em sua maquina:

- `git clone https://github.com/olavobelfort/microservice-design-patterns.git`

Agora você poderá executar os vários comandos abaixo.

### Configurando o Ambiente

1. Para iniciar o Mensage Broker RabittMQ


2. Execute `cd microservice-design-patterns/notification` para ir até a pasta do primeiro microservice **notification**


3. Execute o seguinte comando:
```bash
docker-compose up --build -d
```

4. Verifique se deu tudo certo acessando a interface do RabittMQ:
   http://localhost:15672/

6. Agora é preciso configurar o ambiente do segundo microservice **user**


7. Vá até a pasta do projeto executando `cd ../user`


8. Execute novamente o comando a seguir para subir o banco de dados Postgresql
```bash
docker-compose up --build -d
```

### Iniciando os microservices

1. Atenção certifiquese que você se encontra no diretorio /user


2. Execute o seguinte comando para iniciar a aplicação do microservice **user**
```bash
./mvnw spring-boot:run
```

3. Agora que você iniciou o microservice user, é preciso que você abra um novo terminal para inicializar o microservice **notification**

4. Vá até o destino do projeto com o nome da pasta do repositório **microservice-design-patterns**


5. Agora para iniciar o microservice **notification** execute o seguinte comando
```bash
cd notification

./mvnw spring-boot:run
```

### Testar o funcionamento dos mciroservices

1. O microservice user, expõe 3 endpoints:
- http://localhost:8081/user/create (POST)
- http://localhost:8081/user/list (GET)
- http://localhost:8081/user/disable (PUT)

Quando executado o endpoint create e o disable é disparado uma mensagem na fila utilizada do RabittMQ.

Fazendo com que o microservice notification que possui um consumer, será capaz de ler a mensagem enviada. Olhando o terminal do notification será possivel visualizar a mensagem enviada


## Observações

- O microservice notification, não possui complexibilidade, somente o consumer da fila utilizada, porem é possivel adicionar novas features aproveitando essa estruta, como por exemplo adicionar um seviço SMTP para realizar o encaminhamento da mensagem no e-mail do usuario
