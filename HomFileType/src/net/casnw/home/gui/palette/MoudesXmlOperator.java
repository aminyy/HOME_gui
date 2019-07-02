//@DECLARE@
package net.casnw.home.gui.palette;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.meta.VariableMetaObj;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author minyf
 * @since 2014/4/14 Parse Module meta XML
 *
 */
public class MoudesXmlOperator {

    public static Document getDocument(String xmlFileName) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document d = reader.read(new File(xmlFileName));
        return d;
    }

    public MoudesXmlOperator() {
    }

    //遍历节点，返回值,用于模块元数据展示
    @SuppressWarnings("empty-statement")
    public static ModuleMetaObj getModuleMetaObj(String xmlFile) throws DocumentException {

        Document doc;
        doc = getDocument(xmlFile);
        Element root = doc.getRootElement();
        ModuleMetaObj mmo = new ModuleMetaObj();

        if ("moduleMeta".equalsIgnoreCase(root.getName())) {
            //解析modulesMeta的attribute
            String name = "";
            String description = "";
            String author = "";
            String moduleClass = "";
            name = root.attributeValue("name");
            moduleClass = root.attributeValue("class");
            description = root.attributeValue("description");
            author = root.attributeValue("author");
            mmo.setName(name);
            mmo.setModuleClass(moduleClass);
            mmo.setDescription(description);
            mmo.setAuthor(author);

            //解析modulesMeta下的element
            String version = "";
            String keyword = "";
            String category = "";
            String applicationField = "";
            String theory = "";
            String timeScale = "";
            String spaceScale = "";
            String spaRefSys = "";
            String model = "";

            version = root.elementText("version");
            keyword = root.elementText("keyword");
            category = root.elementText("category");
            applicationField = root.elementText("applicationField");
            timeScale = root.elementText("timeScale");
            spaceScale = root.elementText("spaceScale");
            spaRefSys = root.elementText("spaRefSys");
            model = root.elementText("model");

            mmo.setApplicationField(applicationField);
            mmo.setCategory(category);
            mmo.setKeyword(keyword);
            mmo.setModel(model);
            mmo.setSpaRefSys(spaRefSys);
            mmo.setSpaceScale(spaceScale);
            mmo.setTheory(theory);

            mmo.setTimeScale(timeScale);
            mmo.setVersion(version);

            //解析变量元数据
            List<Element> varlist = root.elements("varMeta");
            String varName = "";
            String varDes = "";
            String varUnit = "";
            String varRange = "";
            String varValue = "";
            int varSize = 0;
            String varDatatype = "";

            for (int i = 0; i < varlist.size(); i++) {
                for (Iterator it = varlist.get(i).elementIterator(); it.hasNext();) {
                    Element element = (Element) it.next();
                    if (element.attributeValue("name") != null) {
                        varName = element.attributeValue("name");
                    }
                    //容器内变量的名称
                    if (element.attributeValue("description") != null) {
                        varDes = element.attributeValue("description");
                    }
                    if (element.attributeValue("unit") != null) {
                        varUnit = element.attributeValue("unit");
                    }
                    if (element.attributeValue("range") != null) {
                        varRange = element.attributeValue("range");
                    }
                    if (element.attributeValue("value") != null) {
                        varValue = element.attributeValue("value");
                    }
                    if (element.attributeValue("dataType") != null) {
                        varDatatype = element.attributeValue("dataType");
                    }
                    if (element.attributeValue("size") != null
                            && !"".equalsIgnoreCase(element.attributeValue("size"))) {
                        varSize = Integer.parseInt(element.attributeValue("size"));
                    } else {
                        varSize = 0;
                    }
                    VariableMetaObj vbo = new VariableMetaObj();
                    vbo.setName(varName);
                    vbo.setDescription(varDes);
                    vbo.setRange(varRange);
                    vbo.setValue(varValue);
                    vbo.setUnit(varUnit);
                    vbo.setDataType(varDatatype);
                    vbo.setSize(i);

                    mmo.addVarMetaObj(vbo);

                }

            }
        }
        return mmo;
    }

}
