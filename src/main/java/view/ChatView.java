package view;

import controller.ChatController;
import model.Utilizador;
import util.Session;

import javax.swing.*;
import java.awt.*;

/**
 * Interface gráfica do chatbot.
 * <p>
 * Permite ao usuário digitar perguntas, visualizar respostas e enviar feedback de utilidade.
 * </p>
 */

public class ChatView {
    private ChatController controller;
    private JTextField campoPergunta;
    private JTextArea areaResposta;


    /**
     * Construtor da interface, recebendo o controller.
     *
     * @param controller Controlador do chatbot.
     */
    public ChatView(ChatController controller) {
        this.controller = controller;
    }


    /**
     * Inicializa a interface do usuário.
     * Cria a janela, campos de entrada, área de resposta e botões.
     * Exibe uma mensagem de boas-vindas personalizada.
     */
    public void initUI() {
        // Obtém o utilizador logado
        Utilizador user = Session.getUtilizador();
        String perfilMsg;
        switch (user.getPerfilId()) {
            case 1:
                perfilMsg = "Administrador";
                break;
            case 2:
                perfilMsg = "Colaborador";
                break;
            case 3:
                perfilMsg = "Backoffice";
                break;
            default:
                perfilMsg = "Utilizador";
        }

        // Janela principal
        JFrame frame = new JFrame("HelpDesk Bot - " + perfilMsg + " " + user.getNome());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // adiciona o ícone da aplicação
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icone.png"));
        frame.setIconImage(icon.getImage());

        campoPergunta = new JTextField();
        JButton btnEnviar = new JButton("Enviar");
        areaResposta = new JTextArea();
        areaResposta.setEditable(false);

        // Mensagem de boas-vindas
        areaResposta.setText("Olá " + perfilMsg + " " + user.getNome() + "! Como posso ajudar hoje?");

        btnEnviar.addActionListener(e -> {
            String pergunta = campoPergunta.getText();
            controller.buscarResposta(pergunta);
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(campoPergunta, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaResposta), BorderLayout.CENTER);
        painel.add(btnEnviar, BorderLayout.SOUTH);

        frame.add(painel);
        frame.setVisible(true);
    }


    /**
     * Mostra a resposta com botões de feedback.
     *
     * @param resposta        Texto da resposta.
     * @param perguntaOriginal Texto da pergunta original.
     * @param perguntaId      ID da pergunta selecionada.
     */
    public void mostrarRespostaComFeedback(String resposta, String perguntaOriginal, int perguntaId){
        JFrame frameFeedback = new JFrame("Feedback");
        frameFeedback.setSize(400, 200);
        frameFeedback.setLayout(new BorderLayout());

        JTextArea area = new JTextArea(resposta);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setBackground(null);
        area.setBorder(null);

        JPanel painelBotoes = new JPanel();
        JButton btnUtil = new JButton("👍 Útil");
        JButton btnNaoUtil = new JButton("👎 Não foi útil");

        painelBotoes.add(btnUtil);
        painelBotoes.add(btnNaoUtil);

        btnUtil.addActionListener(e -> {
            frameFeedback.dispose();
            JOptionPane.showMessageDialog(null, "Obrigado pelo feedback! 😊");
        });

        btnNaoUtil.addActionListener(e -> {
            frameFeedback.dispose();
            int option = JOptionPane.showConfirmDialog(null, "Deseja abrir um ticket?", "Ticket", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.abrirTicket(perguntaOriginal, perguntaId);
            }
        });


        frameFeedback.add(new JScrollPane(area), BorderLayout.CENTER);
        frameFeedback.add(painelBotoes, BorderLayout.SOUTH);
        frameFeedback.setLocationRelativeTo(null);
        frameFeedback.setVisible(true);
    }


}
