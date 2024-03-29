/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.List;
import net.casnw.home.gui.homFileType.HomFileDataObject;
import net.casnw.home.modules.api.Module;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Administrator
 */
public class Common {
    /*
     修改hom文件
     @para HomFileDataObject obj hom文件对象
     @para Module m 要插入的节点对象
     @Element ele 要插入节点的父节点对象
     @int index 插入的位置
     */

    public static void addNodeToFile(HomFileDataObject obj, Module m, Element ele, int index) throws IOException {
        //修改hom文件        

        OutputFormat format = OutputFormat.createPrettyPrint();

        List elements = ele.elements();
        Element newEle = m.getEle();
        elements.add(index, newEle);

        OutputStream os = obj.getPrimaryFile().getOutputStream();
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(os, "UTF-8"), format);
        // OutputStreamWriter writer = new OutputStreamWriter(os,"UTF-8");
        System.out.println("ele=" + ele.getDocument());
        writer.write(ele.getDocument());
        writer.flush();
        writer.close();
    }

    /**
     * 从hom文件中删除选中节点
     *
     * @para HomFileDataObject obj hom文件对象
     * @para Module m 要删除的节点对象
     */
    public static void delNodeFromFile(HomFileDataObject obj, Module m) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        System.out.println("delete selected node=" + m.getName());
        Element ele = m.getEle();
        Element parentEle = ele.getParent();
        parentEle.remove(ele);

        OutputStream os = obj.getPrimaryFile().getOutputStream();
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(os, "UTF-8"), format);

        writer.write(parentEle.getDocument());
        writer.flush();
        writer.close();

    }

    /**
     * 解析模块注解对象
     *
     * @return ModuleMetaObj
     * @throws ClassNotFoundException
     */
    /*public static ModuleMetaObj parseModuleMeta(String className) throws ClassNotFoundException {
     ModuleMetaObj mmo = null;
     System.out.println("classname="+className);
     //根据类名获得类
     Class module = Class.forName(className);
        
     System.out.println("path11="+module.getResource("").getPath());
     //判断类是否包含ModuleMeta注解
     boolean isAnn = module.isAnnotationPresent(ModuleMeta.class);
     if (isAnn) {
     //获取注解对象
     ModuleMeta moduleAnn = (ModuleMeta) module.getAnnotation(ModuleMeta.class);
     //解析注解对象，生成模块元数据对象
     mmo = new ModuleMetaObj();
     mmo.setName(moduleAnn.name());
     if (moduleAnn.category() != null && !"".equalsIgnoreCase(moduleAnn.category())) {
     mmo.setCategory(moduleAnn.category());
     }
     if (moduleAnn.description() != null && !"".equalsIgnoreCase(moduleAnn.description())) {
     mmo.setDescription(moduleAnn.description());
     }
     if (moduleAnn.applicationField() != null && !"".equalsIgnoreCase(moduleAnn.applicationField())) {
     mmo.setApplicationField(moduleAnn.applicationField());
     }
     if (moduleAnn.author() != null && !"".equalsIgnoreCase(moduleAnn.author())) {
     mmo.setAuthor(moduleAnn.author());
     }
     if (moduleAnn.keyword() != null && !"".equalsIgnoreCase(moduleAnn.keyword())) {
     mmo.setKeyword(moduleAnn.keyword());
     }
     if (moduleAnn.model() != null && !"".equalsIgnoreCase(moduleAnn.model())) {
     mmo.setModel(moduleAnn.model());
     }
     if (moduleAnn.theory() != null && !"".equalsIgnoreCase(moduleAnn.theory())) {
     mmo.setTheory(moduleAnn.theory());
     }

     mmo.setTimeScale(moduleAnn.timeScale());
     if (moduleAnn.timeScale() != null && !"".equalsIgnoreCase(moduleAnn.timeScale().toString())) {
     mmo.setTimeScale(moduleAnn.timeScale());
     }

     if (moduleAnn.spaceScale() != null && !"".equalsIgnoreCase(moduleAnn.spaceScale().toString())) {
     mmo.setSpaceScale(moduleAnn.spaceScale());
     }
     if (moduleAnn.spaRefSys() != null && !"".equalsIgnoreCase(moduleAnn.spaRefSys().toString())) {
     mmo.setSpaRefSys(moduleAnn.spaRefSys());
     }
     mmo.setModuleClass(className);
     //取出模块所有的field
     Field[] fields = module.getFields();
     List<VariableMetaObj> vmoList = new ArrayList<VariableMetaObj>();
     for (Field f : fields) {
     //判断field是否有VariableMeta注解
     if (f.isAnnotationPresent(VariableMeta.class)) {
     vmoList.add(paseVarmeta(f));
     }
     }
     mmo.setVarMetaObjList(vmoList);
     }
     return mmo;
     }

     /**
     * 解析参数注解对象
     *
     * @param Field
     * @return VariableMetaObj
     */
    /* public static VariableMetaObj paseVarmeta(Field f) {
     VariableMeta varAnn = f.getAnnotation(VariableMeta.class);
     VariableMetaObj vmo = new VariableMetaObj();
     vmo.setName(varAnn.name());
     if (varAnn.dataType() != null && !"".equalsIgnoreCase(varAnn.dataType().toString())) {
     vmo.setDataType(varAnn.dataType().toString());
     }
     if (varAnn.description() != null && !"".equalsIgnoreCase(varAnn.description())) {
     vmo.setDescription(varAnn.description());
     }
     if (varAnn.range() != null && !"".equalsIgnoreCase(varAnn.range())) {
     vmo.setRange(varAnn.range());
     }
     if (varAnn.size() > 0) {
     vmo.setSize(varAnn.size());
     }
     if (varAnn.unit() != null && !"".equalsIgnoreCase(varAnn.unit().toString())) {
     vmo.setUnit(varAnn.unit());
     }
     if (varAnn.value() != null && !"".equalsIgnoreCase(varAnn.value())) {
     vmo.setValue(varAnn.value());
     }

     return vmo;
     }*/
}
