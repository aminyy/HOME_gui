package net.casnw.hom.gui.homNavigator;

import java.awt.BorderLayout;
import java.util.Collections;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

@NavigatorPanel.Registration(mimeType = "text/hom+xml", displayName = "Home File Content")
public class HomNavigatorPanel extends JPanel implements NavigatorPanel, LookupListener {

    JScrollPane pane;
    Lookup lookup;
    InstanceContent ic = new InstanceContent();

    public HomNavigatorPanel() {
        setLayout(new BorderLayout());
        pane = new JScrollPane();
        add(pane, BorderLayout.CENTER);
        lookup = new AbstractLookup(ic);
        
    }
    Result<Scene> scenesInLookup;

    @Override
    public void panelActivated(Lookup lkp) {
        scenesInLookup = lkp.lookupResult(Scene.class);
        scenesInLookup.addLookupListener(this);
        resultChanged(new LookupEvent(scenesInLookup));
    }

    @Override
    public void panelDeactivated() {
        scenesInLookup.removeLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent le) {
        if (!scenesInLookup.allInstances().isEmpty()) {
            Scene s = scenesInLookup.allInstances().iterator().next();
            if (s != null) {
                pane.setViewportView(s.createSatelliteView());
                ic.set(Collections.singleton(s), null);
            }
        } else {
            pane.setViewportView(new JLabel("<no file selected>"));
        }
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public String getDisplayName() {
        return "Home File Content";
    }

    @Override
    public String getDisplayHint() {
        return "Home File Content";
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}