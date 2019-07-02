/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.casnw.home.data.asc;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


class ColorBar extends JPanel{
    public    Color[] c;
    public  String[]  data = null;
    public BufferedImage image = null;  
    public  int dev ;
    public ColorBar(String s){
        super.setName(s);
       //setBounds(0,0,20,100);
       
    } 
    
    public void setData(String[] d){
         image = new BufferedImage(50,150, BufferedImage.TYPE_INT_ARGB); 
         dev = image.getHeight()/(c.length-1);
         setSize(50, 170);
         data = d;
        
    }
   
  
    
    public void setColor(int[] x){
        c = new Color[x.length/3];
        
        for(int i= 0;i<= (x.length-3);i=i+3){
        c[i/3]=new Color(x[i],x[i+1],x[i+2]);
        }
    }
         
    public void setColor(Color[] colors){
       c =  colors;
    }
       
         
        @Override
    protected void paintComponent(Graphics g) {
       Graphics2D g2 =(Graphics2D ) g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
       
       if (data == null) {
           
       for(int i= 0; i<= c.length-1; i++){
           g2.setColor(c[i]);
           g2.drawRect(0, (int)(this.getSize().height/c.length)*i,(int) this.getSize().width, (int)this.getSize().height/c.length);
           g2.fillRect(0,(int)(this.getSize().height/c.length)*i,(int) this.getSize().width,(int)this.getSize().height/c.length);
           g2.setColor(Color.red);
           
       }
       
       }
       else{
           
        for(int i= 0; i<= c.length-1; i++){
           g2.setColor(c[i]);
           g2.drawRect((int) this.getSize().width*1/2, dev*i,(int) image.getWidth(),dev);
           g2.fillRect((int) this.getSize().width*1/2,dev*i,(int) image.getWidth(),dev);
         }
        
         Font ft = new Font("SansSerif", 5, 10);
         g2.setColor(Color.BLACK);
         g2.setFont(ft);
         for(int i= 0;i<= c.length-1;i++){
           if(i==0){
               g2.drawString(data[i], 0, dev*i+10);
               g2.drawLine(image.getWidth()*3/8, dev*i, image.getWidth()*1/2, dev*i);
           }
           else{
               g2.drawString(data[i], 0, dev*i+10);
               g2.drawLine(image.getWidth()*3/8, dev*i, image.getWidth()*1/2, dev*i);
           }
        }
           
      }
   }   
        
        
    
    
}
