package dao;

import model.Utilizador;
import util.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações sobre a tabela utilizador
 */
public class UtilizadorDAO {

    /**
     * Lista todos os utilizadores
     *
     * @return lista de utilizadores
     */
    public List<Utilizador> listarTodos() {
        List<Utilizador> lista = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT * FROM utilizadores";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setPerfilId(rs.getInt("perfil_id"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    /**
     * Verifica se um email já existe
     *
     * @param email email a verificar
     * @return true se existir, false caso contrário
     */
    public boolean existeEmail(String email) {
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM utilizadores WHERE email=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





    /**
     * Obtém um utilizador pelo email
     *
     * @param email email do utilizador
     * @return objeto Utilizador ou null se não existir
     */
    public Utilizador obterPorEmail(String email) {
        Utilizador u = null;
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT * FROM utilizadores WHERE email=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setPerfilId(rs.getInt("perfil_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Obtém um utilizador pelo ID
     *
     * @param id ID do utilizador
     * @return objeto Utilizador ou null se não existir
     */
    public Utilizador obterPorId(int id) {
        Utilizador u = null;
        try (Connection c = DBConnection.getConnection()) {
            String sql = "SELECT * FROM utilizadores WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setPerfilId(rs.getInt("perfil_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }




}