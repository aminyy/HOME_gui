/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.data.asc;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.EventQueue.invokeLater;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import static net.casnw.home.data.asc.NewColorSchema.colorbarid;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.filter.expression.ThisPropertyAccessorFactory;
import org.geotools.map.GridCoverageLayer;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.ColorMap;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.swing.JMapPane;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.opengis.referencing.FactoryException;
import org.openide.awt.UndoRedo;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_Asc_VISUAL",
        iconBase = "net/casnw/home/data/asc/calendar.png",
        mimeType = "text/x-asc",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "AscVisual",
        position = 2000
)

public final class AscVisualElement extends JMapPane implements MultiViewElement {
    
    static String fileName;
    MapContent content;
    GridCoverageLayer gcl;
    Style st;
    double minValue;
    double maxValue;
    static GridCoverage2D gcin;
    static double noData;
    static int width;
    static int height;
    boolean sw = false;
    boolean switchs = true;
    ColorSchema cs;
    ColorGradient gs;
    ColorGradient qs;
    final AscDataObject obj;
    private final JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private final StyleBuilder stylebuilder = new StyleBuilder();
    public static TrData tr = new TrData();

    //定义一个内部类来实现
    
    class wAdpt extends WindowAdapter{
       ColorGradient gs;
       public wAdpt(ColorGradient c) throws Exception{
           this.gs = c;  
       }
       
       @Override
        public void windowClosing(WindowEvent e){
           try {           
               sw = true;
               AscVisualElement.this.initColorBar();
               ;
           } catch (Exception ex) {
               Exceptions.printStackTrace(ex);
           }
               
               
             
          
               
          
           
           
       }
    
    }
    class ColorSchema extends NewColorSchema {
        
        //public ColorGradient ss;
        public MyListen ml;
        
        
        public ColorSchema(ColorGradient s) {
            ss = s;
            ml = new MyListen();
           
           
            
        }

        @Override
        public void init(MouseListener l) {
            super.init(l);
            try {
                ff.addWindowListener(new wAdpt(gs));
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            
        }
        
        public void update() {
            AscVisualElement.this.update();
        }

        class MyListen implements MouseListener {

            @Override
            public void mouseClicked(final MouseEvent e) {

                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        colorbarid = Integer.parseInt(e.getComponent().getName()) - 1;
                        cs = chooseColor(colorbarid);
                        AscVisualElement.tr.c = cs;
                        dataStrings = new String[cs.length];
                        
                        double ma ,mi; 
                        if ( maxValue < 1 && maxValue > -1) {
                             ma = AscVisualElement.this.maxValue;
                             mi = AscVisualElement.this.minValue;
                        }
                        else{
                             ma = Math.round(AscVisualElement.this.maxValue);
                             mi = Math.round(AscVisualElement.this.minValue);
                             
                        }
                       
                        
                        for (int i = 0; i < cs.length; i++) {
                            String temp = null;
                            temp = String.valueOf((ma - mi) / cs.length * i + mi);
                            dataStrings[i] = temp.substring(0, 4);
                        }

                        ColorGradient ss = new ColorGradient(50,170);
                        ColorBar jy = new ColorBar("101");
                        jy.setSize(50, 170);
                        jy.setColor(cs);

                        if (colorbarid < 100) {
                            ss.setColor(cs);
                            if (sw) {
                                     
                            }else
                            { 
                                ss.setData(dataStrings);
                            }
                            sw = false;  
                            AscVisualElement.this.jPanel2.removeAll();
                            AscVisualElement.this.jPanel2.add(ss);
                            ss.repaint();
                        } else {
                          
                           if (sw) {         
                            }
                           else
                            { 
                                jy.setData(dataStrings);
                            }
                            sw = false;   
                            AscVisualElement.this.jPanel2.removeAll();
                            AscVisualElement.this.jPanel2.add(jy);
                            jy.repaint();
                        }

                        ColorSchema.this.update();
                    }

                });

                switch (e.getComponent().getName()) {

                    case "1":
                        js.setBorder(new EtchedBorder());
                        break;

                    case "2":
                        js1.setBorder(new EtchedBorder());
                        break;

                    case "3":
                        js2.setBorder(new EtchedBorder());
                        break;

                    case "4":
                        js3.setBorder(new EtchedBorder());
                        break;

                    case "5":
                        js4.setBorder(new EtchedBorder());
                        break;

                    case "6":
                        js5.setBorder(new EtchedBorder());
                        break;

                    case "7":
                        js6.setBorder(new EtchedBorder());
                        break;

                    case "8":
                        js17.setBorder(new EtchedBorder());
                        break;

                    case "9":
                        js28.setBorder(new EtchedBorder());
                        break;

                    case "10":
                        js39.setBorder(new EtchedBorder());
                        break;

                    case "11":
                        js410.setBorder(new EtchedBorder());
                        break;

                    case "12":
                        js511.setBorder(new EtchedBorder());
                        break;

                    case "13":
                        js112.setBorder(new EtchedBorder());

                        break;

                    case "14":
                        js213.setBorder(new EtchedBorder());
                        break;

                    case "15":
                        js314.setBorder(new EtchedBorder());
                        break;

                    case "16":
                        js415.setBorder(new EtchedBorder());
                        break;

                    case "17":
                        js516.setBorder(new EtchedBorder());
                        break;

                    case "18":
                        js617.setBorder(new EtchedBorder());
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    public AscVisualElement(Lookup lkp) throws IOException, FactoryException, Exception {
        //ast = this;
        Color[] ce = {new Color(247, 252, 253), new Color(229, 245, 249), new Color(204, 236, 230), new Color(153, 216, 201),
            new Color(102, 194, 164), new Color(65, 174, 118), new Color(35, 139, 69), new Color(0, 109, 44), new Color(0, 68, 27)};
        tr.c = new Color[ce.length];
        tr.c = ce;
        obj = lkp.lookup(AscDataObject.class);
        initComponents();
        Asc2Tif();
        if (!sw) {
            initColorBar();    
        }
        update();

    }

    //在这里设置colorbar的参数
    private void initColorBar() throws InterruptedException, Exception {
        switchs = true;
        tr.c = new Color[]{new Color(247, 252, 253), new Color(229, 245, 249), new Color(204, 236, 230), new Color(153, 216, 201),
            new Color(102, 194, 164), new Color(65, 174, 118), new Color(35, 139, 69), new Color(0, 109, 44), new Color(0, 68, 27)};

        double minValue = 0.0;
        double maxValue = 0.0;
        double ma ,mi; 
        String[] dataStrings = new String[tr.c.length];
        if ( AscVisualElement.this.maxValue < 1 && AscVisualElement.this.maxValue > -1) {
            ma = AscVisualElement.this.maxValue;
            mi = AscVisualElement.this.minValue;
        }
        else{
            ma = Math.round(AscVisualElement.this.maxValue);
            mi = Math.round(AscVisualElement.this.minValue);
                             
        }
                       
        for (int i = 0; i < tr.c.length; i++) {
            String temp = null;
            temp = String.valueOf((ma - mi) / tr.c.length * i + mi);
            dataStrings[i] = temp.substring(0, 4);
        }
        gs = new ColorGradient(50,170);
        gs.setData(dataStrings);
        gs.setColor(tr.c);
        
        jPanel2.removeAll();
        jPanel2.add(gs);

        gs.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (switchs) {
                            cs = new ColorSchema(gs);
                            cs.init(cs.ml);
                        }
                        switchs = false;
                    }
                });
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
         if (sw) {
           sw = false;
           AscVisualElement.ColorSchema.cs = AscVisualElement.ColorSchema.chooseColor(colorbarid);
           AscVisualElement.tr.c =  AscVisualElement.ColorSchema.cs ;
           AscVisualElement.ColorSchema.dataStrings = new String[ AscVisualElement.ColorSchema.cs .length];
          
           ColorGradient ss = new ColorGradient(50,170);
           ColorBar jy = new ColorBar("101");
           jy.setSize(50, 170);
           jy.setColor( AscVisualElement.ColorSchema.cs);
           String[] colorAltitude = dataStrings;
           if (colorbarid < 100) {
               ss.setColor(AscVisualElement.ColorSchema.cs);
               ss.setData(colorAltitude);
               AscVisualElement.this.jPanel2.removeAll();
               AscVisualElement.this.jPanel2.add(ss);
               ss.repaint();
           }
           else{
               jy.setData(colorAltitude);
               AscVisualElement.this.jPanel2.removeAll();
               AscVisualElement.this.jPanel2.add(jy);
               jy.repaint();
           } 
           ss.addMouseListener(new MouseListener() {
               
                @Override
                public void mouseClicked(MouseEvent e) {
                    invokeLater(new Runnable() {

                        @Override
                        public void run() {

                            if (switchs) {
                                cs = new ColorSchema(gs);
                                cs.init(cs.ml);
                            }
                            switchs = false;
                        }
                    });
                        
              
                }

                @Override
                public void mousePressed(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
           
        }
     //  datas = cs.cs;
        //  cb.setcolor(datas);
        //   cb.repaint();

        gs.ma = maxValue;
        gs.mi = minValue;

    }

    public void Asc2Tif() throws IOException, Exception {
        fileName = obj.getPrimaryFile().getPath();
        File gridFile = new File(fileName);
        System.out.println(gridFile.getName());

        //final File f = TestData.file(this, "ArcGrid.asc");
        AbstractGridFormat format = GridFormatFinder.findFormat(gridFile);
        AbstractGridCoverage2DReader reader = format.getReader(gridFile);

        gcin = reader.read(null);
        width = (Integer) gcin.getProperty("image_width");
        height = (Integer) gcin.getProperty("image_height");
        noData = (Double) gcin.getProperty("GC_NODATA");
        content = new MapContent();
        double[] dest = new double[1];
        minValue = Float.MAX_VALUE;
        maxValue = Float.MIN_VALUE;

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                gcin.evaluate(new GridCoordinates2D(x, y), dest);

                if (dest[0] != noData) {
                    minValue = Math.min(minValue, dest[0]);
                    maxValue = Math.max(maxValue, dest[0]);
                }
            }
        }

        System.out.println(minValue);
        System.out.println(maxValue);

    }

    public void update() throws IllegalArgumentException {
        Color[] c = new Color[tr.c.length + 1];
        System.arraycopy(tr.c, 0, c, 1, tr.c.length);

        c[0] = Color.white;
        st = CreateRasterStyle(minValue, maxValue, c);

        gcl = new GridCoverageLayer(gcin, st, "aa");
        gcl.setVisible(true);

        content.dispose();
        content.addLayer(gcl);

        this.setRenderer(new StreamingRenderer());
        this.setMapContent(content);
        this.drawLayers(true);
    }

    private static void showMap(MapContent map) throws IOException {
        JMapPane mapPane = new JMapPane(map);

        JFrame frame = new JFrame("ImageLab2");

        frame.setLayout(new BorderLayout());
        frame.add(mapPane, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        JButton zoomInButton = new JButton("Zoom In");

        buttons.add(zoomInButton);

        JButton zoomOutButton = new JButton("Zoom Out");

        buttons.add(zoomOutButton);

        JButton pamButton = new JButton("Move");

        buttons.add(pamButton);

        frame.add(buttons, BorderLayout.NORTH);

        
        frame.setSize(600, 400);
        frame.setVisible(true);
        
        
        
        
        
        
    }

    private Style CreateRasterStyle(double min, double max, Color[] c) {
        /* 
         * Create a Style to display the Grid 
         */

        int band = c.length;
        String[] labels = new String[band];
        double[] quantities = new double[band];
        Color[] colors = c;

        /*  new Color(50, 0, 0),
         new Color(105, 0, 0),
         new Color(155, 0, 0),
         new Color(205, 55, 0),
         new Color(255, 105, 0),
         new Color(255, 155, 0),
         new Color(255, 205, 0),
         new Color(155, 255, 55),
         new Color(105, 255, 105),
         new Color(55, 205, 155),
         new Color(0, 155, 205),
         new Color(0, 105, 255),
         new Color(0, 55, 255),
         new Color(0, 0, 255),
         new Color(0, 0, 205)*/
        //   };
        int j = 0;
        for (int i = 0; i < c.length; i++) {
            labels[i] = "a" + i;
            quantities[i] = min + (max - min) / c.length * i;
        }

        ColorMap cm = stylebuilder.createColorMap(labels, quantities, colors, ColorMap.TYPE_INTERVALS);

        RasterSymbolizer rastersymbolizer = stylebuilder.createRasterSymbolizer(cm, 1);

        return stylebuilder.createStyle(rastersymbolizer);
    }

    /* public RenderedOp grayscaleToRGB(RenderedImage src) {
     double[][] matrix = {
     {1.0D, 0.0D},
     {2.0D, 1.0D},
     {3.0D, 0.0D}
     };

     ParameterBlock pb = new ParameterBlock();
     pb.addSource(src);
     //  pb.add(500);
     //   pb.add(300);

     pb.add(matrix);

     //          Perform the band combine operation. 
     return (RenderedOp) RenderedOp.wrapRenderedImage((RenderedImage) JAI.create("bandcombine", pb, null));
     }*/
    @Override
    public String getName() {
        return "AscVisualElement";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(379, 522));

        jPanel2.setPreferredSize(new java.awt.Dimension(50, 172));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(309, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(352, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return obj.getLookup();
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

}
