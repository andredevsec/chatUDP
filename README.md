# Chat Application

Este projeto é uma aplicação de chat baseada em Java que permite a comunicação entre clientes através de mensagens de texto. A aplicação consiste em uma interface de linha de comando e uma interface gráfica construída com Swing.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- `chat`: Contém as classes principais para gerenciamento de mensagens e comunicação.
- `client`: Contém as classes do cliente, incluindo a interface gráfica e de linha de comando.

### Pacote `chat`

#### Classes

- `ChatException`: Classe para tratamento de exceções específicas do chat.
- `ChatFactory`: Classe fábrica para criar instâncias de `Sender` e `Receiver`.
- `MessageContainer`: Interface para contêineres de mensagens.
- `Receiver`: Interface para componentes que recebem mensagens.
- `Sender`: Interface para componentes que enviam mensagens.
- `UDPReceiver`: Implementação de `Receiver` usando UDP.
- `UDPSender`: Implementação de `Sender` usando UDP.

### Pacote `client`

#### Classes

- `Chat`: Cliente de linha de comando para o chat.
- `ChatGUI`: Cliente gráfico para o chat usando Swing.
- `SysOutContainer`: Implementação de `MessageContainer` que imprime as mensagens no console.

### Requisitos

- Java Development Kit (JDK) 8 ou superior.
