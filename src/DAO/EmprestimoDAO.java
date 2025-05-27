package DAO;

import model.Aluno;
import model.Emprestimo;
import model.Livro;
import util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private final Connection conn;

    public EmprestimoDAO() {
        this.conn = Conexao.getConnection();
    }

    public void registrarEmprestimo(Emprestimo emprestimo) {
        try {
            // Verifica estoque do livro
            String verificaEstoque = "SELECT quantidade_estoque FROM livros WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(verificaEstoque)) {
                checkStmt.setInt(1, emprestimo.getLivro().getIdLivro());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int estoque = rs.getInt("quantidade_estoque");
                        if (estoque <= 0) {
                            System.out.println("Livro indisponível para empréstimo.");
                            return;
                        }
                    } else {
                        System.out.println("Livro não encontrado.");
                        return;
                    }
                }
            }

            // Registra o empréstimo
            String insertEmprestimo = "INSERT INTO emprestimos (fk_id_aluno, fk_id_livro, data_emprestimo) VALUES (?, ?, ?)";
            try (PreparedStatement st = conn.prepareStatement(insertEmprestimo, Statement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, emprestimo.getAluno().getIdAluno());
                st.setInt(2, emprestimo.getLivro().getIdLivro());
                st.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
                st.executeUpdate();

                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        emprestimo.setIdEmprestimo(rs.getInt(1));
                    }
                }
            }

            // Atualiza o estoque
            String atualizaEstoque = "UPDATE livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id = ?";
            try (PreparedStatement st = conn.prepareStatement(atualizaEstoque)) {
                st.setInt(1, emprestimo.getLivro().getIdLivro());
                st.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Emprestimo findById(int id){
        var emprestimo = new Emprestimo();
        String sql = "SELECT * FROM emprestimos WHERE id_emprestimo = ?";


        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                var livroDAO = new LivroDAO();
                var alunoDAO = new AlunoDAO();
                if (rs.next()) {
                    emprestimo.setIdEmprestimo(rs.getInt("id_emprestimo"));
                    emprestimo.setAluno(alunoDAO.findById(rs.getInt("fk_id_aluno")));
                    emprestimo.setLivro(livroDAO.findByID(rs.getInt("fk_id_livro")));
                    emprestimo.setDataEmprestimo(LocalDate.parse(rs.getString("data_emprestimo")));
                    emprestimo.setDataDevolucao(LocalDate.parse(rs.getString("data_devolucao")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprestimo;
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Emprestimo e = new Emprestimo();
                e.setIdEmprestimo(rs.getInt("id_emprestimo"));

                // Criar objetos Aluno e Livro apenas com o ID
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("fk_id_aluno"));
                e.setAluno(aluno);

                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("fk_id_livro"));
                e.setLivro(livro);

                e.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                var date = rs.getDate("data_devolucao");
                if (date != null){
                    e.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                }


                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public boolean registrarDevolucao(int idEmprestimo) {
        try {
            // Pega o id do livro a partir do empréstimo
            int idLivro = 0;
            String select = "SELECT id_livro FROM emprestimos WHERE id = ?";
            try (PreparedStatement st = conn.prepareStatement(select)) {
                st.setInt(1, idEmprestimo);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        idLivro = rs.getInt("fk_id_livro");
                    } else {
                        System.out.println("Empréstimo não encontrado.");
                        return false;
                    }
                }
            }

            // Remove o empréstimo
            String sql = "UPDATE emprestimos SET data_devolucao = CURRENT_DATE() WHERE id_emprestimo = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idEmprestimo);
                st.executeUpdate();
            }

            // Atualiza o estoque
            String atualizaEstoque = "UPDATE livros SET quantidade_estoque = quantidade_estoque + 1 WHERE id = ?";
            try (PreparedStatement st = conn.prepareStatement(atualizaEstoque)) {
                st.setInt(1, idLivro);
                st.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
