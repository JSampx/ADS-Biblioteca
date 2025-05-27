import DAO.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void executar() throws SQLException {
        System.out.println("Sistema da biblioteca da Escola Municipal \n### Aprender Mais ###\n");
        var option = -1;
        while (true) {
            System.out.println("1 - Registrar Empréstimo");
            System.out.println("2 - Registrar Devolução");
            System.out.println("3 - Exibir empréstimos");
            System.out.println("______________________________");
            System.out.println("4 - Inserir Aluno");
            System.out.println("5 - Atualizar Aluno");
            System.out.println("6 - Deletar Aluno");
            System.out.println("______________________________");
            System.out.println("7 - Inserir Livro");
            System.out.println("8 - Atualizar Livro");
            System.out.println("9 - Deletar Livro");
            System.out.println("______________________________");

            System.out.println("0 - Encerrar");
            // Verificar se a entrada do usuário é válida
//            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
//            } else {
//                System.out.println("Entrada inválida. Por favor, insira um número.");
//                scanner.next(); // Limpar o buffer
//                continue;
//            }
            switch (option) {
                case 1 -> registrarEmprestimo();
                case 2 -> registrarDevolucao();
                case 3 -> exibirEmprestimos();
                case 4 -> inserirAluno();
                case 5 -> atualizarAluno();
                case 6 -> deletarAluno();
                case 7 -> inserirLivro();
                case 8 -> atualizarLivro();
                case 9 -> deletarLivro();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void registrarEmprestimo() {
        System.out.println("Informe o ID do aluno:");
        int idAluno = scanner.nextInt();

        System.out.println("Informe o ID do livro:");
        int idLivro = scanner.nextInt();

        //System.out.println("Informe a data de devolução (formato: AAAA-MM-DD):");
        //LocalDate dataEmprestimo = LocalDate.parse(scanner.next());

        try {
            var emprestimoDAO = new EmprestimoDAO();
            var alunoDao = new AlunoDAO();
            var livroDao = new LivroDAO();
            var aluno = alunoDao.findById(idAluno);
            var livro = livroDao.findByID(idLivro);
            emprestimoDAO.registrarEmprestimo(new Emprestimo(aluno, livro, LocalDate.now()));
            System.out.println("Empréstimo registrado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao registrar empréstimo.");
        }
    }

    private void registrarDevolucao() {
        var dao = new EmprestimoDAO();
        System.out.println("Digite o ID do empréstimo para realizar a devolução");
        var emprestimoId = scanner.nextInt();
        System.out.println("Deseja registrar a devolução do seguinte livro:");
        System.out.println(dao.findById(emprestimoId));
        System.out.println("1- Sim");
        System.out.println("2 - Não");
        var option = scanner.nextInt();
        switch (option){
            case 1 -> dao.registrarDevolucao(emprestimoId);
            case 2 -> System.exit(0);
        }


    }

    private void exibirEmprestimos() {

        try {
            var dao = new EmprestimoDAO();
            var lista = dao.listarEmprestimos();

            for (var emp : lista) {
                System.out.printf("ID do Empréstimo: %d | Aluno ID: %d | Aluno nome: %s| Livro ID: %d | Título: %s | Empréstimo: %s | Devolução: %s\n",
                        emp.getIdEmprestimo(),
                        emp.getAluno().getIdAluno(),
                        emp.getAluno().getNomeAluno(),
                        emp.getLivro().getIdLivro(),
                        emp.getLivro().getTitulo(),
                        emp.getDataEmprestimo(),
                        emp.getDataDevolucao());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao exibir empréstimos.");
        }
    }


    private void inserirAluno() {
        var aluno = new Aluno();
        System.out.println("Informe o nome do(a) Aluno(a):");
        aluno.setNomeAluno(scanner.next());
        System.out.println("Informe a matricula do(a) Aluno(a):");
        aluno.setMatricula(scanner.next());
        System.out.println("Informe a data de nascimento do(a) Aluno(a) formato 'AAAA-MM-DD':");
        aluno.setDataNascimento(LocalDate.parse(scanner.next()));
        var obj = new AlunoDAO();
        obj.insert(aluno);
    }

    private void atualizarAluno() {
        var alunoDAO = new AlunoDAO();
        System.out.println("Informe os dados do aluno a serem atualizados");
        var lista = alunoDAO.findAll();
        for (Aluno obj : lista) {
            System.out.println(obj);
        }
        System.out.println("Qual o número do aluno que deseja atualizar?");
        int alunoID = scanner.nextInt();
        var alunoAtualizar = alunoDAO.findById(alunoID);
        System.out.println("Digite o nome do Aluno");
        alunoAtualizar.setNomeAluno(scanner.next());
        System.out.println("Digite a matricula do Aluno");
        alunoAtualizar.setMatricula(scanner.next());
        System.out.println("Digite a nome do Aluno");
        alunoAtualizar.setDataNascimento(LocalDate.parse(scanner.next()));
        alunoDAO.update(alunoAtualizar);
    }

    private void deletarAluno() {
        var aluno = new AlunoDAO();
        var lista = aluno.findAll();
        for (Aluno obj : lista) {
            System.out.println(obj);
        }
        System.out.println("Qual o número do aluno que deseja excluir?");
        int alunoID = scanner.nextInt();
        aluno.deleteById(alunoID);

    }

    private void inserirLivro() {
        var livro = new Livro();
        System.out.println("Insira o nome do livro");
        livro.setTitulo(scanner.next());
        System.out.println("Insira o nome do Autor");
        livro.setAutor(scanner.next());
        System.out.println("Insira o ano de publicação do livro");
        livro.setAnoPublicacao(scanner.nextInt());
        System.out.println("Insira estoque do livro");
        livro.setQuantidadeEstoque(scanner.nextInt());
        var obj = new LivroDAO();
        obj.insert(livro);
    }

    private void atualizarLivro() {
        LivroDAO livroDAO = new LivroDAO();
        var lista = livroDAO.findAll();
        for (Livro livro : lista) {
            System.out.println(livro);
        }

        System.out.println("Informe o ID do livro que deseja atualizar:");
        int id = scanner.nextInt();

        Livro livro = new Livro();
        livro.setIdLivro(id);

        System.out.println("Novo título:");
        livro.setTitulo(scanner.next());

        System.out.println("Novo autor:");
        livro.setAutor(scanner.next());

        System.out.println("Novo ano de publicação:");
        livro.setAnoPublicacao(scanner.nextInt());

        System.out.println("Nova quantidade em estoque:");
        livro.setQuantidadeEstoque(scanner.nextInt());

        livroDAO.update(livro);
        System.out.println("Livro atualizado com sucesso.");
    }

    private void deletarLivro() {
    }
}
