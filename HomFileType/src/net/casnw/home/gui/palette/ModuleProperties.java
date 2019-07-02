//@DECLARE@
package net.casnw.home.gui.palette;

import java.util.Date;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.meta.VariableMetaObj;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author minyf
 * @since 2014/4/14 Module Properties
 *
 */
public class ModuleProperties {

    public Property[] getModuleProperties(ModuleMetaObj mmo) throws NoSuchMethodException {
        Property[] pro = new Property[13];

        Node.Property classProp = new PropertySupport.Reflection(mmo, String.class, "getModuleClass", null);
        Node.Property nameProp = new PropertySupport.Reflection(mmo, String.class, "getName", "setName");
        Node.Property versionProp = new PropertySupport.Reflection(mmo, String.class, "getVersion", null);
        Node.Property authorProp = new PropertySupport.Reflection(mmo, String.class, "getAuthor", "setAuthor");
        Node.Property keywordProp = new PropertySupport.Reflection(mmo, String.class, "getKeyword", null);
        Node.Property descProp = new PropertySupport.Reflection(mmo, String.class, "getDescription", null);
        Node.Property categoryProp = new PropertySupport.Reflection(mmo, String.class, "getCategory", null);
        Node.Property appProp = new PropertySupport.Reflection(mmo, String.class, "getApplicationField", null);
        Node.Property theoryProp = new PropertySupport.Reflection(mmo, String.class, "getTheory", null);
        Node.Property modelProp = new PropertySupport.Reflection(mmo, String.class, "getModel", null);
        Node.Property timeProp = new PropertySupport.Reflection(mmo, String.class, "getTimeScale", null);
        Node.Property spaceProp = new PropertySupport.Reflection(mmo, String.class, "getSpaceScale", null);
        Node.Property sparefsysProp = new PropertySupport.Reflection(mmo, String.class, "getSpaRefSys", null);
        pro[0] = nameProp;
        pro[1] = classProp;
        pro[2] = authorProp;
        pro[3] = keywordProp;
        pro[4] = descProp;
        pro[5] = categoryProp;
        pro[6] = appProp;
        pro[7] = theoryProp;
        pro[8] = modelProp;
        pro[9] = timeProp;
        pro[10] = spaceProp;
        pro[11] = sparefsysProp;
        pro[12] = versionProp;

        return pro;

    }

    public Property[] getVarProperties(VariableMetaObj vmo) throws NoSuchMethodException {
        Property[] pro = new Property[8];
        Node.Property nameProp = new PropertySupport.Reflection(vmo, String.class, "getName", "setName");
        Node.Property dataTypeProp = new PropertySupport.Reflection(vmo, String.class, "getDataType", null);
        Node.Property descProp = new PropertySupport.Reflection(vmo, String.class, "getDescription", null);
        Node.Property rangeProp = new PropertySupport.Reflection(vmo, String.class, "getRange", null);
        Node.Property unitProp = new PropertySupport.Reflection(vmo, String.class, "getUnit", null);
        Node.Property valueProp = new PropertySupport.Reflection(vmo, String.class, "getValue", "setValue");
        Node.Property sizeProp = new PropertySupport.Reflection(vmo, int.class, "getSize", "setSize");
        Node.Property dateProp = new  PropertySupport.Reflection(vmo, Date.class, "Date");

        pro[0] = nameProp;
        pro[1] = dataTypeProp;
        pro[2] = descProp;
        pro[3] = rangeProp;
        pro[4] = valueProp;
        pro[5] = sizeProp;
        pro[6] = unitProp;
        pro[7] = dateProp;

        return pro;

    }
}
