package view;

import controller.ChatController;

import javax.swing.*;
import java.awt.*;

public class ChatView {
    private ChatController controller;
    private JTextField campoPergunta;
    private JTextArea areaResposta;

    public ChatView(ChatController controller) {
        this.controller = controller;
    }

    public void initUI() {
        JFrame frame = new JFrame("HelpDesk Bot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        campoPergunta = new JTextField();
        JButton btnEnviar = new JButton("Enviar");
        areaResposta = new JTextArea();
        areaResposta.setEditable(false);

        btnEnviar.addActionListener(e -> {
            String pergunta = campoPergunta.getText();
            controller.buscarResposta(pergunta); // Agora a controller cuida de tudo
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(campoPergunta, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaResposta), BorderLayout.CENTER);
        painel.add(btnEnviar, BorderLayout.SOUTH);

        frame.add(painel);
        frame.setVisible(true);
    }
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
