package controller;


import dao.KnowledgeBaseDAO;
import dao.TicketDAO;
import model.KnowledgeEntry;
import view.ChatView;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static util.QRCodeGenerator.gerarQRCode;

/**
 * Controlador principal do chatbot.
 *
 * Responsável por:
 * <ul>
 *   <li>Gerenciar a comunicação entre a interface (ChatView) e os DAOs.</li>
 *   <li>Pesquisar respostas na base de conhecimento.</li>
 *   <li>Criar tickets quando as respostas não são satisfatórias.</li>
 *   <li>Gerar QR Codes para tickets abertos.</li>
 * </ul>
 */

public class ChatController {
    private ChatView view;
    private KnowledgeBaseDAO kbDAO;
    private TicketDAO ticketDAO;

    /**
     * Inicializa os componentes principais do sistema:
     * <ul>
     *   <li>Instancia os DAOs de base de conhecimento e tickets.</li>
     *   <li>Cria a interface do chatbot.</li>
     * </ul>
     */

    public void init() {
        kbDAO = new KnowledgeBaseDAO();    // Cria o acesso à base de conhecimento
        ticketDAO = new TicketDAO();       // Cria o gerenciador de tickets
        view = new ChatView(this);// Cria a interface gráfica e passa o controller
        view.initUI();                     // Mostra a janela com botões, campo de pergunta etc.
    }

    /**
     * Pesquisa por uma palavra-chave na base de conhecimento e exibe sugestões.
     *
     * @param palavraChave Texto ou palavra-chave informada pelo usuário.
     */

    public void buscarResposta(String palavraChave) {
        List<KnowledgeEntry> sugestoes = kbDAO.buscarSugestoes(palavraChave);

        if (sugestoes.isEmpty()) {
            view.mostrarRespostaComFeedback("Nenhuma sugestão encontrada.", palavraChave, -1);
            return;
        }

        String[] opcoes = sugestoes.stream()
                .map(KnowledgeEntry::getPergunta)
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(
                null,
                "Escolha a pergunta que mais se parece com a sua:",
                "Sugestões encontradas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha != null) {
            Optional<KnowledgeEntry> selecionada = sugestoes.stream()
                    .filter(k -> k.getPergunta().equals(escolha))
                    .findFirst();

            if (selecionada.isPresent()) {
                KnowledgeEntry entry = selecionada.get();
                view.mostrarRespostaComFeedback(entry.getResposta(), entry.getPergunta(), entry.getId());
            } else {
                view.mostrarRespostaComFeedback("Não encontrei nada. Deseja abrir um ticket?", palavraChave, -1);
            }
        } else {
            view.mostrarRespostaComFeedback("Nenhuma pergunta foi selecionada.", palavraChave, -1);
        }
    }

    /**
     * Abre um novo ticket quando a resposta não foi útil.
     * Também gera um QR Code com as informações do ticket.
     *
     * @param perguntaTexto Texto da pergunta original feita pelo usuário.
     * @param perguntaId    ID da pergunta selecionada na base de conhecimento.
     */

    public void abrirTicket(String perguntaTexto, int perguntaId) {
        KnowledgeBaseDAO.marcarComoIneficaz(perguntaTexto);

        int ticketId = ticketDAO.salvarTicket(perguntaTexto, perguntaId);
        System.out.println(">> Ticket criado com ID: " + ticketId);

        String dadosTicket = "Ticket ID: " + ticketId + "\n" +
                "Assunto: " + perguntaTexto + "\n" +
                "ID da pergunta base: " + perguntaId + "\n" +
                "Data: " + LocalDateTime.now() + "\n" +
                "Status: Aberto";

        try {
            Image qrImage = gerarQRCode(dadosTicket, 250, 250);
            ImageIcon icon = new ImageIcon(qrImage);
            JOptionPane.showMessageDialog(null, icon, "Ticket QR Code", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar QR Code.");
        }
    }


}