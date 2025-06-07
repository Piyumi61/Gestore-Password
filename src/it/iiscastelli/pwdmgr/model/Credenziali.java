package it.iiscastelli.pwdmgr.model;

import java.util.UUID;

public class Credenziali {
    
    private UUID id;
    private String sitoApp;
    private String usernameEmail;
    private String password;

    public Credenziali(UUID id, String sitoApp, String usernameEmail, String password) {
        this.id = id;
        this.sitoApp = sitoApp;
        this.usernameEmail = usernameEmail;
        this.password = password;
    }
    
    public Credenziali(Credenziali copia) {
        this(copia.id, copia.sitoApp, copia.usernameEmail, copia.password);
    }
    
    public Credenziali(String sitoApp, String usernameEmail, String password) {
        this(UUID.randomUUID(), sitoApp, usernameEmail, password);
    }
    
    public Credenziali(String sitoApp, String usernameEmail) {
        this(sitoApp, usernameEmail, null);
    }
    
    public Credenziali() {
        this(null, null);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSitoApp() {
        return sitoApp;
    }

    public void setSitoApp(String sitoApp) {
        this.sitoApp = sitoApp;
    }

    public String getUsernameEmail() {
        return usernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.usernameEmail = usernameEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordMascherata() {
        return "*".repeat(password.length());
    }
    
    public Object[] toTableRow() {
        return new Object[] {
            sitoApp,
            usernameEmail,
            getPasswordMascherata()
        };
    }
}
