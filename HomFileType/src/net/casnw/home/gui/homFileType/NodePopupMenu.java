/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.casnw.home.gui.palette.Util;

import net.casnw.home.modules.api.Module;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.NodeOperation;
import org.openide.util.Exceptions;

/**
 *
 * @author Administrator
 */
public class NodePopupMenu implements PopupMenuProvider {

    //  GraphSceneImpl sence;
    Module module;

    NodePopupMenu(Module module) {
        //     this.sence = sence;
        this.module = module;
    }

    @Override
    public JPopupMenu getPopupMenu(final Widget widget, Point localLocation) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delMenu = new JMenuItem("delete");
        JMenuItem propsMenu = new JMenuItem("Properties");
        delMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

             //   widget.removeFromParent();
             //   Module node = (Module)sence.findObject(widget);
                // sence.removeNodeWithEdges(node);
            }
        });
        propsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BeanNode node;
                try {
                    node = Util.getModuleBeanNode(module.getMmo());
                    node.setDisplayName(module.getName());
                    node.setShortDescription("Description of " + module.getName());
                    NodeOperation.getDefault().showProperties(node);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        });
        popup.add(propsMenu);
        popup.add(delMenu);
        return popup;
    }

}
