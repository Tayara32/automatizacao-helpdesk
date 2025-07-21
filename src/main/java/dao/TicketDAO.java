package dao;

import model.Ticket;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class TicketDAO {

    public int salvarTicket(String perguntaTexto, int perguntaId) {
        String sql = "INSERT INTO tickets (pergunta, pergunta_id, data_abertura, status) VALUES (?, ?, ?, 'Aberto')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, perguntaTexto);
            ps.setInt(2, perguntaId);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));


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
