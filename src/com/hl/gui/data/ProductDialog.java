package com.hl.gui.data;

import javax.swing.text.JTextComponent;

public interface ProductDialog {

    public void createGUI();
    
    public void addMandatoryField(JTextComponent field);
    
    public void populateFields(Object data);
    
    public Object parseFields();

    public Object getParsedData();
    
    public boolean checkMandatoryFields();
    
    public boolean insertToDatabase(Object data);

    public boolean updateToDatabase(Object data);

}
