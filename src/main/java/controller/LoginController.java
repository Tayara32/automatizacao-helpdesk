
package controller;

// importa os DAOs e modelos necessários

import dao.UtilizadorDAO;
import model.Utilizador;
import service.PasswordEncryptionService;


/**
 * Controlador responsável pela autenticação de utilizadores no sistema
 */
public class LoginController {

    /**
     * Autentica o utilizador pelo email e password.
     *
     * @param email        Email do utilizador.
     * @param palavraChave Password em texto simples.
     * @return Objeto Utilizador se login for válido, ou null se inválido.
     */
    public Utilizador autenticar(String email, String palavraChave) {
        UtilizadorDAO dao = new UtilizadorDAO();
        Utilizador u = dao.obterPorEmail(email);

        if (u != null && PasswordEncryptionService.verifyPassword(palavraChave, u.getSenha())) {
            return u; // retorna o utilizador completo
        }
        return null;
    }
}



