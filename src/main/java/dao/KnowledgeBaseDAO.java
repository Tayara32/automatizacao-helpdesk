package dao;

import model.KnowledgeEntry;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeBaseDAO {

    public List<KnowledgeEntry> buscarSugestoes(String palavraChave) {
        List<KnowledgeEntry> sugestoes = new ArrayList<>();
        String sql = "SELECT * FROM knowledge_base WHERE LOWER(pergunta) LIKE ? AND eficaz = TRUE";



        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + palavraChave.toLowerCase() + "%");
            System.out.println(">> SQL executada: " + sql);
            System.out.println(">> Palavra-chave: %" + palavraChave.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("ultima_atualizacao");
                LocalDateTime dataAtualizacao = (ts != null) ? ts.toLocalDateTime() : null;

                KnowledgeEntry entry = new KnowledgeEntry(
                        rs.getInt("id"),
                        rs.getString("pergunta"),
                        rs.getString("resposta"),
                        rs.getBoolean("eficaz"),
                        dataAtualizacao
                );
                sugestoes.add(entry);
                System.out.println(">> ENCONTRADO: " + rs.getString("pergunta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sugestoes;
    }

    public static void marcarComoIneficaz(String pergunta) {
        String sql = "UPDATE knowledge_base SET eficaz = FALSE WHERE LOWER(pergunta) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + pergunta.toLowerCase() + "%");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
