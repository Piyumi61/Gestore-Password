package it.iiscastelli.pwdmgr.io;

import it.iiscastelli.pwdmgr.model.Credenziali;
import java.util.List;

public interface CaricamentoDatiListener {
    
    void caricamentoCompletato(List<Credenziali> elencoCredenziali);
}
