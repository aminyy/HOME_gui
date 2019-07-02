//@DECLARE@
package net.casnw.home.gui.palette;

import java.beans.IntrospectionException;
import java.util.List;
import net.casnw.home.modules.api.Module;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.modules.api.Utils;
import net.casnw.home.meta.VariableMetaObj;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author minyf
 * @since 2014/4/14 Some utilities functions
 *
 */
public class Util {

    public static AbstractNode getModuleNode(final Module obj) {
        AbstractNode node = new AbstractNode(Children.LEAF) {
            @Override
            protected Sheet createSheet() {
                Sheet sheet = super.createSheet();
                Sheet.Set set = sheet.createPropertiesSet();
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
        };
        return node;
    }

    public static BeanNode getModuleBeanNode(final ModuleMetaObj mmo) throws IntrospectionException {
        BeanNode node = new BeanNode(Children.LEAF) {
            @Override
            protected Sheet createSheet() {

                Sheet sheet = Sheet.createDefault();
                Sheet.Set set = Sheet.createPropertiesSet();

               // Module obj = getLookup().lookup(Module.class);

                if (mmo != null) {
                    try {
                        
                        List<VariableMetaObj> vmoList = mmo.getVarMetaObjList();
                        ModuleProperties mp = new ModuleProperties();
                        set.put(mp.getModuleProperties(mmo));
                        if (vmoList != null) {
                            for (int i = 0; i < vmoList.size(); i++) {
                                try {
                                    VariableMetaObj vmo = vmoList.get(i);
                                    String name = vmo.getName();

                                    Sheet.Set varSet = Sheet.createPropertiesSet();

                                    varSet.setDisplayName(name);
                                    varSet.setName(name);
                                    varSet.put(mp.getVarProperties(vmo));
                                    varSet.setValue("tabName", "variable meta");
                                    sheet.put(varSet);
                                } catch (NoSuchMethodException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }
                        }
                    } catch (NoSuchMethodException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                sheet.put(set);

                return sheet;
            }
        };
        return node;
    }

    public static Element createXmlElement(Module m) {
        final String MODULE_CLASS = "class";
        final String MODULE_NAME = "name";
        String elementType = "module";
        DocumentFactory DocumentFactory = new DocumentFactory();
        System.out.println("==createXmlElement=="+m.getMmo().getModuleClass());
        if (Utils.isContext(m.getMmo().getModuleClass()) == 1) {
            elementType = "context";
        }
        Element newEle = DocumentFactory.createElement(elementType);
        newEle.addAttribute(MODULE_CLASS, m.getMmo().getModuleClass());
        newEle.addAttribute(MODULE_NAME, m.getName());

        for (VariableMetaObj vmo : m.getMmo().getVarMetaObjList()) {
            Element varEle = newEle.addElement("var");

            if (vmo.getName() != null && !"".equalsIgnoreCase(vmo.getName())) {
                varEle.addAttribute("name", vmo.getName());
                //变量在容器中的名称，这部分有待进一步商榷
                varEle.addAttribute("atrribute", vmo.getName());
            }
            if (vmo.getDataType() != null && !"".equalsIgnoreCase(vmo.getDataType())) {
                varEle.addAttribute("type", vmo.getDataType());
            }
            if (vmo.getValue() != null && !"".equalsIgnoreCase(vmo.getValue())) {
                varEle.addAttribute("value", vmo.getValue());
            }
        }
        return newEle;
    }
}
