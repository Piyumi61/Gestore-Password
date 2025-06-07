package it.iiscastelli.pwdmgr.model;

import it.iiscastelli.pwdmgr.config.Costanti;

public class ValutatoreComplessita implements Costanti {
    
    private static final int LUNGHEZZA_MIN_REQUISITO = 8;
    
    public static final int SOGLIA_MOLTO_DEBOLE = 16;
    public static final int SOGLIA_DEBOLE = 33;
    public static final int SOGLIA_BUONA = 49;
    public static final int SOGLIA_MOLTO_BUONA = 66;
    public static final int SOGLIA_FORTE = 83;
    
    public static final String TESTO_MOLTO_DEBOLE = "Molto debole";
    public static final String TESTO_DEBOLE = "Debole";
    public static final String TESTO_BUONA = "Buona";
    public static final String TESTO_MOLTO_BUONA = "Molto buona";
    public static final String TESTO_FORTE = "Forte";
    public static final String TESTO_MOLTO_FORTE = "Molto forte";
    
    private String password;

    public ValutatoreComplessita(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private int contaMaiuscole() {
        int contatore = 0;
        for(char carattere : password.toCharArray()) {
            if(Character.isUpperCase(carattere)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaMinuscole() {
        int contatore = 0;
        for(char carattere : password.toCharArray()) {
            if(Character.isLowerCase(carattere)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaCifre() {
        int contatore = 0;
        for(char carattere : password.toCharArray()) {
            if(Character.isDigit(carattere)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaSimboli() {
        int contatore = 0;
        for(char carattere : password.toCharArray()) {
            if(SIMBOLI.contains(String.valueOf(carattere))) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private boolean contieneSoloMaiuscole() {
        boolean soloMaiuscole = true;
        int i = 0;
        while(i < password.length() && soloMaiuscole) {
            if(!Character.isUpperCase(password.charAt(i))) {
                soloMaiuscole = false;
            }
            i++;
        }
        return soloMaiuscole;
    }
    
    private boolean contieneSoloMinuscole() {
        boolean soloMinuscole = true;
        int i = 0;
        while(i < password.length() && soloMinuscole) {
            if(!Character.isLowerCase(password.charAt(i))) {
                soloMinuscole = false;
            }
            i++;
        }
        return soloMinuscole;
    }
    
    private boolean contieneSoloCifre() {
        boolean soloCifre = true;
        int i = 0;
        while(i < password.length() && soloCifre) {
            if(!Character.isDigit(password.charAt(i))) {
                soloCifre = false;
            }
            i++;
        }
        return soloCifre;
    }
    
    private int contaCifreSimboliIntermedi() {
        int contatore = 0;
        for(int i = 1; i < password.length() - 1; i++) {
            char carattere = password.charAt(i);
            if(Character.isDigit(carattere) || SIMBOLI.contains(String.valueOf(carattere))) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaRequisitiSoddisfatti(int numMaiuscole, int numMinuscole, int numCifre, int numSimboli) {
        int contatore = 1;
        if(numMaiuscole > 0) {
            contatore++;
        }
        if(numMinuscole > 0) {
            contatore++;
        }
        if(numCifre > 0) {
            contatore++;
        }
        if(numSimboli > 0) {
            contatore++;
        }
        return contatore;
    }
    
    private boolean contieneSoloLettere() {
        boolean soloLettere = true;
        int i = 0;
        while(i < password.length() && soloLettere) {
            if(!Character.isLetter(password.charAt(i))) {
                soloLettere = false;
            }
            i++;
        }
        return soloLettere;
    }
    
    private int malusCaratteriRipetuti() {
        float malus = 0;
        int lunghezza = password.length();
        int nCaratteriRipetuti = 0;
        int nCaratteriUnici;
        for(int i = 0; i < lunghezza; i++) {
            boolean carattereRipetuto = false;
            for(int j = 0; j < lunghezza; j++) {
                if(password.charAt(i) == password.charAt(j) && i != j) {
                    carattereRipetuto = true;
                    malus += (float) lunghezza / Math.abs(j - i);
                }
            }
            if(carattereRipetuto) {
                nCaratteriRipetuti++;
                nCaratteriUnici = lunghezza - nCaratteriRipetuti;
                if(nCaratteriUnici > 0) {
                    malus = malus / nCaratteriUnici;
                }
            }
        }
        return (int) Math.ceil(malus);
    }
    
    private int contaMaiuscoleConsecutive() {
        int contatore = 0;
        for(int i = 0; i < password.length() - 1; i++) {
            char carattereAttuale = password.charAt(i);
            char carattereSuccessivo = password.charAt(i + 1);
            if(Character.isUpperCase(carattereAttuale) && Character.isUpperCase(carattereSuccessivo)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaMinuscoleConsecutive() {
        int contatore = 0;
        for(int i = 0; i < password.length() - 1; i++) {
            char carattereAttuale = password.charAt(i);
            char carattereSuccessivo = password.charAt(i + 1);
            if(Character.isLowerCase(carattereAttuale) && Character.isLowerCase(carattereSuccessivo)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private int contaCifreConsecutive() {
        int contatore = 0;
        for(int i = 0; i < password.length() - 1; i++) {
            char carattereAttuale = password.charAt(i);
            char carattereSuccessivo = password.charAt(i + 1);
            if(Character.isDigit(carattereAttuale) && Character.isDigit(carattereSuccessivo)) {
                contatore++;
            }
        }
        return contatore;
    }
    
    private boolean sonoCaratteriSequenziali(char caratterePrecedente, char carattereAttuale, char carattereSuccessivo) {
        boolean crescente = caratterePrecedente == carattereAttuale - 1 && carattereSuccessivo == carattereAttuale + 1;
        boolean decrescente = carattereSuccessivo == carattereAttuale - 1 && caratterePrecedente == carattereAttuale + 1;
        return crescente || decrescente;
    }
    
    private int contaLettereSequenziali() {
        int contatore = 0;
        for(int i = 1; i < password.length() - 1; i++) {
            char caratterePrecedente = Character.toLowerCase(password.charAt(i - 1));
            char carattereAttuale = Character.toLowerCase(password.charAt(i));
            char carattereSuccessivo = Character.toLowerCase(password.charAt(i + 1));
            if(Character.isLetter(caratterePrecedente) && Character.isLetter(carattereAttuale) && Character.isLetter(carattereSuccessivo)) {
                if(sonoCaratteriSequenziali(caratterePrecedente, carattereAttuale, carattereSuccessivo)) {
                    contatore++;
                }
            }
        }
        return contatore;
    }
    
    private int contaCifreSequenziali() {
        int contatore = 0;
        for(int i = 1; i < password.length() - 1; i++) {
            char caratterePrecedente = password.charAt(i - 1);
            char carattereAttuale = password.charAt(i);
            char carattereSuccessivo = password.charAt(i + 1);
            if(Character.isDigit(caratterePrecedente) && Character.isDigit(carattereAttuale) && Character.isDigit(carattereSuccessivo)) {
                if(sonoCaratteriSequenziali(caratterePrecedente, carattereAttuale, carattereSuccessivo)) {
                    contatore++;
                }
            }
        }
        return contatore;
    }
    
    /*
        Preso come esempio il seguente sito
        https://www.uic.edu/apps/strong-password/
    */
    public int valuta() {
        int punteggio = 0;
        int lunghezza = password.length();
        int numMaiuscole = contaMaiuscole();
        int numMinuscole = contaMinuscole();
        int numCifre = contaCifre();
        int numSimboli = contaSimboli();
        boolean contieneSoloCifre = contieneSoloCifre();
        
        // lunghezza
        int n = lunghezza;
        punteggio += n * 4;
        
        // maiuscole
        if(!contieneSoloMaiuscole()) {
            n = numMaiuscole;
            punteggio += (lunghezza - n) * 2;
        }
        
        // minuscole
        if(!contieneSoloMinuscole()) {
            n = numMinuscole;
            punteggio += (lunghezza - n) * 2;
        }
        
        // cifre
        if(!contieneSoloCifre) {
            n = numCifre;
            punteggio += n * 4;
        }
        
        // simboli
        n = numSimboli;
        punteggio += n * 6;
        
        // cifre e simboli intermedi
        n = contaCifreSimboliIntermedi();
        punteggio += n * 2;
        
        // requisiti
        if(lunghezza >= LUNGHEZZA_MIN_REQUISITO) {
            n = contaRequisitiSoddisfatti(numMaiuscole, numMinuscole, numCifre, numSimboli);
            if(n >= 4) {
                punteggio += n * 2;
            }
        }
        
        // solo lettere
        if(contieneSoloLettere()) {
            n = lunghezza;
            punteggio -= n;
        }
        
        // solo cifre
        if(contieneSoloCifre) {
            n = lunghezza;
            punteggio -= n;
        }
        
        // caratteri ripetuti
        n = malusCaratteriRipetuti();
        punteggio -= n;
        
        // maiuscole consecutive
        n = contaMaiuscoleConsecutive();
        punteggio -= n * 2;
        
        // minuscole consecutive
        n = contaMinuscoleConsecutive();
        punteggio -= n * 2;
        
        // cifre consecutive
        n = contaCifreConsecutive();
        punteggio -= n * 2;
        
        // lettere sequenziali
        n = contaLettereSequenziali();
        punteggio -= n * 3;
        
        // cifre sequenziali
        n = contaCifreSequenziali();
        punteggio -= n * 3;
        
        if(punteggio < 0) {
            punteggio = 0;
        }
        if(punteggio > 100) {
            punteggio = 100;
        }
        return punteggio;
    }
}
