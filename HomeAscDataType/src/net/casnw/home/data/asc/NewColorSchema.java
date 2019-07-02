package net.casnw.home.data.asc;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;




/**
 *
 * @author dhs
 */
public class NewColorSchema {   
    public   ColorGradient ss;
    
    Button jj,jse,jr;
    JPanel j,j1,j2,je ;    
    public JFrame ff;
    //QualitativeColorBar qs;
    static int colorbarid;
    static ColorBean bean;
    static ColorBar js,js1,js2,js3,js4,js5,js6,js17,
        js28,js39,js410,js511,js112,js213,js314, js415,js516,js617;
    
    public static String[] dataStrings;
    static public Color[] cs;
    MouseListener l;
    

   // private static NewColorSchema nc =new NewColorSchema();
  

 

    public NewColorSchema(){}
    public void init(MouseListener listener){
        l= listener;
        setLayout();
    }

    public static  Color[] chooseColor(int cc){
        
        if (cc >= 100) {
            int i = cc -100;
            return bean.colorBars.get(i);       
        }
        else {
           int[] y=  bean.data[cc];
           Color[] c = {new Color(247,252,253),new Color(229,245,249),new Color(204,236,230),new Color(153,216,201),new Color(102,194,164),new Color(65,174,118),new Color(35,139,69),new Color(0,109,44),new Color(0,68,27)};
           c = new Color[y.length/3];
           
           for(int i= 0;i<= (y.length-3);i=i+3){
               c[i/3]=new Color(y[i],y[i+1],y[i+2]);
           }
           return c;
        }
          
}




    void setLayout(){
        bean = new ColorBean();
        
        j = new JPanel();
        j.setSize(180,200);
        j.setLayout(new GridLayout(2,9));
        
        j1 = new JPanel();
        j1.setSize(180,200);
        j1.setLayout(new GridLayout(2,9));
        
        j2 = new JPanel();
        j2.setSize(180,200);
        j2.setLayout(new GridLayout(2,9));
        
       // JPanel j2 ;
       // j2 = new JPanel();
       // j2.setSize(180,200);
      //  j2.setLayout(new GridLayout(2,9));
                 
        ColorBar jt1  = new ColorBar("101");
        jt1.setColor(bean.colorBars.get(0));
        jt1.setSize(20, 100);
        jt1.addMouseListener(l);
        j1.add(jt1);
               
        ColorBar jt2  = new ColorBar("102");
        jt2.setColor(bean.colorBars.get(1));
        jt2.setSize(20, 100);
        jt2.addMouseListener(l);
        j1.add(jt2);
        
        
        ColorBar jt3  = new ColorBar("103");
        jt3.setColor(bean.colorBars.get(2));
        jt3.setSize(20, 100);
        jt3.addMouseListener(l);
        j1.add(jt3);
           
        /*
        ColorBar jt3  = new ColorBar("103");
        jt3.setColor(bean.colorBars.get(2));
       // jt3.setSize(20, 100);
        jt3.addMouseListener(l);
        j1.add(jt3);
               
        ColorBar jt4  = new ColorBar("104");
        jt4.setColor(bean.colorBars.get(3));
       // jt4.setSize(20, 100);
        jt4.addMouseListener(l);
        j1.add(jt4);
               
        ColorBar jt5  = new ColorBar("105");
        jt5.setColor(bean.colorBars.get(4));
        //jt5.setSize(20, 100);
        jt5.addMouseListener(l);
        j1.add(jt5);
               
        ColorBar jt6  = new ColorBar("106");
        jt6.setColor(bean.colorBars.get(5));
       // jt6.setSize(20, 100);
        jt6.addMouseListener(l);
        j1.add(jt6);
               
        ColorBar jt7  = new ColorBar("107");
        jt7.setColor(bean.colorBars.get(6));
       // jt7.setSize(20, 100);
        jt7.addMouseListener(l);
        j1.add(jt7);
               
        ColorBar jt8  = new ColorBar("108");
        jt8.setColor(bean.colorBars.get(7));
        //jt8.setSize(20, 100);
        jt8.addMouseListener(l);
        j1.add(jt8);
               
        ColorBar jt9  = new ColorBar("109");
        jt9.setColor(bean.colorBars.get(8));
        //jt9.setSize(20, 100);
        jt9.addMouseListener(l);
        j1.add(jt9);
        */
        for (int i = 4; i <= 18; i++) {
            j1.add(new Panel());
        }
        
        /*
        ColorBar jt10  = new ColorBar("110");
        jt10.setColor(bean.colorBars.get(9));
        //jt10.setSize(20, 100);
        jt10.addMouseListener(l);
        j2.add(jt10);
               
        ColorBar jt11  = new ColorBar("111");
        jt11.setColor(bean.colorBars.get(10));
        //jt11.setSize(20, 100);
        jt11.addMouseListener(l);
        j2.add(jt11);
               
        ColorBar jt12  = new ColorBar("112");
        jt12.setColor(bean.colorBars.get(11));
        //jt12.setSize(20, 100);
        jt12.addMouseListener(l);
        j2.add(jt12);
         
        ColorBar jt13  = new ColorBar("113");
        jt13.setColor(bean.colorBars.get(12));
        //jt13.setSize(20, 100);
        jt13.addMouseListener(l);
        j2.add(jt13);
        */
        for (int i = 5; i <= 18; i++) {
            j2.add(new Panel());
        }
               
        js  = new ColorBar("1");
        js.setColor(bean.data[0]);
        js.addMouseListener(l);
        j.add(js);
        
        js1  = new ColorBar("2");
        js1.setColor(bean.data[1]);
        js1.addMouseListener(l);
        j.add(js1);
            
        js2  = new ColorBar("3");
        js2.setColor(bean.data[2]);
        js2.addMouseListener(l);
        j.add(js2);
        
        js3  = new ColorBar("4");
        js3.setColor(bean.data[3]);
        js3.addMouseListener(l);
        j.add(js3);
        
        js4  = new ColorBar("5");
        js4.setColor(bean.data[4]);
        js4.addMouseListener(l);
        j.add(js4);
        
        js5  = new ColorBar("6");
        js5.setColor(bean.data[5]);
        js5.addMouseListener(l);
        j.add(js5);
        
        js6  = new ColorBar("7");
        js6.setColor(bean.data[6]);
        js6.addMouseListener(l);
        j.add(js6);
        
        js17  = new ColorBar("8");
        js17.setColor(bean.data[7]);
        js17.addMouseListener(l);
        j.add(js17);
        
        js28  = new ColorBar("9");
        js28.setColor(bean.data[8]);
        js28.addMouseListener(l);
        j.add(js28);
               
        js39  = new ColorBar("10");
        js39.setColor(bean.data[9]);
        js39.addMouseListener(l);
        j.add(js39);
        
        js410  = new ColorBar("11");
        js410.setColor(bean.data[10]);
        js410.addMouseListener(l);
        j.add(js410);
        
        js511  = new ColorBar("12");
        js511.setColor(bean.data[11]);
        js511.addMouseListener(l);
        j.add(js511);
        
        js112  = new ColorBar("13");
        js112.setColor(bean.data[12]);
        js112.addMouseListener(l);
        j.add(js112);
        
        js213  = new ColorBar("14");
        js213.setColor(bean.data[13]);
        js213.addMouseListener(l);
        j.add(js213);
        
        js314  = new ColorBar("15");
        js314.setColor(bean.data[14]);
        js314.addMouseListener(l);
        j.add(js314);
        
        js415  = new ColorBar("16");
        js415.setColor(bean.data[15]);
        js415.addMouseListener(l);
        j.add(js415);
        
        js516  = new ColorBar("17");
        js516.setColor(bean.data[16]);
        js516.addMouseListener(l);
        j.add(js516);
        
        js617  = new ColorBar("18");
        js617.setColor(bean.data[17]);
        js617.addMouseListener(l);
        j.add(js617); 
        
        ff = new JFrame("newcolorbar");
        JTabbedPane tab = new JTabbedPane();
        tab.add(j,"homochromy");
        tab.add(j1,"multicolour");
        //tab.add(j2,"qualitative");
        ff.add(tab);
        ff.pack();
        ff.setSize(180,300);
        ff.setVisible(true);
        
      
       
        
    }
        
    
}

  
