package it.iiscastelli.pwdmgr.ui;

import it.iiscastelli.pwdmgr.config.Colore;
import it.iiscastelli.pwdmgr.config.Costanti;
import it.iiscastelli.pwdmgr.model.GeneratorePassword;
import it.iiscastelli.pwdmgr.model.ValutatoreComplessita;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DialogGeneraPassword extends javax.swing.JDialog {

    private static final int LUNGHEZZA_MIN = Costanti.LUNGHEZZA_MIN;
    private static final int LUNGHEZZA_MAX = Costanti.LUNGHEZZA_MAX;
    private static final int LUNGHEZZA_INIZIALE = Costanti.LUNGHEZZA_INIZIALE;
    
    private static final String MINUSCOLE_ACTION_COMMAND = "Minuscole";
    private static final String MAIUSCOLE_ACTION_COMMAND = "Maiuscole";
    private static final String CIFRE_ACTION_COMMAND = "Cifre";
    private static final String SIMBOLI_ACTION_COMMAND = "Simboli";
    
    private static final int SOGLIA_MOLTO_DEBOLE = ValutatoreComplessita.SOGLIA_MOLTO_DEBOLE;
    private static final int SOGLIA_DEBOLE = ValutatoreComplessita.SOGLIA_DEBOLE;
    private static final int SOGLIA_BUONA = ValutatoreComplessita.SOGLIA_BUONA;
    private static final int SOGLIA_MOLTO_BUONA = ValutatoreComplessita.SOGLIA_MOLTO_BUONA;
    private static final int SOGLIA_FORTE = ValutatoreComplessita.SOGLIA_FORTE;
    
    private static final String TESTO_MOLTO_DEBOLE = ValutatoreComplessita.TESTO_MOLTO_DEBOLE;
    private static final String TESTO_DEBOLE = ValutatoreComplessita.TESTO_DEBOLE;
    private static final String TESTO_BUONA = ValutatoreComplessita.TESTO_BUONA;
    private static final String TESTO_MOLTO_BUONA = ValutatoreComplessita.TESTO_MOLTO_BUONA;
    private static final String TESTO_FORTE = ValutatoreComplessita.TESTO_FORTE;
    private static final String TESTO_MOLTO_FORTE = ValutatoreComplessita.TESTO_MOLTO_FORTE;
    
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
    public DialogGeneraPassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inizializza();
    }
    
    private void inizializza() {
        lunghezza = LUNGHEZZA_INIZIALE;
        minuscole = true;
        maiuscole = true;
        cifre = true;
        simboli = true;
        password = "";
        lunghezzaStr = String.valueOf(lunghezza);
        txtLunghezza.setText(lunghezzaStr);
        generatore = new GeneratorePassword(lunghezza, maiuscole, minuscole, cifre, simboli);
        valutatore = new ValutatoreComplessita(password);
        pbComplessita.setValue(0);
        lblComplessita.setVisible(false);
        sldLunghezza.setMinimum(LUNGHEZZA_MIN);
        sldLunghezza.setMaximum(LUNGHEZZA_MAX);
        sldLunghezza.setValue(LUNGHEZZA_INIZIALE);
        pack();
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
        if(valore <= SOGLIA_MOLTO_DEBOLE) {
            pbComplessita.setForeground(Colore.RED);
        } else if(valore <= SOGLIA_DEBOLE) {
            pbComplessita.setForeground(Colore.ORANGE);
        } else if(valore <= SOGLIA_BUONA) {
            pbComplessita.setForeground(Colore.YELLOW);
        } else if(valore <= SOGLIA_MOLTO_BUONA) {
            pbComplessita.setForeground(Colore.GREEN);
        } else if(valore <= SOGLIA_FORTE) {
            pbComplessita.setForeground(Colore.TEAL);
        } else {
            pbComplessita.setForeground(Colore.CYAN);
        }
    }
    
    private void aggiornaTestoComplessita() {
        int valore = pbComplessita.getValue();
        lblComplessita.setVisible(!password.isEmpty());
        if(valore <= SOGLIA_MOLTO_DEBOLE) {
            lblComplessita.setText(TESTO_MOLTO_DEBOLE);
        } else if(valore <= SOGLIA_DEBOLE) {
            lblComplessita.setText(TESTO_DEBOLE);
        } else if(valore <= SOGLIA_BUONA) {
            lblComplessita.setText(TESTO_BUONA);
        } else if(valore <= SOGLIA_MOLTO_BUONA) {
            lblComplessita.setText(TESTO_MOLTO_BUONA);
        } else if(valore <= SOGLIA_FORTE) {
            lblComplessita.setText(TESTO_FORTE);
        } else {
            lblComplessita.setText(TESTO_MOLTO_FORTE);
        }
    }
    
    private void aggiornaLunghezza() {
        int nuovaLunghezza = sldLunghezza.getValue();
        if(nuovaLunghezza == lunghezza) {
            return;
        }
        lunghezza = nuovaLunghezza;
        lunghezzaStr = String.valueOf(lunghezza);
        txtLunghezza.setText(lunghezzaStr);
        generaPassword();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnCopia = new javax.swing.JButton();
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
        btnChiudi = new javax.swing.JButton();
        btnGenera = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generazione password casuale");
        setResizable(false);

        _lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblPassword.setText("Password");

        txtPassword.setToolTipText("");
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        btnCopia.setBackground(new java.awt.Color(13, 110, 253));
        btnCopia.setForeground(new java.awt.Color(255, 255, 255));
        btnCopia.setText("Copia negli appunti");
        btnCopia.setBorderPainted(false);
        btnCopia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCopia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiaActionPerformed(evt);
            }
        });

        _lblComplessita.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblComplessita.setText("Complessità");

        pbComplessita.setForeground(new java.awt.Color(25, 135, 84));
        pbComplessita.setValue(50);

        lblComplessita.setText("Normale");

        txtLunghezza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLunghezza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLunghezzaKeyReleased(evt);
            }
        });

        _lblLunghezza.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblLunghezza.setText("Lunghezza");

        sldLunghezza.setForeground(new java.awt.Color(13, 110, 253));
        sldLunghezza.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sldLunghezza.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sldLunghezzaMouseDragged(evt);
            }
        });
        sldLunghezza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sldLunghezzaMouseClicked(evt);
            }
        });

        _lblSceltaCaratteri.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        _lblSceltaCaratteri.setText("Scelta caratteri");

        chkMaiuscole.setSelected(true);
        chkMaiuscole.setText("Maiuscole");
        chkMaiuscole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkCifre.setSelected(true);
        chkCifre.setText("Cifre");
        chkCifre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkMinuscole.setSelected(true);
        chkMinuscole.setText("Minuscole");
        chkMinuscole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSceltaCaratteriActionPerformed(evt);
            }
        });

        chkSimboli.setSelected(true);
        chkSimboli.setText("Simboli");
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

        btnChiudi.setBackground(new java.awt.Color(108, 117, 125));
        btnChiudi.setForeground(new java.awt.Color(255, 255, 255));
        btnChiudi.setText("Chiudi");
        btnChiudi.setBorderPainted(false);
        btnChiudi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChiudi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiudiActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenera)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCopia))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_lblComplessita)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pbComplessita, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblComplessita))
                            .addComponent(pnlConfigurazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnChiudi)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenera)
                    .addComponent(btnCopia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(_lblComplessita)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbComplessita, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComplessita))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlConfigurazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChiudi)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sldLunghezzaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldLunghezzaMouseDragged
        aggiornaLunghezza();
    }//GEN-LAST:event_sldLunghezzaMouseDragged

    private void chkSceltaCaratteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSceltaCaratteriActionPerformed
        switch(evt.getActionCommand()) {
            case MINUSCOLE_ACTION_COMMAND:
                minuscole = chkMinuscole.isSelected();
                break;
            case MAIUSCOLE_ACTION_COMMAND:
                maiuscole = chkMaiuscole.isSelected();
                break;
            case CIFRE_ACTION_COMMAND:
                cifre = chkCifre.isSelected();
                break;
            case SIMBOLI_ACTION_COMMAND:
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
        if(txtLunghezza.getText().isBlank()) {
            evt.consume();
            return;
        }
        try {
            lunghezza = Integer.parseInt(txtLunghezza.getText());
        } catch (NumberFormatException ex) {
            txtLunghezza.setText(lunghezzaStr);
            return;
        }
        if(lunghezza < LUNGHEZZA_MIN || lunghezza > LUNGHEZZA_MAX) {
            txtLunghezza.setText(lunghezzaStr);
            return;
        }
        sldLunghezza.setValue(lunghezza);
        lunghezzaStr = txtLunghezza.getText();
        generaPassword();
    }//GEN-LAST:event_txtLunghezzaKeyReleased

    private void btnGeneraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneraActionPerformed
        generaPassword();
    }//GEN-LAST:event_btnGeneraActionPerformed

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        password = txtPassword.getText();
        valutatore.setPassword(password);
        valutaComplessita();
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void btnChiudiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiudiActionPerformed
        dispose();
    }//GEN-LAST:event_btnChiudiActionPerformed

    private void sldLunghezzaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldLunghezzaMouseClicked
        aggiornaLunghezza();
    }//GEN-LAST:event_sldLunghezzaMouseClicked

    private void btnCopiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiaActionPerformed
        StringSelection stringSelection = new StringSelection(password);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_btnCopiaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _lblComplessita;
    private javax.swing.JLabel _lblLunghezza;
    private javax.swing.JLabel _lblPassword;
    private javax.swing.JLabel _lblSceltaCaratteri;
    private javax.swing.JButton btnChiudi;
    private javax.swing.JButton btnCopia;
    private javax.swing.JButton btnGenera;
    private javax.swing.JCheckBox chkCifre;
    private javax.swing.JCheckBox chkMaiuscole;
    private javax.swing.JCheckBox chkMinuscole;
    private javax.swing.JCheckBox chkSimboli;
    private javax.swing.JLabel lblComplessita;
    private javax.swing.JProgressBar pbComplessita;
    private javax.swing.JPanel pnlConfigurazione;
    private javax.swing.JSlider sldLunghezza;
    private javax.swing.JTextField txtLunghezza;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
