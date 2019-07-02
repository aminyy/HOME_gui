/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.BorderLayout;

import java.io.IOException;
import javax.swing.Action;
import javax.swing.JComponent;

import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.casnw.home.gui.utils.DataPoolOperation;
import net.casnw.home.io.ContextDescription;
import net.casnw.home.io.ModelDescription;
import net.casnw.home.io.XMLParse;
import org.dom4j.DocumentException;

import org.netbeans.core.spi.multiview.CloseOperationState;

import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_HomFile_VISUAL1",
        iconBase = "net/casnw/home/gui/homFileType/alarmclock.png",
        mimeType = "text/hom+xml",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "HomFileVisual1",
        position = 2000
)
@Messages("LBL_HomFile_VISUAL1=树形设计")
public final class HomFileVisualElement1 extends TopComponent implements MultiViewElement, ExplorerManager.Provider {

    private HomFileDataObject obj;
    private Lookup lookup;
    private JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private JScrollPane jsp = new JScrollPane();
    private final ExplorerManager mgr = new ExplorerManager();
    //  private TransferHandler th = new AlbumTransferHandler();
    private ModelDescription modelDes;
    private DataPoolOperation dpo = null;
    int i = 0;
    int j = 0;
    int m = 0;
    int n = 0;

    public HomFileVisualElement1(final Lookup lkp) throws IOException, DocumentException {
        obj = lkp.lookup(HomFileDataObject.class);
        lookup = new ProxyLookup(lkp, Lookups.singleton(new HomNavigatorLookupHint()));

        assert obj != null;
        initComponents();
        setLayout(new BorderLayout());
        // Module obj = new Module();

        showModelTree();

        setLayout(new BorderLayout());
        add(beanTreeView1, BorderLayout.CENTER);
        //  beanTreeView1.setTransferHandler(th);

        InstanceContent ic = new InstanceContent();
        Lookup dynamicLookup = new AbstractLookup(ic);
        lookup = new ProxyLookup(dynamicLookup, getLookup());
        ic.add(PaletteSupport.createPalette());

    }

    @Override
    public String getName() {
        return "HomFileVisualElement1";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        beanTreeView1 = new org.openide.explorer.view.BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beanTreeView1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beanTreeView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView beanTreeView1;
    // End of variables declaration//GEN-END:variables
    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        toolbar.setFloatable(false);
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public void componentOpened() {
        System.out.println("i=" + i++);
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
        System.out.println("j=" + j++);
        Object obj = lookup.lookup(InstanceContent.class);
     //   showModelTree();

        //  lookup.lookup(InstanceContent.class).add(scene);
    }

    @Override
    public void componentHidden() {
        // lookup.lookup(InstanceContent.class).remove(scene);
    }

    @Override
    public void componentActivated() {
        System.out.println("n=" + n++);
    }

    @Override
    public void componentDeactivated() {
        System.out.println("m=" + m++);
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        callback.getTopComponent().setDisplayName(obj.getPrimaryFile().getNameExt());
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return mgr;//To change body of generated methods, choose Tools | Templates.
    }

    private void showModelTree() {
        try {
            XMLParse xmlp = new XMLParse(obj.getPrimaryFile().getPath());
            modelDes = xmlp.getModelDescription();
            ContextDescription cd = new ContextDescription();
            cd.addModule(modelDes);
            dpo = new DataPoolOperation(modelDes);

            Node root = new AbstractNode(Children.create(new ContextChildFactory(cd, obj, modelDes, dpo), true));
            root.setName("Model Tree");
            root.setDisplayName("Model Tree");

            mgr.setRootContext(root);

        } catch (Exception ex) {
        }
    }

}