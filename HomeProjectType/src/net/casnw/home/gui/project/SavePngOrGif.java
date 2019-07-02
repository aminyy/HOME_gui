package net.casnw.home.gui.project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import org.geotools.styling.Style;

/**
 *
 * @author dhs
 */
public class SavePngOrGif {
    List<File> files ;
    String filename = "C:\\alipay\\gg.png";
    Style[] styles;
    JPanel jp;
    JFrame jf;
   
    public void setFilename(List<File> file){
        //filename = file;
        files = file;
    }
    public void setTranslatedElement(JPanel j){
        jp = j;
    }
     public void setTranslatedElement(JFrame j){
        jf = j;
    }
    
    public void setStyles(){}
    
    public void toGif(String newfile){
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.setRepeat(0);
        e.start(newfile);
        BufferedImage[] bufferedImages = new BufferedImage[files.size()];
        for (int i = 0; i < files.size(); i++) {
            e.setDelay(200);
            try {
                bufferedImages[i] = ImageIO.read(files.get(i));
                e.addFrame(bufferedImages[i]);
            } catch (IOException ex) {
                Logger.getLogger(SavePngOrGif.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        e.finish();
    }
    
    public void saveAsPng(File f) throws IOException{
        
        BufferedImage im;
        
        if (jf != null) {
             im = new BufferedImage(jf.getWidth(), jf.getHeight(), BufferedImage.TYPE_INT_RGB);
             Graphics2D g2d = im.createGraphics();
             
             jf.paint(g2d);
             ImageIO.write(im, "jpg", f);
        }else if (jp != null) {
             im = new BufferedImage(jp.getWidth(), jp.getHeight(), BufferedImage.TYPE_INT_RGB);
             Graphics2D g2d = im.createGraphics();
             jp.paint(g2d);
             ImageIO.write(im, "jpg", f);
             
        }
        
    }
    /*以下为使用测试部分
    public static void main(String[] args) throws IOException{
   
       SavePngOrGif d = new SavePngOrGif();
       String[] ss = {"C:\\Users\\dhs\\Desktop\\util备份\\1.jpg","C:\\Users\\dhs\\Desktop\\util备份\\2.jpg"};
       d.setFilename(ss);
       d.toGif("C:\\alipay\\ff.gif");
    
    }
    */
}
