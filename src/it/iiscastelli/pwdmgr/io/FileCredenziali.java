package it.iiscastelli.pwdmgr.io;

import it.iiscastelli.pwdmgr.model.Credenziali;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileCredenziali {
    
    private static final String NOME_FILE = "credenziali.dat";
    private static final String MODALITA_RW = "rw";
    
    private static final int LUNGHEZZA_CAMPO_ID = 16;
    private static final int LUNGHEZZA_CAMPO_SITO_APP = 30;
    private static final int LUNGHEZZA_CAMPO_USERNAME_EMAIL = 30;
    private static final int LUNGHEZZA_CAMPO_PASSWORD = 30;
    private static final int LUNGHEZZA_RECORD = LUNGHEZZA_CAMPO_ID + LUNGHEZZA_CAMPO_SITO_APP + LUNGHEZZA_CAMPO_USERNAME_EMAIL + LUNGHEZZA_CAMPO_PASSWORD;
    
    private static final int PUNTATORE_NON_VALIDO = -1;
    
    private RandomAccessFile raf;

    public FileCredenziali() throws IOException {
        raf = new RandomAccessFile(NOME_FILE, MODALITA_RW);
    }
    
    public List<Credenziali> leggiCredenziali() throws IOException {
        List<Credenziali> elencoCredenziali = new ArrayList<>();
        raf.seek(0);
        while(raf.getFilePointer() < raf.length()) {
            UUID id = leggiID();
            String sitoApp = leggiStringaLunghezzaFissa(LUNGHEZZA_CAMPO_SITO_APP);
            String usernameEmail = leggiStringaLunghezzaFissa(LUNGHEZZA_CAMPO_USERNAME_EMAIL);
            String passwordCodificata = leggiStringaLunghezzaFissa(LUNGHEZZA_CAMPO_PASSWORD);
            String password = CodificatorePassword.decodifica(sitoApp, usernameEmail, passwordCodificata);
            Credenziali credenziali = new Credenziali(id, sitoApp, usernameEmail, password);
            elencoCredenziali.add(credenziali);
        }
        return elencoCredenziali;
    }
    
    public void aggiungiRecordCredenziali(Credenziali credenziali) throws IOException {
        raf.seek(raf.length());
        scriviID(credenziali.getId());
        scriviStringaLunghezzaFissa(credenziali.getSitoApp(), LUNGHEZZA_CAMPO_SITO_APP);
        scriviStringaLunghezzaFissa(credenziali.getUsernameEmail(), LUNGHEZZA_CAMPO_USERNAME_EMAIL);
        String passwordCodificata = CodificatorePassword.codifica(credenziali);
        scriviStringaLunghezzaFissa(passwordCodificata, LUNGHEZZA_CAMPO_PASSWORD);
        raf.setLength(raf.getFilePointer());
    }
    
    public void eliminaRecordCredenziali(UUID id) throws IOException {
        long puntatoreRecord = cercaRecordCredenziali(id);
        if(puntatoreRecord == PUNTATORE_NON_VALIDO) {
            throw new IOException();
        }
        raf.seek(puntatoreRecord);
        while(raf.getFilePointer() < raf.length() - LUNGHEZZA_RECORD) {
            raf.seek(raf.getFilePointer() + LUNGHEZZA_RECORD);
            byte[] uuid = new byte[LUNGHEZZA_CAMPO_ID];
            raf.read(uuid);
            byte[] sitoApp = new byte[LUNGHEZZA_CAMPO_SITO_APP];
            raf.read(sitoApp);
            byte[] usernameEmail = new byte[LUNGHEZZA_CAMPO_USERNAME_EMAIL];
            raf.read(usernameEmail);
            byte[] password = new byte[LUNGHEZZA_CAMPO_PASSWORD];
            raf.read(password);
            
            raf.seek(raf.getFilePointer() - LUNGHEZZA_RECORD);
            raf.write(uuid);
            raf.write(sitoApp);
            raf.write(usernameEmail);
            raf.write(password);
        }
        raf.setLength(raf.getFilePointer());
    }
    
    public void modificaCampiCredenziali(Credenziali credenziali) throws IOException {
        long puntatoreRecord = cercaRecordCredenziali(credenziali.getId());
        if(puntatoreRecord == PUNTATORE_NON_VALIDO) {
            throw new IOException();
        }
        raf.seek(puntatoreRecord);
        raf.skipBytes(LUNGHEZZA_CAMPO_ID);
        scriviStringaLunghezzaFissa(credenziali.getSitoApp(), LUNGHEZZA_CAMPO_SITO_APP);
        scriviStringaLunghezzaFissa(credenziali.getUsernameEmail(), LUNGHEZZA_CAMPO_USERNAME_EMAIL);
    }
    
    public void modificaCampoPassword(Credenziali credenziali) throws IOException {
        long puntatoreRecord = cercaRecordCredenziali(credenziali.getId());
        if(puntatoreRecord == PUNTATORE_NON_VALIDO) {
            throw new IOException();
        }
        raf.seek(puntatoreRecord);
        raf.skipBytes(LUNGHEZZA_CAMPO_ID + LUNGHEZZA_CAMPO_SITO_APP + LUNGHEZZA_CAMPO_USERNAME_EMAIL);
        String passwordCodificata = CodificatorePassword.codifica(credenziali);
        scriviStringaLunghezzaFissa(passwordCodificata, LUNGHEZZA_CAMPO_PASSWORD);
    }
    
    private long cercaRecordCredenziali(UUID uuid) throws IOException {
        long puntatoreRecord = PUNTATORE_NON_VALIDO;
        raf.seek(0);
        while(raf.getFilePointer() < raf.length() && puntatoreRecord == PUNTATORE_NON_VALIDO) {
            byte[] bytesFile = new byte[LUNGHEZZA_CAMPO_ID];
            raf.read(bytesFile);
            UUID uuidFile = ConvertitoreUUID.bytesToUUID(bytesFile);
            if(uuidFile.equals(uuid)) {
                puntatoreRecord = raf.getFilePointer() - LUNGHEZZA_CAMPO_ID;
            } else {
                raf.skipBytes(LUNGHEZZA_CAMPO_SITO_APP + LUNGHEZZA_CAMPO_USERNAME_EMAIL + LUNGHEZZA_CAMPO_PASSWORD);
            }
        }
        return puntatoreRecord;
    }
    
    private UUID leggiID() throws IOException {
        byte[] bytes = new byte[LUNGHEZZA_CAMPO_ID];
        raf.read(bytes);
        return ConvertitoreUUID.bytesToUUID(bytes);
    }
    
    private void scriviID(UUID uuid) throws IOException {
        byte[] bytes = ConvertitoreUUID.UUIDToBytes(uuid);
        raf.write(bytes);
    }
    
    private String leggiStringaLunghezzaFissa(int lunghezza) throws IOException {
        byte[] bytes = new byte[lunghezza];
        raf.read(bytes);
        int lunghezzaStringa = 0;
        int i = 0;
        while(i < lunghezza && bytes[i] != 0) {
            lunghezzaStringa++;
            i++;
        }
        return new String(bytes, 0, lunghezzaStringa);
    }
    
    private void scriviStringaLunghezzaFissa(String stringa, int lunghezza) throws IOException {
        byte[] bytes = new byte[lunghezza];
        byte[] bytesStringa = stringa.getBytes();
        System.arraycopy(bytesStringa, 0, bytes, 0, bytesStringa.length);
        for(int i = bytesStringa.length; i < lunghezza; i++) {
            bytes[i] = 0;
        }
        raf.write(bytes);
    }
    
    public void chiudi() throws IOException {
        raf.close();
    }
}
