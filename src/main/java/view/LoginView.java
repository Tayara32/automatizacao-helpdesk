package view;

// importa o controlador responsável pela autenticação
import controller.LoginController;
import model.Utilizador;
import util.Session;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável pela interface de login do backoffice
 */
public class LoginView extends JFrame {

    // campo de texto para o email (utilizador)
    private JTextField txtEmail;

    // campo de password
    private JPasswordField txtPassword;

    // botão de login
    private JButton btnLogin;

    /**
     * Construtor da interface de login
     */
    public LoginView() {
        setTitle("Login - HelpDesk");
        setSize(400, 350);
        util.IconeUtil.aplicarIcone(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes do ecrã de login
     */
    private void inicializarComponentes() {
        JPanel titulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel tituloLabel = new JLabel("HelpDesk - Chat");
        tituloLabel.setFont(new Font(tituloLabel.getFont().getFontName(), Font.BOLD, 24));
        titulo.add(tituloLabel);
        titulo.setBackground(new Color(65, 102, 124, 255));
        tituloLabel.setForeground(new Color(232, 237, 243));

        ImageIcon logoOriginal = new ImageIcon(getClass().getResource("/images/icone.png"));
        Image imagemRedimensionada = logoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagemRedimensionada);
        JLabel logoLabel = new JLabel(logo);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Painel central com imagem + formulário
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(Box.createVerticalStrut(10)); // Espaço entre título e imagem
        centro.add(logoLabel);
        centro.add(Box.createVerticalStrut(10)); // Espaço entre imagem e formulário
        centro.add(painel);
        // etiqueta e campo de email
        JLabel lblEmail = new JLabel("Utilizador (Email):");
        txtEmail = new JTextField();

        // etiqueta e campo de password
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        // botão de login
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> fazerLogin());

        // organiza os componentes no painel
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblPassword);
        painel.add(txtPassword);
        painel.add(new JLabel()); // espaço vazio para alinhamento
        painel.add(btnLogin);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(centro, BorderLayout.CENTER);
//        painelPrincipal.add(painel, BorderLayout.CENTER);
        painelPrincipal.setBackground(new Color(255, 255, 255, 255));

        setContentPane(painelPrincipal);
    }

    private void fazerLogin() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        LoginController controllerLogin = new LoginController();
        Utilizador user = controllerLogin.autenticar(email, password);

        if (user != null) {
            int perfil = user.getPerfilId();
            String mensagem = "Bem-vindo!";

            if (perfil == 1) {
                mensagem = "Bem-vindo Administrador!";
            } else if (perfil == 2) {
                mensagem = "Bem-vindo Colaborador!";
            } else if (perfil == 3) {
                mensagem = "Bem-vindo Backoffice!";
            }

            JOptionPane.showMessageDialog(this, mensagem);
            Session.setUtilizador(user);
            System.out.println("Usuário logado: " + user.getId() + " - " + user.getNome());


            // Inicia o chatbot
            controller.ChatController chatController = new controller.ChatController();
            chatController.init();  // <-- inicia o ChatView

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas!");
        }
    }





    /**
     * Método main para arrancar a aplicação diretamente
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}