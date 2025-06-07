package it.iiscastelli.pwdmgr.ui;

import com.formdev.flatlaf.FlatLightLaf;
import it.iiscastelli.pwdmgr.io.CaricamentoDatiListener;
import it.iiscastelli.pwdmgr.io.FileCredenziali;
import it.iiscastelli.pwdmgr.io.ThreadCaricatoreDati;
import it.iiscastelli.pwdmgr.model.Credenziali;
import it.iiscastelli.pwdmgr.ui.model.CredenzialiTableModel;
import it.iiscastelli.pwdmgr.util.GestoreMessaggi;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FramePrincipale extends javax.swing.JFrame implements ListSelectionListener, CaricamentoDatiListener {
    
    private static final int INDICE_SITO_APP = 0;
    private static final int INDICE_USERNAME_EMAIL = 1;
    private static final int INDICE_PASSWORD = 2;

    private List<Credenziali> elencoCredenziali;
    private DefaultTableModel model;
    private int indiceRigaSelezionata;
    private FileCredenziali file;
    
    /**
     * Creates new form FramePrincipale
     */
    public FramePrincipale() {
        initComponents();
        inizializza();
    }
    
    private void inizializza() {
        elencoCredenziali = new ArrayList<>();
        model = new CredenzialiTableModel();
        tblElencoCredenziali.setModel(model);
        ListSelectionModel selectionModel = tblElencoCredenziali.getSelectionModel();
        selectionModel.addListSelectionListener(this);
        indiceRigaSelezionata = -1;
        aggiornaStatoPulsanti();
        try {
            file = new FileCredenziali();
        } catch (IOException ex) {}
    }
    
    private void apriDialogNuoveCredenziali() {
        DialogNuoveCredenziali dialog = new DialogNuoveCredenziali(this, true);
        dialog.setVisible(true);
    }
    
    private void apriDialogModificaCredenziali() {
        DialogModificaCredenziali dialog = new DialogModificaCredenziali(this, true);
        Credenziali copiaCredenziali = new Credenziali(elencoCredenziali.get(indiceRigaSelezionata));
        dialog.setCredenziali(copiaCredenziali);
        dialog.setVisible(true);
    }
    
    private void apriDialogModificaPassword() {
        DialogModificaPassword dialog = new DialogModificaPassword(this, true);
        Credenziali copiaCredenziali = new Credenziali(elencoCredenziali.get(indiceRigaSelezionata));
        dialog.setCredenziali(copiaCredenziali);
        dialog.setVisible(true);
    }
    
    private void apriDialogGeneraPassword() {
        DialogGeneraPassword dialog = new DialogGeneraPassword(this, true);
        dialog.setVisible(true);
    }
    
    private void apriDialogValutaComplessita() {
        DialogValutaComplessita dialog = new DialogValutaComplessita(this, true);
        dialog.setVisible(true);
    }
    
    public void aggiungiCredenziali(Credenziali credenziali) {
        try {
            file.aggiungiRecordCredenziali(credenziali);
        } catch (IOException ex) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante il salvataggio delle credenziali");
            return;
        }
        elencoCredenziali.add(credenziali);
        model.addRow(credenziali.toTableRow());
        GestoreMessaggi.mostraInformazione(this, "Credenziali salvate con successo");
    }
    
    public void modificaCredenziali(Credenziali credenziali) {
        try {
            file.modificaCampiCredenziali(credenziali);
        } catch (IOException ex) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante la modifica delle credenziali");
            return;
        }
        Credenziali credenzialiSelezionate = elencoCredenziali.get(indiceRigaSelezionata);
        credenzialiSelezionate.setSitoApp(credenziali.getSitoApp());
        credenzialiSelezionate.setUsernameEmail(credenziali.getUsernameEmail());
        model.setValueAt(credenzialiSelezionate.getSitoApp(), indiceRigaSelezionata, INDICE_SITO_APP);
        model.setValueAt(credenzialiSelezionate.getUsernameEmail(), indiceRigaSelezionata, INDICE_USERNAME_EMAIL);
        GestoreMessaggi.mostraInformazione(this, "Credenziali modificate con successo");
    }
    
    public void modificaPassword(Credenziali credenziali) {
        try {
            file.modificaCampoPassword(credenziali);
        } catch (IOException ex) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante la modifica della password");
            return;
        }
        Credenziali credenzialiSelezionate = elencoCredenziali.get(indiceRigaSelezionata);
        credenzialiSelezionate.setPassword(credenziali.getPassword());
        model.setValueAt(credenzialiSelezionate.getPasswordMascherata(), indiceRigaSelezionata, INDICE_PASSWORD);
        GestoreMessaggi.mostraInformazione(this, "Password modificata con successo");
    }
    
    public void eliminaCredenziali() {
        Credenziali credenzialiSelezionate = elencoCredenziali.get(indiceRigaSelezionata);
        try {
            file.eliminaRecordCredenziali(credenzialiSelezionate.getId());
        } catch (IOException ex) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante l'eliminazione delle credenziali");
            return;
        }
        elencoCredenziali.remove(credenzialiSelezionate);
        model.removeRow(indiceRigaSelezionata);
        indiceRigaSelezionata = -1;
        aggiornaStatoPulsanti();
        GestoreMessaggi.mostraInformazione(this, "Credenziali eliminate con successo");
    }
    
    private void aggiornaStatoPulsanti() {
        btnModificaCredenziali.setEnabled(indiceRigaSelezionata >= 0);
        miCredenziali.setEnabled(indiceRigaSelezionata >= 0);
        btnModificaPassword.setEnabled(indiceRigaSelezionata >= 0);
        miPassword.setEnabled(indiceRigaSelezionata >= 0);
        btnElimina.setEnabled(indiceRigaSelezionata >= 0);
        btnCopiaPassword.setEnabled(indiceRigaSelezionata >= 0);
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        indiceRigaSelezionata = tblElencoCredenziali.getSelectedRow();
        aggiornaStatoPulsanti();
    }
    
    @Override
    public void caricamentoCompletato(List<Credenziali> elencoCredenziali) {
        if(elencoCredenziali == null) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante il caricamento delle credenziali");
            return;
        }
        this.elencoCredenziali = elencoCredenziali;
        for(Credenziali credenziali : elencoCredenziali) {
            model.addRow(credenziali.toTableRow());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNuoveCredenziali = new javax.swing.JButton();
        _scrollPane = new javax.swing.JScrollPane();
        tblElencoCredenziali = new javax.swing.JTable();
        btnModificaCredenziali = new javax.swing.JButton();
        btnModificaPassword = new javax.swing.JButton();
        btnElimina = new javax.swing.JButton();
        btnCopiaPassword = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        miNuoveCredenziali = new javax.swing.JMenuItem();
        _menuFileSeparatore = new javax.swing.JPopupMenu.Separator();
        miChiudi = new javax.swing.JMenuItem();
        menuModifica = new javax.swing.JMenu();
        miCredenziali = new javax.swing.JMenuItem();
        miPassword = new javax.swing.JMenuItem();
        menuPassword = new javax.swing.JMenu();
        miGeneraCasuale = new javax.swing.JMenuItem();
        miValutaComplessita = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestore password");
        setPreferredSize(new java.awt.Dimension(600, 400));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnNuoveCredenziali.setBackground(new java.awt.Color(13, 110, 253));
        btnNuoveCredenziali.setForeground(new java.awt.Color(255, 255, 255));
        btnNuoveCredenziali.setText("Nuove credenziali");
        btnNuoveCredenziali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuoveCredenziali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuoveCredenzialiActionPerformed(evt);
            }
        });

        tblElencoCredenziali.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sito / App", "Username / Email", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblElencoCredenziali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblElencoCredenziali.getTableHeader().setReorderingAllowed(false);
        _scrollPane.setViewportView(tblElencoCredenziali);
        tblElencoCredenziali.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblElencoCredenziali.getColumnModel().getColumnCount() > 0) {
            tblElencoCredenziali.getColumnModel().getColumn(0).setResizable(false);
            tblElencoCredenziali.getColumnModel().getColumn(1).setResizable(false);
        }

        btnModificaCredenziali.setBackground(new java.awt.Color(255, 193, 7));
        btnModificaCredenziali.setText("Modifica credenziali");
        btnModificaCredenziali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificaCredenziali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificaCredenzialiActionPerformed(evt);
            }
        });

        btnModificaPassword.setBackground(new java.awt.Color(255, 193, 7));
        btnModificaPassword.setText("Modifica password");
        btnModificaPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificaPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificaPasswordActionPerformed(evt);
            }
        });

        btnElimina.setBackground(new java.awt.Color(220, 53, 69));
        btnElimina.setForeground(new java.awt.Color(255, 255, 255));
        btnElimina.setText("Elimina");
        btnElimina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaActionPerformed(evt);
            }
        });

        btnCopiaPassword.setBackground(new java.awt.Color(13, 110, 253));
        btnCopiaPassword.setForeground(new java.awt.Color(255, 255, 255));
        btnCopiaPassword.setText("Copia password negli appunti");
        btnCopiaPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCopiaPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiaPasswordActionPerformed(evt);
            }
        });

        menuFile.setText("File");

        miNuoveCredenziali.setText("Nuove credenziali");
        miNuoveCredenziali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuoveCredenzialiActionPerformed(evt);
            }
        });
        menuFile.add(miNuoveCredenziali);
        menuFile.add(_menuFileSeparatore);

        miChiudi.setText("Chiudi");
        miChiudi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miChiudiActionPerformed(evt);
            }
        });
        menuFile.add(miChiudi);

        menuBar.add(menuFile);

        menuModifica.setText("Modifica");

        miCredenziali.setText("Credenziali");
        miCredenziali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCredenzialiActionPerformed(evt);
            }
        });
        menuModifica.add(miCredenziali);

        miPassword.setText("Password");
        miPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPasswordActionPerformed(evt);
            }
        });
        menuModifica.add(miPassword);

        menuBar.add(menuModifica);

        menuPassword.setText("Strumenti");

        miGeneraCasuale.setText("Genera password casuale");
        miGeneraCasuale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGeneraCasualeActionPerformed(evt);
            }
        });
        menuPassword.add(miGeneraCasuale);

        miValutaComplessita.setText("Valuta complessità password");
        miValutaComplessita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miValutaComplessitaActionPerformed(evt);
            }
        });
        menuPassword.add(miValutaComplessita);

        menuBar.add(menuPassword);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(_scrollPane))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
                        .addComponent(btnModificaCredenziali)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificaPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnElimina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCopiaPassword))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuoveCredenziali)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuoveCredenziali)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificaCredenziali, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificaPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnElimina)
                    .addComponent(btnCopiaPassword))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void miChiudiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miChiudiActionPerformed
        dispose();
    }//GEN-LAST:event_miChiudiActionPerformed

    private void miNuoveCredenzialiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuoveCredenzialiActionPerformed
        apriDialogNuoveCredenziali();
    }//GEN-LAST:event_miNuoveCredenzialiActionPerformed

    private void btnNuoveCredenzialiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuoveCredenzialiActionPerformed
        apriDialogNuoveCredenziali();
    }//GEN-LAST:event_btnNuoveCredenzialiActionPerformed

    private void btnModificaCredenzialiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificaCredenzialiActionPerformed
        apriDialogModificaCredenziali();
    }//GEN-LAST:event_btnModificaCredenzialiActionPerformed

    private void miCredenzialiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCredenzialiActionPerformed
        apriDialogModificaCredenziali();
    }//GEN-LAST:event_miCredenzialiActionPerformed

    private void btnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaActionPerformed
        eliminaCredenziali();
    }//GEN-LAST:event_btnEliminaActionPerformed

    private void btnModificaPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificaPasswordActionPerformed
        apriDialogModificaPassword();
    }//GEN-LAST:event_btnModificaPasswordActionPerformed

    private void miPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPasswordActionPerformed
        apriDialogModificaPassword();
    }//GEN-LAST:event_miPasswordActionPerformed

    private void btnCopiaPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiaPasswordActionPerformed
        Credenziali credenziali = elencoCredenziali.get(indiceRigaSelezionata);
        StringSelection stringSelection = new StringSelection(credenziali.getPassword());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        GestoreMessaggi.mostraInformazione(this, "Password copiata negli appunti");
    }//GEN-LAST:event_btnCopiaPasswordActionPerformed

    private void miGeneraCasualeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGeneraCasualeActionPerformed
        apriDialogGeneraPassword();
    }//GEN-LAST:event_miGeneraCasualeActionPerformed

    private void miValutaComplessitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miValutaComplessitaActionPerformed
        apriDialogValutaComplessita();
    }//GEN-LAST:event_miValutaComplessitaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            file.chiudi();
        } catch (IOException ex) {
            GestoreMessaggi.mostraErrore(this, "Si è verificato un errore durante la chiusura dell'applicazione");
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ThreadCaricatoreDati thread = new ThreadCaricatoreDati(file, this);
        thread.start();
    }//GEN-LAST:event_formWindowOpened
    
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
            UIManager.setLookAndFeel(FlatLightLaf.class.getName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FramePrincipale().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator _menuFileSeparatore;
    private javax.swing.JScrollPane _scrollPane;
    private javax.swing.JButton btnCopiaPassword;
    private javax.swing.JButton btnElimina;
    private javax.swing.JButton btnModificaCredenziali;
    private javax.swing.JButton btnModificaPassword;
    private javax.swing.JButton btnNuoveCredenziali;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuModifica;
    private javax.swing.JMenu menuPassword;
    private javax.swing.JMenuItem miChiudi;
    private javax.swing.JMenuItem miCredenziali;
    private javax.swing.JMenuItem miGeneraCasuale;
    private javax.swing.JMenuItem miNuoveCredenziali;
    private javax.swing.JMenuItem miPassword;
    private javax.swing.JMenuItem miValutaComplessita;
    private javax.swing.JTable tblElencoCredenziali;
    // End of variables declaration//GEN-END:variables

}
