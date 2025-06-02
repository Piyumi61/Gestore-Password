package it.iiscastelli.pwdmgr.model;

public class Credenziali {
    
    private String sitoApp;
    private String usernameEmail;
    private String password;
    
    public Credenziali(String sitoApp, String usernameEmail, String password) {
        this.sitoApp = sitoApp;
        this.usernameEmail = usernameEmail;
        this.password = password;
    }
    
    public Credenziali(String sitoApp, String usernameEmail) {
        this(sitoApp, usernameEmail, null);
    }
    
    public Credenziali() {
        this(null, null);
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
