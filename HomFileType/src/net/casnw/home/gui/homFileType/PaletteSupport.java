//@DECLARE@
package net.casnw.home.gui.homFileType;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.casnw.home.gui.palette.ModuleNode;
import net.casnw.home.gui.palette.MoudesXmlOperator;
import net.casnw.home.gui.palette.Util;
import net.casnw.home.modules.api.Module;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.nodes.NodeOperation;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author luolh & minyf
 * @since 2014/4/14 Palette
 *
 */
public class PaletteSupport {

    static String modulesFile = "C:\\modules.xml";

    public static PaletteController createPalette() {
        try {
            Module obj = new Module();
            Document doc;
            Element ERootNode;
            doc = MoudesXmlOperator.getDocument(modulesFile);

            //得到该XML文件的DOM树的根元素
            ERootNode = doc.getRootElement();

            obj.setName(ERootNode.attributeValue("name"));
            obj.setCode(ERootNode.attributeValue("id"));

            AbstractNode paletteRoot = new ModuleNode(obj, ERootNode);
            paletteRoot.setName("Palette Root");

            final PaletteController controller = PaletteFactory.createPalette(paletteRoot, new MyPaletteActions(), null, new MyDnDHandler());

            return controller;
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    private static final class MyPaletteActions extends PaletteActions {

        @Override
        public Action[] getImportActions() {
            return new Action[]{};
        }

        @Override
        public Action[] getCustomPaletteActions() {
            return new Action[]{};
        }

        @Override
        public Action[] getCustomCategoryActions(Lookup arg0) {
            return new Action[]{};
        }

        @Override
        public Action[] getCustomItemActions(Lookup arg0) {
            return new Action[]{};
        }

        @Override
        public Action getPreferredAction(Lookup item) {
            return new MFPaletteInsertAction(item);

        }

        private static class MFPaletteInsertAction extends AbstractAction {

            private Lookup item;

            MFPaletteInsertAction(Lookup item) {
                this.item = item;
            }

            public void actionPerformed(ActionEvent e) {
                final Module obj =  item.lookup(Module.class);
                System.out.println("cc==" + obj.getName());
                AbstractNode node = Util.getModuleNode(obj);
                node.setDisplayName(obj.getName());
                node.setShortDescription("Description of " + obj.getName());
                NodeOperation.getDefault().showProperties(node);
            }
        }

    }

    private static class MyDnDHandler extends DragAndDropHandler {

        public void customize(ExTransferable exTransferable, Lookup lookup) {
            try {
                Node node = lookup.lookup(Node.class);
                final Module m = (Module) exTransferable.getTransferData(Module.DATA_FLAVOR);
                System.out.println("ddd=" + m.getName());
                
                final Image image = node.getIcon(BeanInfo.ICON_COLOR_16x16);
                exTransferable.put(new ExTransferable.Single(DataFlavor.imageFlavor) {

                    protected Object getData() throws IOException, UnsupportedFlavorException {
                        return image;
                    }

                });
            } catch (UnsupportedFlavorException | IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }

}
