package com.appacademia.dao;

import com.appacademia.Database;
import com.appacademia.model.Treino;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinoDAO {

    public List<Treino> findAllByUsuario(int idUsuario) throws SQLException {
        List<Treino> list = new ArrayList<>();
        String sql = "SELECT * FROM treino WHERE id_usuario = ? ORDER BY data_criacao DESC";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Treino t = new Treino(
                            rs.getInt("id_treino"),
                            rs.getInt("id_usuario"),
                            rs.getString("tipo_treino"),
                            rs.getBytes("pdf_treino"),
                            rs.getTimestamp("data_criacao").toLocalDateTime(),
                            rs.getString("observacoes")
                    );
                    list.add(t);
                }
            }
        }
        return list;
    }

    public void insert(Treino t, InputStream pdfStream) throws SQLException {
        String sql = "INSERT INTO treino (id_usuario, tipo_treino, pdf_treino, observacoes) VALUES (?, ?, ?, ?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, t.getIdUsuario());
            ps.setString(2, t.getTipoTreino());
            ps.setBinaryStream(3, pdfStream);
            ps.setString(4, t.getObservacoes());
            ps.executeUpdate();
        }
    }

    public boolean deleteById(int idTreino) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM treino WHERE id_treino = ?")) {
            ps.setInt(1, idTreino);
            return ps.executeUpdate() > 0;
        }
    }
}
