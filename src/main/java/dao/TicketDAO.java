package dao;

import model.Ticket;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;


/**
 * DAO responsável pela manipulação de dados relacionados a tickets no sistema de Help Desk.
 * <p>
 * Esta classe fornece métodos para:
 * <ul>
 *   <li>Registrar novos tickets no banco de dados.</li>
 *   <li>Gerar o ID do ticket recém-criado.</li>
 * </ul>
 */

public class TicketDAO {

    /**
     * Salva um novo ticket na tabela <code>tickets</code>.
     *
     * @param perguntaTexto Texto original da pergunta feita pelo usuário.
     * @param perguntaId    ID da pergunta relacionada (da base de conhecimento).
     * @return ID do ticket criado no banco de dados ou -1 caso ocorra algum erro.
     */

    public int salvarTicket(String perguntaTexto, int perguntaId) {
        String sql = "INSERT INTO tickets (pergunta, pergunta_id, data_abertura, status) VALUES (?, ?, ?, 'Aberto')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, perguntaTexto);
            ps.setInt(2, perguntaId);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            System.out.println(">> SQL executada: " + sql);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
