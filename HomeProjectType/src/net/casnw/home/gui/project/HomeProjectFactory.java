//@DECLARE@
package net.casnw.home.gui.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Luolh
 * @since 2014/3/11
 * Customize the HOME project with HOME.XML
 * 
 */
@ServiceProvider(service=ProjectFactory.class)
public class HomeProjectFactory implements ProjectFactory {
    public static final String PROJECT_FILE = "build.xml";

    //Specifies when a project is a project, i.e.,
    //if "HOME.XML" is present in a folder:
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_FILE) != null;
    }

    //Specifies when the project will be opened, i.e., if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new HomeProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        // leave unimplemented for the moment
    }
    
}
