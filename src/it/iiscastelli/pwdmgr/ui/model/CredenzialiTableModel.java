package it.iiscastelli.pwdmgr.ui.model;

import javax.swing.table.DefaultTableModel;

public class CredenzialiTableModel extends DefaultTableModel {
    
    public CredenzialiTableModel() {
        super();
        addColumn("Sito / App");
        addColumn("Username / Email");
        addColumn("Password");
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
