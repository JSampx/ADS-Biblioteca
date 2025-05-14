import model.Aluno;
import model.Livro;
import model.Emprestimo;

public class Main {
    public static void main(String[] args) {
        
        Aluno aluno = new Aluno(1, "Maria Oliveira", "20231234", "2001-05-20");
        System.out.println("Aluno: " + aluno.getNomeAluno());
        System.out.println("Matrícula: " + aluno.getMatricula());

       
        Livro livro = new Livro(1, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943, 3);
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());

        
        Emprestimo emp = new Emprestimo(1, aluno.getIdAluno(), livro.getIdLivro(), "2025-05-14", "2025-05-21");
        System.out.println("Empréstimo criado para o aluno ID " + emp.getIdAluno() + " com o livro ID " + emp.getIdLivro());
    }
}
