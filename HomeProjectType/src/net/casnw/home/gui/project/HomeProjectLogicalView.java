/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.project;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
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
 * @author Administrator
 */
class HomeProjectLogicalView implements LogicalViewProvider {

    @StaticResource()
    public static final String HOME_ICON = "net/casnw/home/gui/project/icon16.png";

    private final HomeProject project;

    public HomeProjectLogicalView(HomeProject project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
        try {
            //Obtain the project directory's node:
            FileObject projectDirectory = project.getProjectDirectory();
            DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
            Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
            //Decorate the project directory's node:
            return new ProjectNode(nodeOfProjectFolder, project);
        } catch (DataObjectNotFoundException donfe) {
            Exceptions.printStackTrace(donfe);
            //Fallback-the directory couldn't be created -
            //read-only filesystem or something evil happened
            return new AbstractNode(Children.LEAF);
        }
    }

    private final class ProjectNode extends FilterNode {

        final HomeProject project;

        public ProjectNode(Node node, HomeProject project)
                throws DataObjectNotFoundException {
            super(node,
                    /*  NodeFactorySupport.createCompositeChildren(
                     project,
                     "Projects/net-casnw-home-gui-project/Nodes"),*/
                    new FilterNode.Children(node),
                    new ProxyLookup(
                            new Lookup[]{
                                Lookups.singleton(project),
                                node.getLookup()
                            }));
            this.project = project;
        }

        @Override
        public Action[] getActions(boolean arg0) {
            return new Action[]{
                new ProjectAction(ActionProvider.COMMAND_RUN, "Run", project),
                new ProjectAction("display", "Display", project),
                CommonProjectActions.newFileAction(),
                CommonProjectActions.copyProjectAction(),
                CommonProjectActions.deleteProjectAction(),
                CommonProjectActions.customizeProjectAction(),
                CommonProjectActions.closeProjectAction()
            };
        }

        @Override
        public Image getIcon(int type) {
            return ImageUtilities.loadImage(HOME_ICON);
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }

    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }

    private static class ProjectAction extends AbstractAction {

        private final HomeProject project;
        private final String command;

        public ProjectAction(String cmd, String displayName, HomeProject prj) {
            this.project = prj;
            putValue(NAME, displayName);
            this.command = cmd;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Utils util = new Utils();
            if (command.equals("display")) {
                try {
                    util.processCommand("display",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if(command.equals("run")){
                try {
                    util.processCommand("run",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }else if (command.equals(ActionProvider.COMMAND_CLEAN)) {
                try {
                    util.processCommand("clean",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (command.equals(ActionProvider.COMMAND_BUILD)) {
                try {
                    util.processCommand("jar",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (command.equals(ActionProvider.COMMAND_REBUILD)) {
                try {
                    util.processCommand("uberjar",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (command.equals(ActionProvider.COMMAND_PROFILE)) {
                try {
                    util.processCommand("help",this.project);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }


        @Override
        public boolean isEnabled() {
            ActionProvider prov = (ActionProvider) project.getLookup().lookup(ActionProvider.class);
            return prov.isActionEnabled(command, null);
        }

    }

}
