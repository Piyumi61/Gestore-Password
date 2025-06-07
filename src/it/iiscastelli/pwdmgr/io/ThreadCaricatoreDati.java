package it.iiscastelli.pwdmgr.io;

import it.iiscastelli.pwdmgr.model.Credenziali;
import java.io.IOException;
import java.util.List;

public class ThreadCaricatoreDati extends Thread {

    private FileCredenziali file;
    private CaricamentoDatiListener listener;

    public ThreadCaricatoreDati(FileCredenziali file, CaricamentoDatiListener listener) {
        super();
        this.file = file;
        this.listener = listener;
    }
    
    @Override
    public void run() {
        List<Credenziali> elencoCredenziali;
        try {
            elencoCredenziali = file.leggiCredenziali();
        } catch(IOException e) {
            elencoCredenziali = null;
        }
        listener.caricamentoCompletato(elencoCredenziali);
    }
}
