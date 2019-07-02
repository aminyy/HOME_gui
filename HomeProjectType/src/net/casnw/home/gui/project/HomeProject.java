//@DECLARE@
package net.casnw.home.gui.project;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.casnw.home.gui.project.sub.HomeSubprojectProvider;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.MoveOrRenameOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Luolh
 * @since 2014/03/11 HOME project
 *
 */
public class HomeProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;

    HomeProject(FileObject dir, ProjectState state) {
        this.projectDir = dir;
        this.state = state;
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                this,
                new Info(),
                new HomeProjectLogicalView(this),
                new HomeCustomizerProvider(this),
                new HomeActionProvider(),
                new HomeProjectMoveOrRenameOperation(),
                new HomeProjectCopyOperation(),
                new HomeProjectDeleteOperation(this),
                new HomeSubprojectProvider(this),});
        }
        return lkp;
    }

    private final class Info implements ProjectInformation {

        @StaticResource()
        public static final String HOME_ICON = "net/casnw/home/gui/project/icon16.png";

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(HOME_ICON));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return HomeProject.this;
        }
    }

    class HomeActionProvider implements ActionProvider {

        @Override
        public String[] getSupportedActions() {
            return new String[]{
                ActionProvider.COMMAND_RENAME,
                ActionProvider.COMMAND_MOVE,
                ActionProvider.COMMAND_COPY,
                ActionProvider.COMMAND_DELETE,
                ActionProvider.COMMAND_RUN,
                ActionProvider.COMMAND_BUILD
            };
        }

        @Override
        public void invokeAction(String string, Lookup lkp) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_RENAME)) {
                DefaultProjectOperations.performDefaultRenameOperation(
                        HomeProject.this,
                        "");
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_MOVE)) {
                DefaultProjectOperations.performDefaultMoveOperation(
                        HomeProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(
                        HomeProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(
                        HomeProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_RUN)) {
                /* DefaultProjectOperations.performDefaultCopyOperation(
                 HomeProject.this);*/
                Utils util = new Utils();
                try {
                    util.processCommand("run",HomeProject.this);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_BUILD)) {
                DefaultProjectOperations.performDefaultDeleteOperation(
                        HomeProject.this);
            }
        }

        @Override
        public boolean isActionEnabled(String command, Lookup lookup) throws IllegalArgumentException {
            switch (command) {
                case ActionProvider.COMMAND_RENAME:
                    return true;
                case "display":
                    return true;
                case ActionProvider.COMMAND_MOVE:
                    return true;
                case ActionProvider.COMMAND_COPY:
                    return true;
                case ActionProvider.COMMAND_DELETE:
                    return true;
                case ActionProvider.COMMAND_RUN:
                    return true;
                case ActionProvider.COMMAND_BUILD:
                    return true;
            }
            return false;
        }

    }

    private final class HomeProjectMoveOrRenameOperation implements MoveOrRenameOperationImplementation {

        @Override
        public List<FileObject> getMetadataFiles() {
            return new ArrayList<>();
        }

        @Override
        public List<FileObject> getDataFiles() {
            return new ArrayList<>();
        }

        @Override
        public void notifyRenaming() throws IOException {
        }

        @Override
        public void notifyRenamed(String nueName) throws IOException {
        }

        @Override
        public void notifyMoving() throws IOException {
        }

        @Override
        public void notifyMoved(Project original, File originalPath, String nueName) throws IOException {
        }

    }

    private final class HomeProjectCopyOperation implements CopyOperationImplementation {

        @Override
        public List<FileObject> getMetadataFiles() {
            return new ArrayList<>();
        }

        @Override
        public List<FileObject> getDataFiles() {
            return new ArrayList<>();
        }

        @Override
        public void notifyCopying() throws IOException {
        }

        @Override
        public void notifyCopied(Project prjct, File file, String string) throws IOException {
        }

    }

    private final class HomeProjectDeleteOperation implements DeleteOperationImplementation {

        private final HomeProject project;

        private HomeProjectDeleteOperation(HomeProject project) {
            this.project = project;
        }

        @Override
        public List<FileObject> getDataFiles() {
            List<FileObject> files = new ArrayList<>();
            FileObject[] projectChildren = project.getProjectDirectory().getChildren();
            for (FileObject fileObject : projectChildren) {
                addFile(project.getProjectDirectory(), fileObject.getNameExt(), files);
            }
            return files;
        }

        private void addFile(FileObject projectDirectory, String fileName, List<FileObject> result) {
            FileObject file = projectDirectory.getFileObject(fileName);
            if (file != null) {
                result.add(file);
            }
        }

        @Override
        public List<FileObject> getMetadataFiles() {
            return new ArrayList<>();
        }

        @Override
        public void notifyDeleting() throws IOException {
        }

        @Override
        public void notifyDeleted() throws IOException {
        }

    }

}
