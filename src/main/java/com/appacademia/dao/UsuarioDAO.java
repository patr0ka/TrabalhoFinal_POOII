package com.appacademia.dao;

import com.appacademia.Database;
import com.appacademia.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public UsuarioDAO() {
        try {
            criarUsuariosDeTeste(); // 🔹 cria automaticamente admin/aluno se não existirem
        } catch (SQLException e) {
            System.err.println("⚠️ Erro ao criar usuários de teste: " + e.getMessage());
        }
    }

    // 🔹 Cria admin e aluno de teste se o banco estiver vazio
    private void criarUsuariosDeTeste() throws SQLException {
        try (Connection c = Database.getConnection()) {
            // Verifica se já existem usuários
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM usuario");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return; // já existem, não cria
                }
            }

            // Cria administrador
            int idAdmin = 0;
            try (PreparedStatement ps = c.prepareStatement("""
                INSERT INTO usuario (nome, cpf, email, tipo_usuario)
                VALUES ('Administrador Teste', '00000000000', 'admin@app.com', 'funcionario')
                RETURNING id_usuario
            """)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) idAdmin = rs.getInt("id_usuario");
                }
            }
            try (PreparedStatement ps = c.prepareStatement("""
                INSERT INTO funcionario (id_usuario, cargo, salario, senha)
                VALUES (?, 'Gerente', 5000, 'admin123')
            """)) {
                ps.setInt(1, idAdmin);
                ps.executeUpdate();
            }

            // Cria aluno
            int idAluno = 0;
            try (PreparedStatement ps = c.prepareStatement("""
                INSERT INTO usuario (nome, cpf, email, tipo_usuario)
                VALUES ('Aluno Teste', '11111111111', 'aluno@app.com', 'cliente')
                RETURNING id_usuario
            """)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) idAluno = rs.getInt("id_usuario");
                }
            }
            try (PreparedStatement ps = c.prepareStatement("""
                INSERT INTO cliente (id_usuario, senha, plano, data_inicio, data_vencimento, status)
                VALUES (?, 'aluno123', 'Mensal', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', TRUE)
            """)) {
                ps.setInt(1, idAluno);
                ps.executeUpdate();
            }

            System.out.println("✅ Usuários de teste criados com sucesso!");
            System.out.println("   • Admin -> ID: " + idAdmin + " | Senha: admin123");
            System.out.println("   • Aluno -> ID: " + idAluno + " | Senha: aluno123");
        }
    }

    // 🔹 Login por ID (para a tela de login)
    public Usuario autenticarPorId(int idUsuario, String senha) throws SQLException {
        String sql = """
        SELECT *
        FROM usuario
        WHERE id_usuario = ?
          AND senha = ?
    """;

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUsuario(rs);
                }
            }
        }
        return null;
    }


    // 🔹 Retorna todos os usuários cadastrados
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT id_usuario, nome, cpf, email, data_nascimento, tipo_usuario FROM usuario ORDER BY nome";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = mapUsuario(rs);
                list.add(u);
            }
        }
        return list;
    }

    // 🔹 Insere um novo usuário manualmente
    public Usuario insert(Usuario u, String senha) throws SQLException {
        String sqlUsuario = """
        INSERT INTO usuario (nome, cpf, email, data_nascimento, tipo_usuario, senha)
        VALUES (?, ?, ?, ?, ?, ?)
        RETURNING id_usuario
    """;

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlUsuario)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getEmail());

            if (u.getDataNascimento() != null)
                ps.setDate(4, Date.valueOf(u.getDataNascimento()));
            else
                ps.setNull(4, Types.DATE);

            ps.setString(5, u.getTipoUsuario());
            ps.setString(6, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u.setId(rs.getInt("id_usuario"));
                }
            }
        }
        return u;
    }


    private void insertCliente(int idUsuario, String senha, Connection c) throws SQLException {
        String sql = """
            INSERT INTO cliente (id_usuario, senha, plano, data_inicio, data_vencimento, status)
            VALUES (?, ?, NULL, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', TRUE)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, senha);
            ps.executeUpdate();
        }
    }

    private void insertFuncionario(int idUsuario, String senha, Connection c) throws SQLException {
        String sql = """
            INSERT INTO funcionario (id_usuario, senha, cargo, salario)
            VALUES (?, ?, NULL, NULL)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, senha);
            ps.executeUpdate();
        }
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        u.setEmail(rs.getString("email"));
        Date d = rs.getDate("data_nascimento");
        if (d != null) u.setDataNascimento(d.toLocalDate());
        u.setTipoUsuario(rs.getString("tipo_usuario"));
        return u;
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
