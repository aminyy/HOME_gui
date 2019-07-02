//@DECLARE@
package net.casnw.home.gui.project.sub;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author luolh
 * @since 2014/3/11
 * Sub project with report.xml in the project explorer
 * 
 */
@ServiceProvider(service=ProjectFactory.class)
public class HomeSubProjectFactory implements ProjectFactory {

    public static final String PROJECT_FILE = "model.hom";

    //Specifies when a project is a project, i.e.,
    //if "customer.txt" is present in the parent folder
    //and the project has a subfolder named "src":
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_FILE) != null;
    }

    //Specifies when the project will be opened, i.e., if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new HomeSubProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        // leave unimplemented for the moment
    }

}
