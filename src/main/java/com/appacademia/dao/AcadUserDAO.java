package com.appacademia.dao;

import com.appacademia.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcadUserDAO {

    public void vincularUsuarioAcademia(int idAcademia, int idUsuario) throws SQLException {
        String sql = "INSERT INTO acadUser (id_academia, id_user) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idAcademia);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public List<Integer> listarUsuariosDaAcademia(int idAcademia) throws SQLException {
        List<Integer> lista = new ArrayList<>();
        String sql = "SELECT id_user FROM acadUser WHERE id_academia = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idAcademia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(rs.getInt("id_user"));
        }
        return lista;
    }

    public void removerVinculo(int idAcademia, int idUsuario) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM acadUser WHERE id_academia = ? AND id_user = ?")) {
            ps.setInt(1, idAcademia);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }
}
