package util;

import model.Utilizador;

public class Session {
    private static Utilizador utilizadorLogado;

    public static void setUtilizador(Utilizador u) {
        utilizadorLogado = u;
    }

    public static Utilizador getUtilizador() {
        return utilizadorLogado;
    }
}
