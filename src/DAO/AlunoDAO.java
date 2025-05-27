package DAO;

import java.sql.*;
import java.util.List;

import model.Aluno;
import util.Conexao;

public class AlunoDAO {
    private Connection conn;

    public AlunoDAO() {
        this.conn = Conexao.getConnection();
    }

    public void insert(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, matricula, data_nascimento) VALUES (?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, aluno.getNomeAluno());
            st.setString(2, aluno.getMatricula());
            st.setDate(3, Date.valueOf(aluno.getDataNascimento()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setIdAluno(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Em produção, use um logger
        }
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, matricula = ?, data_nascimento = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, aluno.getNomeAluno());
            st.setString(2, aluno.getMatricula());
            st.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            st.setInt(4, aluno.getIdAluno());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Aluno findById(Integer id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        Aluno aluno = null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno();
                    aluno.setIdAluno(rs.getInt("id"));
                    aluno.setNomeAluno(rs.getString("nome"));
                    aluno.setMatricula(rs.getString("matricula"));
                    aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aluno;
    }

    public List<Aluno> findAll() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = null;

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id"));
                aluno.setNomeAluno(rs.getString("nome"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                alunos.add(aluno);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return alunos;
    }
}
