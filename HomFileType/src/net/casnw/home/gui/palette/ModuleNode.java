//@DECLARE@
package net.casnw.home.gui.palette;

import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import net.casnw.home.modules.api.Module;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.meta.VariableMetaObj;
import org.dom4j.Element;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.actions.Presenter;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Luolh & minyf
 * @since 2014/4/14 
 * Define the node of Module
 *
 */
public class ModuleNode extends AbstractNode {

   private Module module = null;

    public ModuleNode(Module obj, Element element) {
        // mcf = new ModuleChildFactory();

        super(new ModuleChildren(obj, element), Lookups.singleton(obj));
        this.module = obj;
        //   super(Children.create(new ModuleChildFactory(xmlElement), true), Lookups.singleton(obj));
        setDisplayName(obj.getName());
        if (element.getName().equalsIgnoreCase("module")) {
            setIconBaseWithExtension(obj.getImagePath());
        }
    }

    @Override
    public String getHtmlDisplayName() {
        Module obj = getLookup().lookup(Module.class);
        if (obj != null) {
            return "<font color='0000FF'>" + obj.getName() + "</font>";
        } else {
            return null;
        }
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{new MyAction()};
    }

    private class MyAction extends AbstractAction implements Presenter.Popup {

        public MyAction() {
            putValue(NAME, "Do Something");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Module obj = getLookup().lookup(Module.class);
            JOptionPane.showMessageDialog(null, "Hello from " + obj);
        }

        @Override
        public JMenuItem getPopupPresenter() {
            JMenu result = new JMenu("Submenu");  //remember JMenu is a subclass of JMenuItem
            result.add(new JMenuItem(this));
            result.add(new JMenuItem(this));
            return result;
        }

    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();

        Module obj = getLookup().lookup(Module.class);
        if (obj.getMmo() != null) {
            try {
                ModuleMetaObj mmo = obj.getMmo();
                List<VariableMetaObj> vmoList = mmo.getVarMetaObjList();
                ModuleProperties mp = new ModuleProperties();
                set.put(mp.getModuleProperties(mmo));
                if (vmoList != null) {
                    for (int i = 0; i < vmoList.size(); i++) {
                        VariableMetaObj vmo = vmoList.get(i);
                        String name = vmo.getName();

                        Sheet.Set varSet = Sheet.createPropertiesSet();

                        varSet.setDisplayName(name);
                        varSet.setName(name);
                        varSet.put(mp.getVarProperties(vmo));
                        varSet.setValue("tabName", "variable meta");
                        sheet.put(varSet);
                    }
                }

            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        sheet.put(set);

        return sheet;
    }

    @Override
    public Transferable drag() throws IOException {
        //  this.createSheet();
        return module;
    }

}
