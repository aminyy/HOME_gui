//@DECLARE@
package net.casnw.home.gui.project.sub;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeListener;
import net.casnw.home.gui.project.HomeProject;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.SubprojectProvider;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author luolh
 * @since 2014/3/11 Sub project in the project explorer
 *
 */
public class HomeSubprojectProvider implements SubprojectProvider {

    private final HomeProject project;

    public HomeSubprojectProvider(HomeProject project) {
        this.project = project;
    }

    @Override
    public Set<? extends Project> getSubprojects() {
        return loadProjects(project.getProjectDirectory());
    }

    private Set loadProjects(FileObject dir) {
        Set newProjects = new HashSet();
        FileObject reportsFolder = dir.getFileObject("Model");
        if (reportsFolder != null) {
            for (FileObject childFolder : reportsFolder.getChildren()) {
                try {
                    Project subp = ProjectManager.getDefault().
                            findProject(childFolder);
                    if (subp != null && subp instanceof HomeSubProject) {
                        newProjects.add((HomeSubProject) subp);
                    }
                } catch (IOException | IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        return Collections.unmodifiableSet(newProjects);
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

}
