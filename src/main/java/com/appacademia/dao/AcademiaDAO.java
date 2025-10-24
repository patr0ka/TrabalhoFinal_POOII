package com.appacademia.dao;

import com.appacademia.Database;
import com.appacademia.model.Academia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademiaDAO {

    public List<Academia> findAll() throws SQLException {
        List<Academia> list = new ArrayList<>();
        String sql = "SELECT * FROM academia ORDER BY nome";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Academia a = new Academia(
                        rs.getInt("id_academia"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("localizacao"),
                        rs.getString("telefone")
                );
                list.add(a);
            }
        }
        return list;
    }

    public Academia insert(Academia a) throws SQLException {
        String sql = "INSERT INTO academia (nome, cnpj, localizacao, telefone) VALUES (?, ?, ?, ?) RETURNING id_academia";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getCnpj());
            ps.setString(3, a.getLocalizacao());
            ps.setString(4, a.getTelefone());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        }
        return a;
    }

    public boolean deleteById(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM academia WHERE id_academia = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
