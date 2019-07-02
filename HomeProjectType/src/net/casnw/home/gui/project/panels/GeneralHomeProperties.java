//@DECLARE@
package net.casnw.home.gui.project.panels;

import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author luolh
 * @since 2014/3/11
 * HOME project properties panel
 * 
 */
public class GeneralHomeProperties 
    implements ProjectCustomizer.CompositeCategoryProvider {

    private static final String GENERAL = "HOME项目属性";

    @ProjectCustomizer.CompositeCategoryProvider.Registration(
            projectType = "net-casnw-home-gui-project", position = 10)
    public static GeneralHomeProperties createGeneral() {
        return new GeneralHomeProperties();
    }

    @NbBundle.Messages("LBL_Config_General=General")
    @Override
    public Category createCategory(Lookup lkp) {
        return ProjectCustomizer.Category.create(
                GENERAL,
                Bundle.LBL_Config_General(),
                null);
    }

    @Override
    public JComponent createComponent(Category category, Lookup lkp) {
        return new JPanel();
    }

}
