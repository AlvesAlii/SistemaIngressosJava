package Dao;

import Ingressos.*;
import java.sql.*;
import java.util.*;

// Classe de acesso a DB
 
public class IngressosDAO {
    private final String URL = "jdbc:mysql://localhost:3306/sistema_ingressos";
    private final String USER = "root";
    private final String PASSWORD = "";

    // Insere os três tipos de ingressos (Normal, Meia e VIP) para um evento

    public void inserirIngressos(String evento, java.sql.Date data, double valorNormal, double valorMeia, double valorVIP) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            
            inserirTipoIngresso(conn, evento, data, valorNormal, "NORMAL", "Ingresso Normal");
            inserirTipoIngresso(conn, evento, data, valorMeia, "MEIA", "Meia Entrada");
            inserirTipoIngresso(conn, evento, data, valorVIP, "VIP", "Área VIP");
            
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ingressos: " + e.getMessage());
        }
    }

    private void inserirTipoIngresso(Connection conn, String evento, java.sql.Date data, double valorBase, String tipo, String detalhe) throws SQLException {
        String sql = "INSERT INTO ingresso (evento, data_evento, valor_base, tipo, detalhe_evento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento);
            stmt.setDate(2, data);
            stmt.setDouble(3, valorBase);
            stmt.setString(4, tipo);
            stmt.setString(5, detalhe);
            stmt.executeUpdate();
    }
}

    public List<Ingresso> listarIngressos() {
        List<Ingresso> lista = new ArrayList<>();
        String sql = "SELECT * FROM ingresso";

        try (var conn = DriverManager.getConnection(URL, USER, PASSWORD);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(criarIngressoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Ingresso> buscarIngressosPorEvento(String nomeEvento) {
        List<Ingresso> ingressos = new ArrayList<>();
        String sql = "SELECT * FROM ingresso WHERE evento = ?";
        
        try (var conn = DriverManager.getConnection(URL, USER, PASSWORD);
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeEvento);
            var rs = stmt.executeQuery();
            
            while (rs.next()) {
                ingressos.add(criarIngressoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingressos;
    }

private Ingresso criarIngressoFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String tipo = rs.getString("tipo");
    String evento = rs.getString("evento");
    java.sql.Date data = rs.getDate("data_evento"); // java.sql.Date
    double valorBase = rs.getDouble("valor_base");
    String detalhe = rs.getString("detalhe_evento");

    switch (tipo) {
        case "VIP":
            return new IngressoVIP(id, evento, data, valorBase, detalhe);
        case "MEIA":
            return new IngressoMeia(id, evento, data, valorBase, detalhe);
        default:
            return new IngressoNormal(id, evento, data, valorBase, detalhe);
    }
}

    public Set<String> listarEventosUnicos() {
        Set<String> eventos = new HashSet<>();
        String sql = "SELECT DISTINCT evento FROM ingresso";

        try (var conn = DriverManager.getConnection(URL, USER, PASSWORD);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                eventos.add(rs.getString("evento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }
}