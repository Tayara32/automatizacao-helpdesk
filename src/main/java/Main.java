import controller.ChatController;
import view.LoginView;


/**
 * Classe principal do sistema.
 * <p>
 * Ponto de entrada da aplicação, responsável por iniciar o chatbot.
 * </p>
 */


public class Main {

    /**
     * Método principal da aplicação.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
