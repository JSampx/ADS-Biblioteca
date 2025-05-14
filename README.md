# Sistema Simples de Gerenciamento de Biblioteca

Este é um projeto Java simples para gerenciar informações de alunos, livros e empréstimos de uma pequena biblioteca.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

* `model`: Contém as classes de domínio que representam as entidades do negócio (ex: `Aluno`, `Livro`, `Emprestimo`).
* `dao`: Contém as classes Data Access Object (DAO) responsáveis pela comunicação com o banco de dados e execução das operações (CRUD - Create, Read, Update, Delete).
* `util`: Contém classes utilitárias, como a classe para gerenciar a conexão com o banco de dados.
* `Main`: A classe principal que contém o menu para interagir com o sistema via terminal.

## Pré-requisitos

Para executar este projeto, você precisará ter instalado:

* Java Development Kit (JDK) 8 ou superior
* Sistema de gerenciamento de banco de dados MySQL 8.0 ou superior
* Um ambiente de desenvolvimento integrado (IDE) como Eclipse, IntelliJ IDEA ou NetBeans (opcional, mas recomendado)

## Configuração do Banco de Dados

1.  Certifique-se de ter o MySQL instalado e em execução.
2.  Crie um banco de dados para o projeto (por exemplo, `biblioteca`).
3.  Execute os scripts SQL para criar as tabelas necessárias. Com base na estrutura, você precisará das tabelas `Alunos`, `Livros` e `Emprestimos`. Um exemplo básico dos comandos SQL (ajuste conforme necessário com os campos exatos do seu projeto):

    ```sql
    CREATE TABLE Alunos (
        id_aluno INT AUTO_INCREMENT PRIMARY KEY,
        nome_aluno VARCHAR(100) NOT NULL,
        matricula VARCHAR(20) UNIQUE,
        data_nascimento DATE
    );

    CREATE TABLE Livros (
        id_livro INT AUTO_INCREMENT PRIMARY KEY,
        titulo VARCHAR(150) NOT NULL,
        autor VARCHAR(100),
        ano_publicacao INT,
        quantidade_estoque INT DEFAULT 0
    );

    CREATE TABLE Emprestimos (
        id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
        id_aluno INT,
        id_livro INT,
        data_emprestimo DATE DEFAULT CURRENT_DATE,
        data_devolucao DATE,
        FOREIGN KEY (id_aluno) REFERENCES Alunos(id_aluno),
        FOREIGN KEY (id_livro) REFERENCES Livros(id_livro)
    );
    ```

4.  Atualize as informações de conexão com o banco de dados na classe de utilitário de conexão (provavelmente no pacote `util`) para corresponder às suas configurações do MySQL (URL, usuário, senha).

## Como Compilar e Executar

1.  Clone o repositório ou baixe os arquivos do projeto.
2.  Abra o projeto na sua IDE Java preferida.
3.  Certifique-se de que as dependências (como o conector JDBC para MySQL) estejam configuradas no caminho de construção (build path) do projeto. Se estiver usando Maven ou Gradle, as dependências devem estar no arquivo de configuração (`pom.xml` ou `build.gradle`).
4.  Compile o projeto.
5.  Execute a classe `Main`.

## Utilização

Ao executar a classe `Main`, um menu será exibido no terminal, permitindo que você interaja com o sistema para realizar operações como cadastrar alunos, livros, registrar empréstimos, etc. Siga as opções apresentadas no menu.

## Contribuindo

Se desejar contribuir com este projeto, sinta-se à vontade para fazer um fork do repositório e enviar suas sugestões de melhoria.

## Licença

[Opcional: Adicione informações sobre a licença do seu projeto aqui]
