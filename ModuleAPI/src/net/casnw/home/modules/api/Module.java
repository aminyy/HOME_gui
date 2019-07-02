package net.casnw.home.modules.api;

import net.casnw.home.meta.ModuleMetaObj;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import net.casnw.home.io.ModuleDescription;
import net.casnw.home.model.Model;
import org.dom4j.Element;

public class Module implements Cloneable, Transferable {

    public static DataFlavor DATA_FLAVOR = new DataFlavor(Module.class, "module");

    private String name;
    private String code;
    private String xmlfile;
    private ModuleMetaObj mmo;
    private Image icon;
    private String imagePath;
    private ModuleDescription md;
    private Element ele;
    private Model modle;

    @Override
    public DataFlavor[] getTransferDataFlavors() {

        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor == DATA_FLAVOR;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
            IOException {
        if (flavor == DATA_FLAVOR) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the xmlfile
     */
    public String getXmlfile() {
        return xmlfile;
    }

    /**
     * @param xmlfile the xmlfile to set
     */
    public void setXmlfile(String xmlfile) {
        this.xmlfile = xmlfile;
    }

    /**
     * @return the mmo
     */
    public ModuleMetaObj getMmo() {
        return mmo;
    }

    /**
     * @param mmo the mmo to set
     */
    public void setMmo(ModuleMetaObj mmo) {
        this.mmo = mmo;
    }

    public Image getIcon() {
        return icon;
    }

    /**
     * @param image the image to set
     */
    public void setIcon(Image icon) {
        this.icon = icon;
        //  this.icon =  new ImageIcon(imagePath).getImage();
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.icon = new ImageIcon(imagePath).getImage();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Module m = null;
        try {
            m = (Module) super.clone();   //浅复制  
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        m.DATA_FLAVOR = (DataFlavor) DATA_FLAVOR.clone();
        m.mmo = (ModuleMetaObj) mmo.clone();   //深度复制  
        return m;
    }

    /**
     * @return the md
     */
    public ModuleDescription getMd() {
        return md;
    }

    /**
     * @param md the md to set
     */
    public void setMd(ModuleDescription md) {
        this.md = md;
    }

    /**
     * @return the ele
     */
    public Element getEle() {
        return ele;
    }

    /**
     * @param ele the ele to set
     */
    public void setEle(Element ele) {
        this.ele = ele;
    }
    private List listeners = Collections.synchronizedList(new LinkedList());

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

    /**
     * @return the modle
     */
    public Model getModle() {
        return modle;
    }

    /**
     * @param modle the modle to set
     */
    public void setModle(Model modle) {
        this.modle = modle;
    }

}
