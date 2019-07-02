/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import net.casnw.home.gui.homFileType.HomFileDataObject;
import net.casnw.home.io.ModelDescription;
import net.casnw.home.io.VariableDescription;
import net.casnw.home.model.AbsComponent;
import net.casnw.home.model.AttributeAccess;
import net.casnw.home.model.Componentable;
import net.casnw.home.model.Context;
import net.casnw.home.model.Contextable;
import net.casnw.home.model.Model;
import net.casnw.home.modules.api.Module;
import net.casnw.home.poolData.Datable;
import net.casnw.home.poolData.PoolBoolean;
import net.casnw.home.poolData.PoolBoolean2DArray;
import net.casnw.home.poolData.PoolBooleanArray;
import net.casnw.home.poolData.PoolCalendar;
import net.casnw.home.poolData.PoolDate;
import net.casnw.home.poolData.PoolDouble;
import net.casnw.home.poolData.PoolDouble2DArray;
import net.casnw.home.poolData.PoolDouble3DArray;
import net.casnw.home.poolData.PoolDoubleArray;
import net.casnw.home.poolData.PoolFloat;
import net.casnw.home.poolData.PoolFloat2DArray;
import net.casnw.home.poolData.PoolFloatArray;
import net.casnw.home.poolData.PoolInteger;
import net.casnw.home.poolData.PoolInteger2DArray;
import net.casnw.home.poolData.PoolInteger3DArray;
import net.casnw.home.poolData.PoolIntegerArray;
import net.casnw.home.poolData.PoolLong;
import net.casnw.home.poolData.PoolLong2DArray;
import net.casnw.home.poolData.PoolLongArray;
import net.casnw.home.poolData.PoolObject;
import net.casnw.home.poolData.PoolObjectArray;
import net.casnw.home.poolData.PoolString;
import net.casnw.home.poolData.PoolString2DArray;
import net.casnw.home.poolData.PoolStringArray;
import net.casnw.home.runtime.ModelLoader;
import net.casnw.home.runtime.Runtime;
import org.dom4j.Attribute;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import org.dom4j.io.XMLWriter;
import org.openide.util.Exceptions;


/**
 *
 * @author Administrator
 */
public class DataPoolOperation {

    private Model model;
    //模型中所有的context，context的名称不能重复
    private Map<String, Contextable> contextList = new HashMap<String, Contextable>();
    private Map<String, Boolean> contextInit = new HashMap<String, Boolean>();
    // private Map<String, Datable> dataPool;

    public DataPoolOperation(ModelDescription md) {
        Runtime rt = null;        //不需要，可以删除
        ModelLoader modelLoader = new ModelLoader(rt);
        try {
            this.model = modelLoader.loadModel(md);
            this.model.init();
            this.contextList = modelLoader.getContextList();
            for (String key : contextList.keySet()) {
                contextInit.put(key, Boolean.FALSE);
            }

        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InstantiationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public Model getModel() {

        return model;
    }

    public Context getContext(String name) {
        Contextable context = contextList.get(name);
        //  this.context = (Context) context;
        return (Context) context;
    }

    public void initAccessors(Context c) {
        try {
            c.initAccessors();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public Object[][] getContextVariables(String name) {
        Context c = getContext(name);
        if (!contextInit.get(name).booleanValue()) {
            initAccessors(c);
            contextInit.put(name, Boolean.TRUE);
        }
        Map<String, Datable> dataPool = c.getDataPool();
        Object[][] obj = new Object[dataPool.size()][4];
        int i = 0;
        for (String key : dataPool.keySet()) {
            Datable value = dataPool.get(key);
            JButton jb = new JButton("查看…");
            obj[i][0] = key;
            obj[i][1] = value.getClass().getSimpleName();
            obj[i][2] = value.toString();
            obj[i][3] = jb;
            i++;

        }

        return obj;
    }

    public void setContextValue(String name, Vector vec, HomFileDataObject obj, Element ele) {
        Context c = getContext(name);
        Map<String, Datable> dataPool = c.getDataPool();
        Map<String, String> changeData = new HashMap<String, String>();
        for (int i = 0; i < vec.size(); i++) {
            Vector aa = (Vector) vec.get(i);
            String key = aa.get(0).toString();
            Datable data = dataPool.get(key);
            System.out.println("data=" + data.toString());
            String oldValue = "";

            oldValue = data.toString();
            if (data.equals(null)) {
                oldValue = "";
            }

            String newValue = "";
            if (aa.get(1) != null) {
                newValue = aa.get(1).toString();
            }
            if (oldValue != newValue) {
                data.setValue(newValue);
                changeData.put(key, newValue);
            }
        }
        try {
            updateValue2Xml(name, obj, ele, changeData);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void updateValue2Xml(String name, HomFileDataObject obj, Element ele, Map<String, String> changeData) throws IOException, DocumentException {
        //修改hom文件        
        OutputFormat format = OutputFormat.createPrettyPrint();
        //对Element中参数进行修改

        for (String key : changeData.keySet()) {
            String value = changeData.get(key);
            setVarValue(ele, key, value);
            iteratorElements(ele, key, value);
        }

        OutputStream os = obj.getPrimaryFile().getOutputStream();
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(os, "UTF-8"), format);
        // OutputStreamWriter writer = new OutputStreamWriter(os,"UTF-8");
        System.out.println("ele=" + ele.getDocument());
        writer.write(ele.getDocument());
        writer.flush();
        writer.close();

    }

    public void iteratorElements(Element ele, String key, String value) {
        List elements = ele.elements();
        for (Iterator<Element> iter = elements.iterator(); iter.hasNext();) {
            Element innerEle = iter.next();
            setVarValue(innerEle, key, value);
            iteratorElements(innerEle, key, value);
        }

    }

    public void setVarValue(Element ele, String attriname, String value) {
        List<Element> eleList = ele.elements("var");
        for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
            Element innerEle = iter.next();
            String attri = innerEle.attribute("attribute").getValue();
            System.out.println("attri=" + attri);
            if (attri.equalsIgnoreCase(attriname)) {
                if (innerEle.attribute("value") != null) {
                    Attribute att = innerEle.attribute("value");
                    att.setText(value);

                } else {
                    innerEle.addAttribute("value", value);
                }
            }

        }
    }

    //取得数据池中变量名为attributeName的变量和包含它的模块
    public Vector getComAndVars(String context, String attributeName) {
        Context c = getContext(context);
        //如果数据池没有初始化，做数据池的初始化
        if (!contextInit.get(context).booleanValue()) {
            initAccessors(c);
            contextInit.put(context, Boolean.TRUE);
        }
        Vector vec = new Vector();
        //取出变量对应关系列表
        List<AttributeAccess> attriAccessList = new ArrayList<AttributeAccess>();
        attriAccessList = c.getAttributeAccess();
        for (AttributeAccess attriAcc : attriAccessList) {
            if (attriAcc.getAttributeName().equalsIgnoreCase(attributeName)) {
                Vector obj = new Vector();
                String compName = attriAcc.getComponent().getInstanceName();
                String varName = attriAcc.getCompName();
                obj.add(compName);
                obj.add(varName);

                vec.add(obj);
            }
        }

        return vec;
    }

    public List<String> getVariables(String context, String comp, String dataType) {
        List<String> varList = new ArrayList<String>();
        Context c = getContext(context);
        List<AttributeAccess> attriAccessList = new ArrayList<AttributeAccess>();
        attriAccessList = c.getAttributeAccess();
        Field[] fields;
        for (AttributeAccess attriAcc : attriAccessList) {
            if (attriAcc.getComponent().equals(c.getComponent(comp))) {
                fields = attriAcc.getComponent().getClass().getDeclaredFields();
                for (Field f : fields) {
                    if (f.getName().equalsIgnoreCase(attriAcc.getCompName())) {
                        String dt = f.getType().getSimpleName();
                        if (dt.equalsIgnoreCase(dataType)) {
                            varList.add(attriAcc.getCompName());
                            break;
                        }
                    }
                }

            }
        }

        return varList;
    }

    public List<Componentable> getComponents(String context) {
        Context c = getContext(context);
        List<Componentable> comList = new ArrayList<Componentable>();
        comList = c.getComponents();
        return comList;
    }

    //将新建的变量关系更新到xml文件中
    /*String name 容器名
     *Vector vec 修改的值
     *HomFileObject obj 文件对象
     *Element ele 容器节点的Element对象
     *String dataType 变量的数据类型
     */
    public void updateAccess(Module obj, List arr, HomFileDataObject hdb, String attributeName, String dataType) {
        Context c = getContext(obj.getName());
        List changeData = new ArrayList();

        for (int i = 0; i < arr.size(); i++) {

            String compName = ((String[]) arr.get(i))[0];
            String varName = ((String[]) arr.get(i))[1];

            Componentable component = c.getComponent(compName);
            AttributeAccess attri = new AttributeAccess();
            attri.setAttributeName(attributeName);
            attri.setCompName(varName);
            attri.setComponent(component);
            attri.setContext(c);
            attri.setDataType(dataType);
            c.addAccess(attri);
            changeData.add(new String[]{component.getClass().getName(), varName});

        }
        try {
            updateAttribute2Xml(obj, attributeName, hdb, changeData);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        //重新初始化内存中的context对象
        initAccessors(c);
        contextInit.put(obj.getName(), Boolean.TRUE);

    }

    public void updateAttribute2Xml(Module obj, String attributeName, HomFileDataObject hdo, List changeData) throws IOException {
        //修改hom文件        
        OutputFormat format = OutputFormat.createPrettyPrint();
        //对Element中参数进行修改
        Element ele = obj.getEle();
        for (int i = 0; i < changeData.size(); i++) {
            String compName = ((String[]) changeData.get(i))[0];
            String varName = ((String[]) changeData.get(i))[1];
            setVarAttribute(ele, obj.getName(), compName, varName, attributeName);
            // iteratorElements(ele, key, value);
        }

        OutputStream os = hdo.getPrimaryFile().getOutputStream();
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(os, "UTF-8"), format);
        writer.write(ele.getDocument());
        writer.flush();
        writer.close();
    }

    public void setVarAttribute(Element ele, String contextName, String componentName, String varName, String attributeName) {
        List<Element> eleList = ele.elements();
        for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
            Element innerEle = iter.next();
            if (!innerEle.getName().equalsIgnoreCase("var")) {
                String componentClass = innerEle.attribute("class").getValue();

                if (componentClass.equalsIgnoreCase(componentName)) {
                    List<Element> varList = innerEle.elements("var");
                    for (Iterator<Element> varIter = varList.iterator(); varIter.hasNext();) {
                        Element varEle = varIter.next();

                        String name = varEle.attribute("name").getText();
                        if (name.equalsIgnoreCase(varName)) {

                            if (varEle.attribute("attribute") != null) {
                                Attribute att = varEle.attribute("attribute");
                                att.setText(attributeName);

                            } else {
                                varEle.addAttribute("attribute", attributeName);
                            }

                            if (varEle.attribute("context") != null) {
                                Attribute att = varEle.attribute("context");
                                att.setText(contextName);

                            } else {
                                varEle.addAttribute("context", contextName);
                            }

                        }
                    }
                }
            }
        }
    }
}
