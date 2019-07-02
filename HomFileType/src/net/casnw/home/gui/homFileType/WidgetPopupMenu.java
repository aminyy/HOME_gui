/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;
import net.casnw.home.gui.palette.Util;
import net.casnw.home.gui.panel.ContextPanel;
import net.casnw.home.gui.utils.Common;
import net.casnw.home.gui.utils.DataPoolOperation;
import net.casnw.home.io.ContextDescription;
import net.casnw.home.io.ModuleDescription;
import net.casnw.home.io.VariableDescription;
import net.casnw.home.io.XMLParse;

import net.casnw.home.modules.api.Module;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.modules.api.Utils;
import net.casnw.home.meta.VariableMetaObj;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.NodeOperation;
import org.openide.util.Exceptions;

/**
 * 2014-7-29
 *右键删属性
 * @author zxr
 */
public class WidgetPopupMenu implements PopupMenuProvider {

    private GraphSceneImpl sence;
    private HomFileDataObject obj;
    private DataPoolOperation dpo;
    WidgetPopupMenu(GraphSceneImpl sence, HomFileDataObject obj) {
        this.sence = sence;
        this.obj = obj;
          
    }

    @Override
    public JPopupMenu getPopupMenu(final Widget widget, Point localLocation) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delMenu = new JMenuItem("delete");
        JMenuItem propsMenu = new JMenuItem("Properties");
        JMenuItem contMenu = new JMenuItem("setContext");
        
        delMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                //   widget.removeFromParent();
                  
                Module node = (Module)sence.findObject(widget);
                
               if(Utils.isContext(node.getMmo().getModuleClass())==1) //容器
                {                       
                  //节点是容器，删除容器及容器内的模块
                  sence.removeFromParent();
                 Common.delNodeFromFile(obj, node);      
                
               }else{
                  //节点是模块，只删除节点本身
                  sence.removeNodeWithEdges(node);
                  Common.delNodeFromFile(obj, node); 
               }
                  
                }catch(IOException ex){
                  Exceptions.printStackTrace(ex);
                }catch (Exception ex) {
                }                 
            }
        });
        propsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractNode node = Util.getModuleNode((Module)sence.findObject(widget));
                node.setDisplayName(((Module)sence.findObject(widget)).getName());
                node.setShortDescription("Description of " + ((Module)sence.findObject(widget)).getName());
                NodeOperation.getDefault().showProperties(node);
            }
        });
       contMenu.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
          XMLParse xmlp = new XMLParse(obj.getPrimaryFile().getPath());
          dpo = new DataPoolOperation(xmlp.getModelDescription());
         JFrame jf = new JFrame("设置数据池属性");//实例化一个对象
                Container container = jf.getContentPane();//获取一个容器
                Module module=(Module)sence.findObject(widget);
                ContextDescription cd = new ContextDescription();
                ContextPanel cp = new ContextPanel(module, dpo,obj);

                container.add(cp);//将标签添加到容器中

                jf.setVisible(true);//可视化
                jf.setSize(476, 500);//设窗体大小
                jf.setLocation(400, 300);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
       });
        if (Utils.isContext(((Module)sence.findObject(widget)).getMmo().getModuleClass()) == 1) {
        popup.add(propsMenu);
        popup.add(delMenu);
        popup.add(contMenu);
        }else{
        popup.add(propsMenu);
        popup.add(delMenu);
        }
        return popup;
    }

}
