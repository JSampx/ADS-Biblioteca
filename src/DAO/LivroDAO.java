package DAO;

import model.Livro;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private Connection conn;

    public LivroDAO() {
        this.conn = Conexao.getConnection();
    }

    public void insert(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, ano_publicacao, quantidade_estoque) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, livro.getTitulo());
            st.setString(2, livro.getAutor());
            st.setInt(3, livro.getAnoPublicacao());
            st.setInt(4, livro.getQuantidadeEstoque());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setIdLivro(rs.getInt(1)); // assumindo que Livro tem o campo id com setter
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Livro livro) {
        String sql = "UPDATE livros SET (titulo, autor, ano_publicacao, quantidade_estoque) VALUES (?,?,?,?) WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, livro.getTitulo());
            st.setString(2, livro.getAutor());
            st.setInt(3, livro.getAnoPublicacao());
            st.setInt(4, livro.getQuantidadeEstoque());
            st.setInt(5, livro.getIdLivro());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Livro findByID(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setIdLivro(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                    livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livro;
    }

    public List<Livro> findAll() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                livros.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
