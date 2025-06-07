package it.iiscastelli.pwdmgr.util;

import java.awt.Component;
import javax.swing.JOptionPane;

public class GestoreMessaggi {
    
    public static void mostraErrore(Component padre, String messaggio) {
        JOptionPane.showMessageDialog(padre, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void mostraInformazione(Component padre, String messaggio) {
        JOptionPane.showMessageDialog(padre, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }
}
