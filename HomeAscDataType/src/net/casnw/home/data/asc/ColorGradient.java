package net.casnw.home.data.asc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import java.awt.image.BufferedImage;
import javax.swing.JComponent;  


public class ColorGradient extends JComponent  {  
  
    /** 
     *  
     */  
   // private static final long serialVersionUID = -4134440495899912869L;  
    public BufferedImage image = null;  
    public BufferedImage images = null;
    public double ma = 0.0;
    public double mi = 0.0;
    public  String[]  data = null;
    public  Color[] colors = null;
    public  int[] colorline = null;
    public int[] rgbData = null;
    public int  rgbDatas[] = null;
    public  int dev ;
    
    public ColorGradient(int w, int h) {
        setSize(w, h);
        setImage(w, h);
    }
    
    public  void setData(String[]  datas){
        data = datas;
    }
    
    public void setImage(int w, int h){
         image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);  
         images = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);  
    }
    
    @Override 
    protected void paintComponent(Graphics g) {  
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        dev = image.getHeight()/(colors.length);
        g2.drawImage(getImages(colors),  image.getWidth()*3/4, 0, image.getWidth(), image.getHeight(), null);
        Font ft = new Font("SansSerif", 5, 10);
        g2.setFont(ft);
        
        for(int i= 0;i<= colors.length-1;i++){
           if(i==0){
               g2.drawString(data[i], 0, dev*i+10);
               g2.drawLine(image.getWidth()*5/8, dev*i, image.getWidth()*3/4, dev*i);
           }
           else{
               g2.drawString(data[i], 0, dev*i+10);
               g2.drawLine(image.getWidth()*5/8, dev*i, image.getWidth()*3/4, dev*i);
          }
        }
      
     
    }  
    
    public void setColor(Color[] c){
        colors = c;
    }
  
    private BufferedImage  getImages(Color[] cs){
        rgbData = new int[image.getWidth()*(image.getHeight()/colors.length )];   
        for(int i = 0;i<= cs.length-1;i++){
            if (i == cs.length -1) {
                Color L = cs[cs.length-1];
                int[] Cl = {L.getRed(),L.getGreen(),L.getBlue()};
                int a=255;  
                int index = 0;
                for(int row=0; row<dev; row++) {  
                     
                    for(int col=0; col<image.getWidth(); col++) {               
                        rgbData[index] = ((a & 0xff) << 24) |  
                              ((Cl[0] & 0xff) << 16) |  
                              ((Cl[1] & 0xff) << 8)  |  
                              ((Cl[2] & 0xff));  
    
                       index++;  
                    }             
                }
                
                setRGB(image,0, (cs.length-1)*(image.getHeight()/colors.length ),image.getWidth(),(image.getHeight()/colors.length ), rgbData); 
            }
            else{
                generateVGradientImage(rgbData,cs[i+1],cs[i]);  
            
                for(int is : rgbData){
                    setRGB(image,0, i*(image.getHeight()/colors.length ),image.getWidth(),(image.getHeight()/colors.length ), rgbData);  
                }
                
           }
       }
       return image;
   }
   
 
 
    private void generateVGradientImage(int[] rgbData,Color scolor ,Color color) {  
         int[] startColor = getStartColor(scolor);  
         int[] endColor = getEndColor(color);  
         int index = 0;
         float rr = startColor[0] - endColor[0];  
         float gg = startColor[1] - endColor[1];  
         float bb = startColor[2] - endColor[2];  
         int a=255;  
         int r=0, g=0, b= 0;  
      
         for(int row=0; row<dev; row++) {  
             for(int col=0; col<image.getWidth(); col++) {     
                 r = endColor[0] + (int)(rr*((float)row/dev));  
                 g = endColor[1] + (int)(gg * ((float)row/dev));  
                 b = endColor[2] + (int)(bb * ((float)row/dev));         
                 rgbData[index] = ((a & 0xff) << 24) |  
                              ((r & 0xff) << 16) |  
                              ((g & 0xff) << 8)  |  
                              ((b & 0xff));  
    
                index++;  
            }             
        }  
       
        
    }
      
        
   
   
    public int[] getStartColor(Color color) {  
        return new int[]{color.getRed(),color.getGreen(),color.getBlue()};  
    }  
      
    public int[] getEndColor(Color color) {  
        return new int[]{color.getRed(),color.getGreen(),color.getBlue()};  
    }  
      
    public void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {  
        int type = image.getType();  
        if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )  
            image.getRaster().setDataElements( x, y, width, height, pixels );  
        else  
            image.setRGB( x, y, width, height, pixels, 0, width );  
    }


}  