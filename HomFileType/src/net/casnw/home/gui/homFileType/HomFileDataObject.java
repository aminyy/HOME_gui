/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import org.netbeans.api.actions.Openable;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_HomFile_LOADER=Files of HomFile"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_HomFile_LOADER",
        mimeType = "text/hom+xml",
        extension = {"hom", "HOM"}
)
@DataObject.Registration(
        mimeType = "text/hom+xml",
        iconBase = "net/casnw/home/gui/homFileType/alarmclock.png",
        displayName = "#LBL_HomFile_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/hom+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class HomFileDataObject extends MultiDataObject implements Openable {

    private Lookup lookup;
    private InstanceContent ic = new InstanceContent();

    public HomFileDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        lookup = new ProxyLookup(getCookieSet().getLookup(), new AbstractLookup(ic));
        registerEditor("text/hom+xml", true);
        ic.add(ic);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_HomFile_EDITOR",
            iconBase = "net/casnw/home/gui/homFileType/alarmclock.png",
            mimeType = "text/hom+xml",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "HomFile",
            position = 1000
    )
    @Messages("LBL_HomFile_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    @Override
    public void open() {
        GraphSceneImpl scene = new GraphSceneImpl(this);
       /* LayerWidget lw = new LayerWidget(scene);
        try {
            List<String> asLines = getPrimaryFile().asLines();
            for (int i = 0; i < asLines.size(); i++) {
                String string = asLines.get(i);
                LabelWidget label = new LabelWidget(scene, string);
                label.setPreferredLocation(new Point(20, i * 20));
                label.getActions().addAction(ActionFactory.createMoveAction());
                lw.addChild(label);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        scene.addChild(lw);
        scene.validate();
        ic.add(lw);
        ic.add(scene);*/

    }

}
