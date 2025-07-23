package dao;

// importa o modelo Perfil e a classe de ligação à base de dados

import model.Perfil;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações sobre a tabela 'perfil'
 *
 * Permite consultar e listar os perfis de acesso
 */
public class PerfilDAO {

    /**
     * Obtém um perfil a partir do seu ID
     *
     * @param id identificador do perfil
     * @return objeto Perfil preenchido ou null se não existir
     */
    public Perfil obterPorId(int id) {
        Perfil p = null;
        try (Connection c = DBConnection.getConnection()) {
            // query SQL para selecionar o perfil com o ID fornecido
            String sql = "SELECT * FROM perfil WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // se encontrou resultado, popula o objeto Perfil
            if (rs.next()) {

                p = new Perfil();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // imprime erros para debug
        }
        return p;
    }


    /**
     * Obtém um perfil a partir da descrição
     *
     * @param descricao texto descritivo do perfil
     * @return objeto Perfil preenchido ou null se não existir
     */
    public Perfil obterPorDescricao(String descricao) {
        Perfil p = null;
        try (Connection c = DBConnection.getConnection()) {
            // query para procurar o perfil com a descrição fornecida
            String sql = "SELECT * FROM perfil WHERE nome=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, descricao);
            ResultSet rs = ps.executeQuery();

            // se encontrou resultado, popula o objeto Perfil
            if (rs.next()) {
                p = new Perfil();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}