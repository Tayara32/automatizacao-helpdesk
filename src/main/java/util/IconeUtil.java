package util;

import javax.swing.*;

public class IconeUtil extends JFrame {

    public static void aplicarIcone(JFrame frame) {
        ImageIcon icon = new ImageIcon(IconeUtil.class.getResource("/images/icone.png"));
        frame.setIconImage(icon.getImage());
    }
}