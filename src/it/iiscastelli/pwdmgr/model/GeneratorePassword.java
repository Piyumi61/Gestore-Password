package it.iiscastelli.pwdmgr.model;

import it.iiscastelli.pwdmgr.config.Costanti;
import java.util.Random;

public class GeneratorePassword {
    
    private static final String MAIUSCOLE = Costanti.MAIUSCOLE;
    private static final String MINUSCOLE = Costanti.MINUSCOLE;
    private static final String CIFRE = Costanti.CIFRE;
    private static final String SIMBOLI = Costanti.SIMBOLI;
    
    private static final int N_MAIUSCOLE = MAIUSCOLE.length();
    private static final int N_MINUSCOLE = MINUSCOLE.length();
    private static final int N_CIFRE = CIFRE.length();
    private static final int N_SIMBOLI = SIMBOLI.length();
    
    private int lunghezza;
    private boolean maiuscole;
    private boolean minuscole;
    private boolean cifre;
    private boolean simboli;
    private final Random random;

    public GeneratorePassword(int lunghezza, boolean maiuscole, boolean minuscole, boolean cifre, boolean simboli) {
        this.lunghezza = lunghezza;
        this.maiuscole = maiuscole;
        this.minuscole = minuscole;
        this.cifre = cifre;
        this.simboli = simboli;
        random = new Random();
    }

    public int getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public boolean isMaiuscole() {
        return maiuscole;
    }

    public void setMaiuscole(boolean maiuscole) {
        this.maiuscole = maiuscole;
    }

    public boolean isMinuscole() {
        return minuscole;
    }

    public void setMinuscole(boolean minuscole) {
        this.minuscole = minuscole;
    }

    public boolean isCifre() {
        return cifre;
    }

    public void setCifre(boolean cifre) {
        this.cifre = cifre;
    }

    public boolean isSimboli() {
        return simboli;
    }

    public void setSimboli(boolean simboli) {
        this.simboli = simboli;
    }
    
    public String genera() {
        String password = "";
        String caratteri = "";
        int caratteriRimanenti = lunghezza;
        if(maiuscole) {
            password += MAIUSCOLE.charAt(random.nextInt(N_MAIUSCOLE));
            caratteri += MAIUSCOLE;
            caratteriRimanenti--;
        }
        if(minuscole) {
            password += MINUSCOLE.charAt(random.nextInt(N_MINUSCOLE));
            caratteri += MINUSCOLE;
            caratteriRimanenti--;
        }
        if(cifre) {
            password += CIFRE.charAt(random.nextInt(N_CIFRE));
            caratteri += CIFRE;
            caratteriRimanenti--;
        }
        if(simboli) {
            password += SIMBOLI.charAt(random.nextInt(N_SIMBOLI));
            caratteri += SIMBOLI;
            caratteriRimanenti--;
        }
        int nCaratteri = caratteri.length();
        while(caratteriRimanenti > 0) {
            password += caratteri.charAt(random.nextInt(nCaratteri));
            caratteriRimanenti--;
        }
        password = mescolaCaratteri(password);
        return password.substring(0, lunghezza);
    }
    
    private String mescolaCaratteri(String password) {
        char[] caratteri = password.toCharArray();
        for(int n = 0; n < 100 * caratteri.length; n++) {
            int i = random.nextInt(caratteri.length);
            int j = random.nextInt(caratteri.length);
            char temp = caratteri[i];
            caratteri[i] = caratteri[j];
            caratteri[j] = temp;
        }
        return new String(caratteri);
    }
}
