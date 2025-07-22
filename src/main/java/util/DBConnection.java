package util;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe utilitária para gerenciar a conexão com o banco de dados.
 * <p>
 * Lê os parâmetros de conexão de um arquivo <code>config.properties</code> e retorna uma conexão ativa.
 * </p>
 */


public class DBConnection {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém uma conexão ativa com o banco de dados MySQL.
     *
     * @return Objeto Connection ativo.
     * @throws SQLException caso a conexão falhe.
     */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
