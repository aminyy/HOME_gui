//@DECLARE@
package net.casnw.home.gui.palette;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.casnw.home.modules.api.Module;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.meta.metaParse;

import org.dom4j.Element;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author minyf & luolh
 * @since 2014/4/14 Define the children of Module
 *
 */
public class ModuleChildren extends Children.Array {

    private Module obj;
    private Element element;

    ModuleChildren(Module obj, Element element) {
        this.obj = obj;
        this.element = element;
    }

    @Override
    protected Collection<Node> initCollection() {

        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Iterator i = this.element.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            String name = "";
            String id = "";
            String moduleClass = "";
            String imagePath = "";

            if (el instanceof Element) {
                Module m = new Module();
                ModuleNode mn;
                if (el.attribute("name") != null) {
                   
                    name = el.attribute("name").getValue();
                     System.out.println("moduleChild module name="+name);
                }
                if (el.attribute("id") != null) {
                    id = el.attribute("id").getValue();
                }

                if (el.attribute("image") != null) {
                    imagePath = el.attribute("image").getValue();
                } else {
                    imagePath = "net/casnw/home/gui/modeling/palette/module.png";
                }

                m.setName(name);
                m.setCode(id);
                if (el.attribute("class") != null
                        && !"".equalsIgnoreCase(el.attribute("class").getValue())) {
                    moduleClass = el.attribute("class").getValue();
                    //String moduleFile = System.getProperty("user.dir")+"/MoudleTree/src/net/casnw/home/modules/moduletree/"+xmlfile;
                    //String moduleFile = "G:\\基于建模框架的生态-水文模型构建与参数模拟\\源码\\MoudleTree\\src\\net\\casnw\\home\\modules\\moduletree\\modulesmeta.xml";
                   // String moduleFile = "c:\\" + xmlfile;

                    ModuleMetaObj mmo;
                    try {
                        //2014-06-17改成从类注释中获得元数据
                        // mmo = MoudesXmlOperator.getModuleMetaObj(moduleFile);
                        
                        mmo = metaParse.parseModuleMeta(moduleClass);
                        m.setMmo(mmo);
                        System.out.println("initCollection method " + moduleClass);
                        //create Element object
                        m.setEle(Util.createXmlElement(m));
                        //  }

                    } catch (ClassNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
              //  m.setXmlfile(xmlfile);
                m.setImagePath(imagePath);

                mn = new ModuleNode(m, el);
                nodes.add(mn);
            }
        }
        return nodes;

    }
}
