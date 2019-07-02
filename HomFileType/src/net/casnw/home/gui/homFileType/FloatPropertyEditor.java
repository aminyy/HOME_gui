/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;
import org.openide.nodes.PropertyEditorRegistration;
import net.casnw.home.poolData.PoolFloat;

/**
 *
 * @author Administrator
 */

public class FloatPropertyEditor
        extends PropertyEditorSupport
        implements ExPropertyEditor, InplaceEditor.Factory {

    protected InplaceEditor ed = null;
    protected SpinnerNumberModel model;

    public FloatPropertyEditor(Object source, SpinnerNumberModel model) {
        super(source);
        this.model = model;
    }

    public FloatPropertyEditor(SpinnerNumberModel model) {
        this.model = model;
    }

    @Override
    public String getAsText() {
        Float d = (Float) getValue();
        if (d == null) {
            return "0.0";
        }
        return NumberFormat.getNumberInstance().format(d.floatValue());
    }

    @Override
    public void setAsText(String s) {
        try {
            setValue(new Float(
                    NumberFormat.getNumberInstance().parse(s).floatValue()));
        } catch (ParseException ex) {
            setValue(Float.valueOf(0.0f));
        }
    }

    @Override
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        if (ed == null) {
            ed = new FloatInplaceEditor(model);
        }
        return ed;
    }

    protected static class FloatInplaceEditor implements InplaceEditor {

        private final JSpinner spinner;
        private PropertyEditor editor = null;
        private PropertyModel model;

        public FloatInplaceEditor(SpinnerNumberModel model) {
            this.spinner = new JSpinner(model);
        }

        @Override
        public void connect(PropertyEditor propertyEditor, PropertyEnv env) {
            editor = propertyEditor;
            reset();
        }

        @Override
        public JComponent getComponent() {
            return spinner;
        }

        @Override
        public void clear() {
            //avoid memory leaks:
            editor = null;
            model = null;
        }

        @Override
        public Object getValue() {
            return spinner.getValue();
        }

        @Override
        public void setValue(Object object) {
            try {
                spinner.setValue(object);
            } catch (IllegalArgumentException e) {
                spinner.setValue(null);
            }
        }

        @Override
        public boolean supportsTextEntry() {
            return true;
        }

        @Override
        public void reset() {
            Float d = (Float) editor.getValue();
            if (d != null) {
                setValue(d);
            }
        }

        @Override
        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[0];
        }

        @Override
        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        @Override
        public PropertyModel getPropertyModel() {
            return model;
        }

        @Override
        public void setPropertyModel(PropertyModel propertyModel) {
            this.model = propertyModel;
        }

        @Override
        public boolean isKnownComponent(Component component) {
            return component == spinner || spinner.isAncestorOf(component);
        }

        @Override
        public void addActionListener(ActionListener actionListener) {
            //do nothing - not needed for this component
        }

        @Override
        public void removeActionListener(ActionListener actionListener) {
            //do nothing - not needed for this component
        }
    }
}
