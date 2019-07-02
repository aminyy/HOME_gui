//@DECLARE@
package net.casnw.home.gui.project.sub;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author luolh
 * @since 2014/3/11
 * Sub project info in the project explorer
 * 
 */
@NodeFactory.Registration(projectType = "net-casnw-home-gui-project", position = 20)
public class HomeSubProjectNodeFactory implements NodeFactory {

    @StaticResource()
    public static final String SUB_ICON = "net/casnw/home/gui/project/sub/icon.png";

    @Override
    public NodeList<?> createNodes(Project project) {
        HomeSubprojectProvider rsp = project.getLookup().
            lookup(HomeSubprojectProvider.class);
        assert rsp != null;
        return new HomeNodeList(rsp.getSubprojects());
    }

    private class HomeNodeList implements NodeList<Project> {

        Set<? extends Project> subprojects;

        public HomeNodeList(Set<? extends Project> subprojects) {
            this.subprojects = subprojects;
        }

        @Override
        public List<Project> keys() {
            List<Project> result = new ArrayList<Project>();
            for (Project oneReportSubProject : subprojects) {
                result.add(oneReportSubProject);
            }
            return result;
        }

        @Override
        public Node node(Project node) {
            FilterNode fn = null;
            try {
                fn = new FilterNode(DataObject.find(node.
                        getProjectDirectory()).getNodeDelegate()){
                    @Override
                    public Image getIcon(int type) {
                        return ImageUtilities.loadImage(SUB_ICON);
                    }
                    @Override
                    public Image getOpenedIcon(int type) {
                        return ImageUtilities.loadImage(SUB_ICON);
                    }
                };
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            return fn;
        }

        @Override
        public void addNotify() {
        }

        @Override
        public void removeNotify() {
        }

        @Override
        public void addChangeListener(ChangeListener cl) {
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
        }
        
    }
    
}
