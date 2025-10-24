package com.appacademia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // 🔧 Configurações do banco
    private static final String URL = "jdbc:postgresql://localhost:5432/Academia";
    private static final String USER = "postgres"; // altere se necessário
    private static final String PASSWORD = "postgres"; // altere para sua senha real

    /**
     * Retorna uma conexão com o banco de dados PostgreSQL.
     * Lança SQLException caso ocorra algum problema.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Verifica se o driver está disponível
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver do PostgreSQL não encontrado. Adicione a dependência ao pom.xml.");
            throw new SQLException("Driver não encontrado.", e);
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e; // relança para controle superior
        }
    }

    /**
     * Fecha uma conexão de forma segura (sem precisar repetir try/catch em todo lugar).
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // Teste rápido de conexão
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conectado com sucesso ao banco Academia!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
