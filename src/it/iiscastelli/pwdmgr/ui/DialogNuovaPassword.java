package it.iiscastelli.pwdmgr.ui;

import it.iiscastelli.pwdmgr.config.Colore;
import it.iiscastelli.pwdmgr.model.Credenziali;
import it.iiscastelli.pwdmgr.model.GeneratorePassword;
import it.iiscastelli.pwdmgr.model.ValutatoreComplessita;
import it.iiscastelli.pwdmgr.util.GestoreMessaggi;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DialogNuovaPassword extends javax.swing.JDialog {

    private FramePrincipale padre;
    private Credenziali credenziali;
    private int lunghezza;
    private boolean maiuscole;
    private boolean minuscole;
    private boolean cifre;
    private boolean simboli;
    private String lunghezzaStr;
    private GeneratorePassword generatore;
    private ValutatoreComplessita valutatore;
    private String password;
    
    /**
     * Creates new form DialogNuovaPassword
     */
    public DialogNuovaPassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        padre = (FramePrincipale) parent;
        inizializza();
    }
    
    private void inizializza() {
        lunghezza = 6;
        minuscole = true;
        maiuscole = true;
        cifre = true;
        simboli = true;
        password = "";
        lunghezzaStr = String.valueOf(lunghezza);
        generatore = new GeneratorePassword(lunghezza, maiuscole, minuscole, cifre, simboli);
        valutatore = new ValutatoreComplessita(password);
        btnGenera.setVisible(false);
        pnlConfigurazione.setVisible(false);
        pbComplessita.setValue(0);
        lblComplessita.setVisible(false);
        nascondiPassword();
        pack();
    }

    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
    }
    
    private void mostraPassword() {
        pnlPwdPassword.setVisible(false);
        pnlTxtPassword.setVisible(true);
    }
    
    private void nascondiPassword() {
        pnlPwdPassword.setVisible(true);
        pnlTxtPassword.setVisible(false);
    }
    
    private int contaCheckboxSelezionate() {
        int contatore = 0;
        if(minuscole) {
            contatore++;
        }
        if(maiuscole) {
            contatore++;
        }
        if(cifre) {
            contatore++;
        }
        if(simboli) {
            contatore++;
        }
        return contatore;
    }
    
    private void disabilitaUltimaCheckbox() {
        if(chkMinuscole.isSelected()) {
            chkMinuscole.setEnabled(false);
        }
        if(chkMaiuscole.isSelected()) {
            chkMaiuscole.setEnabled(false);
        }
        if(chkCifre.isSelected()) {
            chkCifre.setEnabled(false);
        }
        if(chkSimboli.isSelected()) {
            chkSimboli.setEnabled(false);
        }
    }
    
    private void riabilitaCheckbox() {
        chkMinuscole.setEnabled(true);
        chkMaiuscole.setEnabled(true);
        chkCifre.setEnabled(true);
        chkSimboli.setEnabled(true);
    }
    
    private void generaPassword() {
        generatore.setLunghezza(lunghezza);
        generatore.setMaiuscole(maiuscole);
        generatore.setMinuscole(minuscole);
        generatore.setCifre(cifre);
        generatore.setSimboli(simboli);
        password = generatore.genera();
        txtPassword.setText(password);
        valutatore.setPassword(password);
        valutaComplessita();
    }
    
    private void valutaComplessita() {
        int punteggio = valutatore.valuta();
        pbComplessita.setValue(punteggio);
        aggiornaColoreProgressBar();
        aggiornaTestoComplessita();
    }
    
    private void aggiornaColoreProgressBar() {
        int valore = pbComplessita.getValue();
        if(valore < 20) {
            pbComplessita.setForeground(Colore.RED);
        } else if(valore < 40) {
            pbComplessita.setForeground(Colore.ORANGE);
        } else if(valore < 60) {
            pbComplessita.setForeground(Colore.YELLOW);
        } else if(valore < 80) {
            pbComplessita.setForeground(Colore.GREEN);
        } else if(valore < 100) {
            pbComplessita.setForeground(Colore.TEAL);
        } else {
            pbComplessita.setForeground(Colore.CYAN);
        }
    }
    
    private void aggiornaTestoComplessita() {
        int valore = pbComplessita.getValue();
        lblComplessita.setVisible(!password.isEmpty());
        if(valore < 20) {
            lblComplessita.setText("Molto debole");
        } else if(valore < 40) {
            lblComplessita.setText("Debole");
        } else if(valore < 60) {
            lblComplessita.setText("Abbastanza buona");
        } else if(valore < 80) {
            lblComplessita.setText("Buona");
        } else if(valore < 100) {
            lblComplessita.setText("Forte");
        } else {
            lblComplessita.setText("Molto forte");
        }
        pack();
    }
    
    private boolean controllaCampi() {
        return !password.isBlank();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpTipologia = new javax.swing.ButtonGroup();
        _lblTipologia = new javax.swing.JLabel();
        btnManuale = new javax.swing.JRadioButton();
        btnCasuale = new javax.swing.JRadioButton();
        _lblPassword = new javax.swing.JLabel();
        pnlPassword = new javax.swing.JPanel();
        pnlPwdPassword = new javax.swing.JPanel();
        pwdPassword = new javax.swing.JPasswordField();
        btnMostra = new javax.swing.JButton();
        pnlTxtPassword = new javax.swing.JPanel();
        txtPassword = new javax.swing.JTextField();
        btnNascondi = new javax.swing.JButton();
        btnGenera = new javax.swing.JButton();
        _lblComplessita = new javax.swing.JLabel();
        pbComplessita = new javax.swing.JProgressBar();
        lblComplessita = new javax.swing.JLabel();
        pnlConfigurazione = new javax.swing.JPanel();
        txtLunghezza = new javax.swing.JTextField();
        _lblLunghezza = new javax.swing.JLabel();
        sldLunghezza = new javax.swing.JSlider();
        _lblSceltaCaratteri = new javax.swing.JLabel();
        chkMaiuscole = new javax.swing.JCheckBox();
        chkCifre = new javax.swing.JCheckBox();
        chkMinuscole = new javax.swing.JCheckBox();
        chkSimboli = new javax.swing.JCheckBox();
        btnIndietro = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuova password");
        setResizable(false);

        _lblTipologia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblTipologia.setText("Tipologia");

        btngrpTipologia.add(btnManuale);
        btnManuale.setSelected(true);
        btnManuale.setText("Manuale");
        btnManuale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManuale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipologiaActionPerformed(evt);
            }
        });

        btngrpTipologia.add(btnCasuale);
        btnCasuale.setText("Casuale");
        btnCasuale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCasuale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipologiaActionPerformed(evt);
            }
        });

        _lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblPassword.setText("Password");

        pnlPassword.setLayout(new javax.swing.OverlayLayout(pnlPassword));

        pwdPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        btnMostra.setBackground(new java.awt.Color(13, 110, 253));
        btnMostra.setForeground(new java.awt.Color(255, 255, 255));
        btnMostra.setText("Mostra");
        btnMostra.setBorderPainted(false);
        btnMostra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMostra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPwdPasswordLayout = new javax.swing.GroupLayout(pnlPwdPassword);
        pnlPwdPassword.setLayout(pnlPwdPasswordLayout);
        pnlPwdPasswordLayout.setHorizontalGroup(
            pnlPwdPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPwdPasswordLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pwdPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMostra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlPwdPasswordLayout.setVerticalGroup(
            pnlPwdPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPwdPasswordLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlPwdPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwdPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostra))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlPassword.add(pnlPwdPassword);

        txtPassword.setToolTipText("");
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        btnNascondi.setBackground(new java.awt.Color(13, 110, 253));
        btnNascondi.setForeground(new java.awt.Color(255, 255, 255));
        btnNascondi.setText("Nascondi");
        btnNascondi.setBorderPainted(false);
        btnNascondi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNascondi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNascondiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTxtPasswordLayout = new javax.swing.GroupLayout(pnlTxtPassword);
        pnlTxtPassword.setLayout(pnlTxtPasswordLayout);
        pnlTxtPasswordLayout.setHorizontalGroup(
            pnlTxtPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTxtPasswordLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNascondi)
                .addGap(0, 0, 0))
        );
        pnlTxtPasswordLayout.setVerticalGroup(
            pnlTxtPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTxtPasswordLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlTxtPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNascondi))
                .addGap(0, 0, 0))
        );

        pnlPassword.add(pnlTxtPassword);

        btnGenera.setBackground(new java.awt.Color(13, 110, 253));
        btnGenera.setForeground(new java.awt.Color(255, 255, 255));
        btnGenera.setText("Genera");
        btnGenera.setBorderPainted(false);
        btnGenera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneraActionPerformed(evt);
            }
        });

        _lblComplessita.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblComplessita.setText("Complessità");

        pbComplessita.setForeground(new java.awt.Color(25, 135, 84));
        pbComplessita.setValue(50);

        lblComplessita.setText("Normale");

        txtLunghezza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLunghezza.setText("6");
        txtLunghezza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLunghezzaKeyReleased(evt);
            }
        });

        _lblLunghezza.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblLunghezza.setText("Lunghezza");

        sldLunghezza.setForeground(new java.awt.Color(13, 110, 253));
        sldLunghezza.setMaximum(30);
        sldLunghezza.setMinimum(1);
        sldLunghezza.setValue(6);
        sldLunghezza.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sldLunghezza.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sldLunghezzaMouseDragged(evt);
            }
        });

        _lblSceltaCaratteri.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblSceltaCaratteri.setText("Scelta caratteri");

        chkMaiuscole.setSelected(true);
        chkMaiuscole.setText("Maiuscole");
        chkMaiuscole.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkMaiuscole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkCifre.setSelected(true);
        chkCifre.setText("Cifre");
        chkCifre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkCifre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkMinuscole.setSelected(true);
        chkMinuscole.setText("Minuscole");
        chkMinuscole.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkMinuscole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkSimboli.setSelected(true);
        chkSimboli.setText("Simboli");
        chkSimboli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkSimboli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlConfigurazioneLayout = new javax.swing.GroupLayout(pnlConfigurazione);
        pnlConfigurazione.setLayout(pnlConfigurazioneLayout);
        pnlConfigurazioneLayout.setHorizontalGroup(
            pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfigurazioneLayout.createSequentialGroup()
                .addComponent(sldLunghezza, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLunghezza))
            .addGroup(pnlConfigurazioneLayout.createSequentialGroup()
                .addComponent(_lblLunghezza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlConfigurazioneLayout.createSequentialGroup()
                .addGroup(pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkCifre, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkMaiuscole, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_lblSceltaCaratteri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkMinuscole, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkSimboli, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlConfigurazioneLayout.setVerticalGroup(
            pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfigurazioneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(_lblLunghezza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sldLunghezza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLunghezza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(_lblSceltaCaratteri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkMaiuscole)
                    .addComponent(chkMinuscole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlConfigurazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCifre)
                    .addComponent(chkSimboli))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnIndietro.setBackground(new java.awt.Color(108, 117, 125));
        btnIndietro.setForeground(new java.awt.Color(255, 255, 255));
        btnIndietro.setText("Indietro");
        btnIndietro.setBorderPainted(false);
        btnIndietro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndietroActionPerformed(evt);
            }
        });

        btnSalva.setBackground(new java.awt.Color(25, 135, 84));
        btnSalva.setForeground(new java.awt.Color(255, 255, 255));
        btnSalva.setText("Salva");
        btnSalva.setBorderPainted(false);
        btnSalva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGenera)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnManuale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCasuale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_lblTipologia)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_lblComplessita)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pbComplessita, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblComplessita)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlConfigurazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalva)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_lblTipologia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnManuale)
                    .addComponent(btnCasuale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(_lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGenera)
                    .addComponent(pnlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(_lblComplessita)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblComplessita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbComplessita, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlConfigurazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalva)
                    .addComponent(btnIndietro))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTipologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipologiaActionPerformed
        switch(evt.getActionCommand()) {
            case "Manuale":
                btnGenera.setVisible(false);
                pnlConfigurazione.setVisible(false);
                btnNascondi.setVisible(true);
                txtPassword.setEditable(true);
                break;
            case "Casuale":
                btnGenera.setVisible(true);
                pnlConfigurazione.setVisible(true);
                mostraPassword();
                btnNascondi.setVisible(false);
                txtPassword.setEditable(false);
                generaPassword();
                break;
        }
        pack();
    }//GEN-LAST:event_btnTipologiaActionPerformed

    private void btnMostraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostraActionPerformed
        mostraPassword();
    }//GEN-LAST:event_btnMostraActionPerformed

    private void btnNascondiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNascondiActionPerformed
        nascondiPassword();
    }//GEN-LAST:event_btnNascondiActionPerformed

    private void sldLunghezzaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldLunghezzaMouseDragged
        lunghezza = sldLunghezza.getValue();
        txtLunghezza.setText(String.valueOf(lunghezza));
        generaPassword();
    }//GEN-LAST:event_sldLunghezzaMouseDragged

    private void chkSceltaCaratteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSceltaCaratteriActionPerformed
        switch(evt.getActionCommand()) {
            case "Minuscole":
                minuscole = chkMinuscole.isSelected();
                break;
            case "Maiuscole":
                maiuscole = chkMaiuscole.isSelected();
                break;
            case "Cifre":
                cifre = chkCifre.isSelected();
                break;
            case "Simboli":
                simboli = chkSimboli.isSelected();
                break;
        }
        int checkboxSelezionate = contaCheckboxSelezionate();
        if(checkboxSelezionate == 1) {
            disabilitaUltimaCheckbox();
        } else {
            riabilitaCheckbox();
        }
        generaPassword();
    }//GEN-LAST:event_chkSceltaCaratteriActionPerformed

    private void txtLunghezzaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLunghezzaKeyReleased
        try {
            lunghezza = Integer.parseInt(txtLunghezza.getText());
            sldLunghezza.setValue(lunghezza);
            lunghezzaStr = txtLunghezza.getText();
            generaPassword();
        } catch (NumberFormatException ex) {
            txtLunghezza.setText(lunghezzaStr);
        }
    }//GEN-LAST:event_txtLunghezzaKeyReleased

    private void btnGeneraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneraActionPerformed
        generaPassword();
    }//GEN-LAST:event_btnGeneraActionPerformed

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        Object source = evt.getSource();
        if(source instanceof JPasswordField) {
            password = new String(pwdPassword.getPassword());
            txtPassword.setText(password);
        } else if(source instanceof JTextField) {
            password = txtPassword.getText();
            pwdPassword.setText(password);
        }
        valutatore.setPassword(password);
        valutaComplessita();
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void btnIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndietroActionPerformed
        DialogNuoveCredenziali dialog = new DialogNuoveCredenziali(padre, true);
        dialog.setCredenziali(credenziali);
        dispose();
        dialog.setVisible(true);
    }//GEN-LAST:event_btnIndietroActionPerformed

    private void btnSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaActionPerformed
        boolean campiValorizzati = controllaCampi();
        if(!campiValorizzati) {
            GestoreMessaggi.mostraErrore(this, "Devi inserire una password");
            return;
        }
        credenziali.setPassword(password);
        padre.aggiungiCredenziali(credenziali);
        dispose();
    }//GEN-LAST:event_btnSalvaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogNuovaPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogNuovaPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogNuovaPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogNuovaPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogNuovaPassword dialog = new DialogNuovaPassword(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _lblComplessita;
    private javax.swing.JLabel _lblLunghezza;
    private javax.swing.JLabel _lblPassword;
    private javax.swing.JLabel _lblSceltaCaratteri;
    private javax.swing.JLabel _lblTipologia;
    private javax.swing.JRadioButton btnCasuale;
    private javax.swing.JButton btnGenera;
    private javax.swing.JButton btnIndietro;
    private javax.swing.JRadioButton btnManuale;
    private javax.swing.JButton btnMostra;
    private javax.swing.JButton btnNascondi;
    private javax.swing.JButton btnSalva;
    private javax.swing.ButtonGroup btngrpTipologia;
    private javax.swing.JCheckBox chkCifre;
    private javax.swing.JCheckBox chkMaiuscole;
    private javax.swing.JCheckBox chkMinuscole;
    private javax.swing.JCheckBox chkSimboli;
    private javax.swing.JLabel lblComplessita;
    private javax.swing.JProgressBar pbComplessita;
    private javax.swing.JPanel pnlConfigurazione;
    private javax.swing.JPanel pnlPassword;
    private javax.swing.JPanel pnlPwdPassword;
    private javax.swing.JPanel pnlTxtPassword;
    private javax.swing.JPasswordField pwdPassword;
    private javax.swing.JSlider sldLunghezza;
    private javax.swing.JTextField txtLunghezza;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
