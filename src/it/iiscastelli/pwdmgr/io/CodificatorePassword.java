package it.iiscastelli.pwdmgr.io;

import it.iiscastelli.pwdmgr.model.Credenziali;

public class CodificatorePassword {
    
    private static String calcolaChiave(String sitoApp, String usernameEmail) {
        String chiave = "";
        int i = 0;
        int j = 0;
        while(i < sitoApp.length() && j < usernameEmail.length()) {
            chiave += sitoApp.charAt(i);
            chiave += usernameEmail.charAt(j);
            i++;
            j++;
        }
        if(i < sitoApp.length()) {
            chiave += sitoApp.substring(i);
        }
        if(j < usernameEmail.length()) {
            chiave += usernameEmail.substring(j);
        }
        return chiave;
    }
    
    private static String codificaDecodifica(String stringa, String chiave) {
        String risultato = "";
        int lunghezzaChiave = chiave.length();
        for(int i = 0; i < stringa.length(); i++) {
            char caratterePassword = stringa.charAt(i);
            char carattereChiave = chiave.charAt(i % lunghezzaChiave);
            char carattereCodificato = (char) (caratterePassword ^ carattereChiave);
            risultato += carattereCodificato;
        }
        return risultato;
    }
    
    public static String codifica(Credenziali credenziali) {
        String chiave = calcolaChiave(credenziali.getSitoApp(), credenziali.getUsernameEmail());
        return codificaDecodifica(credenziali.getPassword(), chiave);
    }
    
    public static String decodifica(String sitoApp, String usernameEmail, String passwordCodificata) {
        String chiave = calcolaChiave(sitoApp, usernameEmail);
        return codificaDecodifica(passwordCodificata, chiave);
    }
}
