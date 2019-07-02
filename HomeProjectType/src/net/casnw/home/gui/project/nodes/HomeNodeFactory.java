//@DECLARE@
package net.casnw.home.gui.project.nodes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import net.casnw.home.gui.project.HomeProject;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author luolh
 * @since 2014/3/11 HOME Project node
 *
 */
@NodeFactory.Registration(projectType = "net-casnw-home-gui-project", position = 10)
public class HomeNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        HomeProject p = project.getLookup().lookup(HomeProject.class);
        assert p != null;
        return new HomeNodeList(p);
    }

    private class HomeNodeList implements NodeList<Node> {

        HomeProject project;

        public HomeNodeList(HomeProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {

            FileObject srcFolder = project.getProjectDirectory().getFileObject("src");
            FileObject inputFolder = project.getProjectDirectory().getFileObject("Input");
            FileObject outputFolder = project.getProjectDirectory().getFileObject("Output");
            FileObject modelFolder = project.getProjectDirectory().getFileObject("Model");
            FileObject logFolder = project.getProjectDirectory().getFileObject("Log");
            FileObject[] fileObjects = new FileObject[]{srcFolder, inputFolder, outputFolder,
                modelFolder, logFolder};
            List<Node> result = new ArrayList<>();
            for (FileObject fileFolder : fileObjects) {
                if (fileFolder != null) {
                    for (FileObject childFolderFile : fileFolder.getChildren()) {
                        try {
                            result.add(DataObject.find(childFolderFile).getNodeDelegate());
                        } catch (DataObjectNotFoundException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
            return result;
        }

        @Override
        public Node node(Node node) {
            return new FilterNode(node);
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
