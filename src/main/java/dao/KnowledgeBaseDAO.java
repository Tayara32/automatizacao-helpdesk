package dao;

import model.KnowledgeEntry;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo acesso à tabela de base de conhecimento (knowledge_base).
 *
 * Esta DAO permite:
 * <ul>
 *   <li>Buscar perguntas e respostas relacionadas a uma palavra-chave.</li>
 *   <li>Marcar perguntas como ineficazes quando uma resposta não é útil.</li>
 * </ul>
 *
 * Conexão com o banco de dados é feita via {@link DBConnection}.
 */


public class KnowledgeBaseDAO {

    /**
     * Busca sugestões de perguntas na base de conhecimento que contenham a palavra-chave informada.
     *
     * @param palavraChave Texto utilizado para buscar perguntas relacionadas.
     * @return Lista de {@link KnowledgeEntry} contendo perguntas e respostas correspondentes.
     */

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

    /**
     * Marca uma pergunta como ineficaz (eficaz = FALSE) na base de conhecimento.
     *
     * @param pergunta Texto da pergunta que será marcada como ineficaz.
     */
    public static void marcarComoIneficaz(String pergunta) {
        String sql = "UPDATE knowledge_base SET eficaz = FALSE WHERE LOWER(pergunta) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + pergunta.toLowerCase() + "%");

            System.out.println(">> SQL executada: " + sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
