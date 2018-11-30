package com.hl.gui.data;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.text.JTextComponent;

import com.hl.gui.HomeLibrary;

public abstract class HomeLibraryProductDialog extends JDialog implements ProductDialog {

    protected boolean isUpdating = false;
    
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 7333418308467673805L;
    private ArrayList<JTextComponent> mandatoryFields = new ArrayList<>();
    protected ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };

    public HomeLibraryProductDialog(Frame parent) {
        super(parent, "Submit Product", true);
    }

    public HomeLibraryProductDialog(Frame parent, Object data) {
        this(parent);
    }

    public HomeLibraryProductDialog(Dialog parent) {
        super(parent, "Submit Product", true);
    }

    public HomeLibraryProductDialog(Dialog parent, Object data) {
        this(parent);
    }

    @Override
    public void addMandatoryField(JTextComponent field) {
        mandatoryFields.add(field);
    }

    @Override
    public boolean checkMandatoryFields() {
        for (JTextComponent field : mandatoryFields) {
            String text = field.getText().trim();
            if (text.isEmpty()) {
                HomeLibrary.showSubmitErrorMessageBox(this, HomeLibrary.MANDATORY_FIELD_MSG);
                return false;
            }
        }
        return true;
    }

}
