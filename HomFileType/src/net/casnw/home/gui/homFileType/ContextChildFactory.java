/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import net.casnw.home.gui.utils.Common;
import java.awt.Container;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.casnw.home.gui.palette.ModuleProperties;
import net.casnw.home.gui.palette.Util;
import net.casnw.home.gui.panel.ContextPanel;
import net.casnw.home.gui.panel.PopFrame;
import net.casnw.home.gui.utils.DataPoolOperation;
import net.casnw.home.io.ContextDescription;
import net.casnw.home.io.ModelDescription;
import net.casnw.home.io.ModuleDescription;
import net.casnw.home.io.VariableDescription;
import net.casnw.home.modules.api.Module;
import net.casnw.home.meta.ModuleMetaObj;
import net.casnw.home.modules.api.Utils;
import net.casnw.home.meta.VariableMetaObj;
import net.casnw.home.meta.metaParse;
import org.dom4j.Element;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.NodeAdapter;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeOperation;
import org.openide.nodes.NodeReorderEvent;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.WeakListeners;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.Lookups;
import org.openide.actions.DeleteAction;

class ContextChildFactory extends ChildFactory<Module> implements NodeListener {

    private ArrayList<Module> modules = new ArrayList<Module>();
    private HomFileDataObject obj;
    private ModelDescription modelDes;
    private DataPoolOperation dpo;

    public ContextChildFactory(ContextDescription cd, HomFileDataObject obj, ModelDescription modelDes, DataPoolOperation dpo) throws ClassNotFoundException {
        if (cd != null) {

            List<ModuleDescription> componentList;
            componentList = cd.getModuleList();//取出所有Module
            for (int i = 0; i < componentList.size(); i++) {
                try {
                    ModuleDescription moduleDes = componentList.get(i);
                    Module m = new Module();
                    m.setEle(moduleDes.getEle());
                    m.setName(moduleDes.getInstanceName());
                    List<VariableDescription> vdList = moduleDes.getVariableList();
                    List<VariableMetaObj> vmList = new ArrayList<>();
                    //从类文件中解析出元数据对象
                    ModuleMetaObj mmo = metaParse.parseModuleMeta(moduleDes.getInstanceClass());

                    //mmo.setName();
                    for (VariableDescription vd : vdList) {
                        VariableMetaObj vmo = new VariableMetaObj();
                        vmo.setContext(vd.getVariableContext());
                        vmo.setDataType(vd.getVariableType());
                        vmo.setName(vd.getVariableName());
                        //  vmo.setSize(Integer.parseInt(vd.getVariableSize()));
                        vmo.setValue(vd.getVariableValue());
                        vmList.add(vmo);
                    }
                    mmo.setVarMetaObjList(vmList);
                    m.setMmo(mmo);
                    m.setMd(moduleDes);

                    modules.add(m);
                } catch (ClassNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        this.obj = obj;
        this.modelDes = modelDes;
        this.dpo = dpo;
    }

    public void setDataPoolOperation(DataPoolOperation dpo) {
        this.dpo = dpo;
    }

    @Override
    protected boolean createKeys(List<Module> list) {
        for (Module m : modules) {
            list.add(m);
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(final Module selectModule) {
        System.out.println("call node createNodeForKey()");
        ModuleNode node = null;

        try {
            if (Utils.isContext(selectModule.getMmo().getModuleClass()) == 1) {
                node = new ModuleNode(selectModule, (ContextDescription) selectModule.getMd(), obj, modelDes, dpo);

            } else {

                node = new ModuleNode(selectModule) {
                    @Override
                    public PasteType getDropType(Transferable t, final int action, final int index) {
                        try {
                            //We get the current customer from the transferable:
                            final Module addModule = (Module) t.getTransferData(Module.DATA_FLAVOR);
                            final int dropIndex = modules.indexOf(selectModule) + 1;
                            return new PasteType() {
                                @Override
                                public Transferable paste() throws IOException {
                                    //We add a new node after the current index,

                                    modules.add(dropIndex, addModule);
                                    Element ele = selectModule.getEle();
                                    // ele.getParent().elements().indexOf(ele);
                                    int index = ele.getParent().elements().indexOf(ele);
                                    Common.addNodeToFile(obj, addModule, ele.getParent(), index);
                                    System.out.println("23 ele count=" + ele.getParent().elements().size());
                                    System.out.println("23 ele index=" + index);
                                    //We refresh the node:
                                    refresh(true);
                                    //We put nothing in the clipboard:
                                    return null;
                                }
                            };
                        } catch (UnsupportedFlavorException | IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        return null;
                    }
                };

            }
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        node.addNodeListener(new NodeAdapter() {
            @Override
            public void nodeDestroyed(NodeEvent ev) {
                try {
                    System.err.println("*** node destroy " + ev.getNode());
                    System.out.println("call node nodeDestroyed()");

                    // if (log == 1) {
                    modules.remove(selectModule);
                    if (modules.contains(selectModule)) {
                        Common.delNodeFromFile(obj, selectModule);
                        //     }
                    }
                    //  log = 0;

                    refresh(true);
                    //  log = 1;
                    System.out.println("refresg");
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        });
        node.addNodeListener(this);

        node.setDisplayName(selectModule.getName());
        return node;
    }

    @Override
    public void childrenAdded(NodeMemberEvent nme) {

    }

    @Override
    public void childrenRemoved(NodeMemberEvent nme) {

    }

    @Override
    public void childrenReordered(NodeReorderEvent nre) {

    }

    @Override
    public void nodeDestroyed(NodeEvent ne) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public class ModuleNode extends BeanNode implements PropertyChangeListener {

        // private Module module = null;
        ModuleMetaObj mmo = null;
        private Module module = null;
        private ModelDescription modelDes;

        public ModuleNode(Module obj, ContextDescription cd, HomFileDataObject hdo, ModelDescription modelDes, DataPoolOperation dpo) throws ClassNotFoundException, IntrospectionException {
            super(obj.getMmo(), Children.create(new ContextChildFactory(cd, hdo, modelDes, dpo), true), Lookups.singleton(obj));
            this.mmo = obj.getMmo();
            this.module = obj;
            this.modelDes = modelDes;
            setDisplayName(obj.getName());

            obj.addPropertyChangeListener(WeakListeners.propertyChange(this, obj));
            /* if (element.getName().equalsIgnoreCase("module")) {
             setIconBaseWithExtension(obj.getImagePath());
             }*/
        }

        public ModuleNode(Module obj) throws IntrospectionException {

            super(obj.getMmo(), Children.LEAF, Lookups.singleton(obj));
            this.module = obj;
            this.mmo = obj.getMmo();
            setDisplayName(obj.getName());
            /* if (element.getName().equalsIgnoreCase("module")) {
             setIconBaseWithExtension(obj.getImagePath());
             }*/
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

            if (Utils.isContext(this.mmo.getModuleClass()) == 1) {
                return new Action[]{
                    new DeleteAction(),
                    new PropertyAction(),
                    new ContextAction()};
            } else {
                return new Action[]{
                    new DeleteAction(),
                    new PropertyAction()};
            }
            //  return new Action[]{new MyAction()};
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {
                this.fireDisplayNameChange(null, getDisplayName());
            }
        }

        //配置连接池
        private class ContextAction extends AbstractAction {

            public ContextAction() {
                putValue(NAME, "setContext");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf = new JFrame("设置数据池属性");//实例化一个对象
                Container container = jf.getContentPane();//获取一个容器
                ContextPanel cp = new ContextPanel(module, dpo, obj);

                container.add(cp);//将标签添加到容器中

                jf.setVisible(true);//可视化
                jf.setSize(476, 500);//设窗体大小
                jf.setLocation(400, 300);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                // ContextPanel cp = new ContextPanel(module);
                // cp.setVisible(true);
                /*JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题条文字串", JOptionPane.ERROR_MESSAGE);
                 PopFrame popFrame = new PopFrame();
                 popFrame.setSize(120, 100);
                 popFrame.setLocationRelativeTo(null);
                
                 ContextPanel cp = new ContextPanel(module);
                 popFrame.add(cp);
                 popFrame.setVisible(true);*/

            }
        }

        private class PropertyAction extends AbstractAction {

            public PropertyAction() {
                putValue(NAME, "properties");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                BeanNode node;
                try {
                    node = Util.getModuleBeanNode(mmo);
                    node.setDisplayName(mmo.getName());
                    node.setShortDescription("Description of " + mmo.getName());
                    NodeOperation.getDefault().showProperties(node);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }

        }

        private class DeleteAction extends AbstractAction {

            public DeleteAction() {
                putValue(NAME, "delete");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("call node destroy()");
                    ModuleNode.this.destroy();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
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

        @Override
        public Transferable drag() throws IOException {
            //  this.createSheet();
            return module;
        }

        @Override
        public PasteType getDropType(Transferable t, final int action, final int index) {
            try {
                //We get the current customer from the transferable:
                final Module addModule = (Module) t.getTransferData(Module.DATA_FLAVOR);
                ModuleNode[] n = null;
                //判断插入的节点是context or component
                System.out.println("iscontext="+addModule.getMmo().getModuleClass());
                if (Utils.isContext(addModule.getMmo().getModuleClass()) == 1) {
                   
                    ContextDescription cd = new ContextDescription();
                    addModule.setMd(cd);
                    n = new ModuleNode[]{new ModuleNode(addModule, cd, obj, modelDes, dpo)};
                } else {
                    n = new ModuleNode[]{new ModuleNode(addModule)};
                }
                final ModuleNode[] mn = n;

                final Children child = this.getChildren();
                final Element ele = this.module.getEle();

                return new PasteType() {
                    @Override
                    public Transferable paste() throws IOException {
                        int index = ele.elements().size();
                        //        addModule.setName(addModule.getName()+"_"+index);
                        System.out.println("13 ele count=" + ele.elements().size());
                        System.out.println("13 ele index=" + index);
                        child.add(mn);
                        //插入位置

                        Common.addNodeToFile(obj, addModule, ele, index);

                        refresh(true);
                        //We put nothing in the clipboard:
                        return null;
                    }
                };
            } catch (UnsupportedFlavorException | IOException | ClassNotFoundException | IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            return null;
        }
    }

}
