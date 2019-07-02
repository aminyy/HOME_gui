/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.project;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import static javax.swing.SwingUtilities.invokeLater;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.map.GridCoverageLayer;
import org.geotools.map.MapContent;
import org.geotools.styling.ColorMap;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.swing.JMapPane;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.project.Project;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.openide.DialogDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author Administrator
 */
public class Utils {
    
    List<FileObject> files;
    FileObject[] projectChildren;
    JFrame f;
    PriorityBlockingQueue<FileString> fileStringBuffers = new PriorityBlockingQueue<FileString>();
    ColorGradient gs;
    static String DefaultFileDictionary;
    private JToolBar toolbar = new JToolBar();  
    private StyleBuilder stylebuilder = new StyleBuilder();
    JFrame frame = new JFrame("ImageLab2");
    JMapPane mapPane;
    List<MapContent> mapFiles = new ArrayList<MapContent>();
    JLabel label;
    static boolean sws;
    static int number;
    //以下为更改代码：dhs
    SavePngOrGif savePngOrGif;
    Thread SavePicture;
    MapContent mc;
    String[] fileStrings;
    
    
    public void processCommand(String command, Project project) throws IllegalArgumentException, IOException {
        switch(command){
            case "display":display(project);
                return;
            case "run":run(command,project);
                return;
        }
    }
    public void run(String command, Project project){
        List<FileObject> files = new ArrayList<>();
        FileObject[] projectChildren = project.getProjectDirectory().getChildren();
        for (FileObject fileObject : projectChildren) {
            addFile(fileObject, files);
        }
        List homFile = new ArrayList();
        for (FileObject fo : files) {
            if ("text/hom+xml".equalsIgnoreCase(fo.getMIMEType())) {

                homFile.add(fo.getName());
                System.out.println("ddd=" + fo.getName());
            }
        }

        String s = (String) JOptionPane.showInputDialog(null, "请选择要运行的文件:\n", "文件", JOptionPane.PLAIN_MESSAGE, null, homFile.toArray(), homFile.get(0).toString());

        /* ChoseHomFile mp = new ChoseHomFile();
         Object[] options = {new JButton("Choice 1"),
         new JButton("Choice 2")};
         DialogDescriptor dd = new DialogDescriptor(mp,
         "Text in title",
         true,
         options,
         null,
         DialogDescriptor.DEFAULT_ALIGN,
         null,
         mp); //create new modal DialogDescriptor with defined ActionListener 
         mp.requestFocus(); // set focus to component which was specified in MyPanel's requestFocus() method 
         TopManager.getDefault().createDialog(dd).show(); //show dialog */
        
        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("cmd.exe").
                //addArgument(command).
                addArgument("/c").
                addArgument("java -jar home.jar "+s+".hom").
                workingDirectory(FileUtil.toFile(project.getProjectDirectory()));

        ExecutionDescriptor descriptor = new ExecutionDescriptor().
                frontWindow(true).
                showProgress(true).
                controllable(true);
        ExecutionService service = ExecutionService.newService(
                processBuilder,
                descriptor,
                command);
        service.run();    
    }
    
    public void display(Project project) throws IllegalArgumentException, IOException{
         DefaultFileDictionary = project.getProjectDirectory().getPath();
         files = new ArrayList<>();
         projectChildren = project.getProjectDirectory().getChildren();
         invokeLater(new Runnable() {

            @Override
            public void run() {
               
        for (FileObject fileObject : projectChildren) {
            if(fileObject.getChildren()!=null){
                for(FileObject f : fileObject.getChildren()){
                    if("text/x-asc".equalsIgnoreCase(f.getMIMEType())){
                        addFile(f,files);
                    }
                }
            }else{
                 if("text/x-asc".equalsIgnoreCase(fileObject.getMIMEType())){
                        addFile(fileObject,files);
                    }     
            }          
        }
        
        //以下是增加的排序代码：dhs
        for (int i = 0 ; i < files.size();i++) {
            System.out.println("size"+files.size()+"file:"+files.get(i));
            FileObject fileObject = files.get(i);
          
            fileStringBuffers.add(new FileString(fileObject.getPath(),fileObject.getName()));
            System.out.println("fileStringBuffers size"+fileStringBuffers.size()+"fileStringBuffers:"+fileStringBuffers.peek().FileName);
        }
       
       if (!NewJPanel.isOpen) {
            NewJPanel np = new NewJPanel();             
            np.setJlist(fileStringBuffers);
       
            np.setLisentener(new CurrentLisentner());
            f = new JFrame();
            f.add(np);
       
            f.pack();
            f.setVisible(true);
            f.setResizable(false);
            
            f.addWindowListener(new WindowAdapter() {
                 
                 
                 @Override
                 public void windowClosing(WindowEvent e){  
                     NewJPanel.isOpen = false;        
                 }
            
         
              });  
       }    
            }
        });
        
       //System.out.println("++++++");
        //
   
       //NewJFrame nd = new NewJFrame();
       //System.out.println("++++++");
       //System.out.println("++++++");
       //nd.setSize(200,300);
       //nd.setVisible(true);
      // nd.setJlist(fileStringBuffers);
       //nd.setLisentener(new CurrentLisentner());
        //以下为更改代码
  

    }
    
class  CurrentLisentner extends MouseAdapter{
        
        @Override
        public void mousePressed(MouseEvent e){
            invokeLater(new Runnable() {

                @Override
                public void run() {
                    
                    try {
                        Utils.this.displayAfter();
                        Utils.this.f.dispose();
                        NewJPanel.isOpen = false;
                    } catch (IllegalArgumentException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }   
                    
                }
            });
           
        }   
    }
    
public void displayAfter() throws IllegalArgumentException, IOException{
   
      while(!fileStringBuffers.isEmpty()){
        FileString fo = fileStringBuffers.peek();
        fileStringBuffers.remove();
        System.out.println("22222222"+ fileStringBuffers.size());
        File gridFile = new File(fo.FilePath);
        System.out.println("111111111111"+fo.FileName);


        AbstractGridFormat format = GridFormatFinder.findFormat(gridFile);
        AbstractGridCoverage2DReader reader = format.getReader(gridFile);

        GridCoverage2D gcin = reader.read(null);

        int width = (Integer) gcin.getProperty("image_width");
        int height = (Integer) gcin.getProperty("image_height");
        double noData = (Double) gcin.getProperty("GC_NODATA");
        double[] dest = new double[1];
        double minValue = Float.MAX_VALUE;
        double maxValue = Float.MIN_VALUE;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gcin.evaluate(new GridCoordinates2D(x, y),
                        dest);
                if (dest[0] != noData) {
                    minValue = Math.min(minValue, dest[0]);
                    maxValue = Math.max(maxValue, dest[0]);
                }
            }
        }


        int[] a = gcin.getOptimalDataBlockSizes();
        String[] name = gcin.getPropertyNames();

        CoordinateReferenceSystem crs = gcin.getCoordinateReferenceSystem2D();

        Style st = CreateRasterStyle(minValue, maxValue);

        GridCoverageLayer gcl = new GridCoverageLayer(gcin, st, "aa");

        gcl.setVisible(true);
        MapContent content = new MapContent();

        content.addLayer(gcl);
        content.setTitle(new String(fo.FileName));

        mapFiles.add(content);
        //this.setRenderer(new StreamingRenderer());

        //this.setMapContent(content);
        //this.drawLayers(true);

            
         
        //this.setToolTipText("aaa");
    }
      mapPane = new JMapPane(null);
      JPanel jp = new JPanel();
      jp.setLayout(new BorderLayout()); 
      jp.add(mapPane,BorderLayout.CENTER);
      JPanel buttons = new JPanel();
      label = new JLabel("displaying");
      buttons.add(label);           
      jp.add(buttons, BorderLayout.NORTH);
      jp.setSize(600,400);
      frame.setLayout(null);
      frame.setSize(700,400);
      jp.setBounds(0,0,600,400);
       
      gs.setBounds(600, 220, 50, 170); 
      frame.add(jp);
      frame.add(gs);
      frame.setVisible(true);
      savePngOrGif = new SavePngOrGif();
      savePngOrGif.setTranslatedElement(frame);
      new File(DefaultFileDictionary+"/images").mkdir();
      sws = false;
      number = 0;
      
      new Thread(){
          @Override
          public void run(){
                   ArrayList<File> fs = new ArrayList<File>();
                                
                   for(int i = 0 ;i < mapFiles.size();i++){
                       number++;
                      //以下为更改代码：dhs
                       mc = mapFiles.get(i);
                       
                       if (label.getText() != "displaying") {
                           try { 
                               File f = new File(DefaultFileDictionary+"/images/"+label.getText()+".jpg");
                               fs.add(f);
                               savePngOrGif.saveAsPng(f);  
                           } catch (IOException ex) {
                               Exceptions.printStackTrace(ex);
                           }
                   
                        } 
                         
                       invokeLater(new Runnable() {

                           @Override
                           public void run() {
                               label.setText(mc.getTitle().substring(0,4)+mc.getTitle().substring(4));  
                               mapPane.setMapContent(mc);   
                           
                           }
                       });
                       
                      
                       
                       try {
                           Thread.sleep(2000);
                       } catch (InterruptedException ex) {
                           Exceptions.printStackTrace(ex);
                       }
                       
                   
                  
                      if (number == mapFiles.size()-1) {
                      fileStrings = new String[fileStringBuffers.size()];
                  
                      int ii = 0;
                      for (FileString stringBuffer: fileStringBuffers) {
                          fileStrings[ii++] = new String(stringBuffer.FilePath.substring(ii, ii)+".jpg"); 
                      }
                    
                      for (String string : fileStrings) {
                          System.out.println(string+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                      }
                      
                      String[]  sds = {"F://wangye//1.jpg","F://wangye//2.jpg","F://wangye//3.jpg","F://wangye//4.jpg","F://wangye//5.jpg","F://wangye//6.jpg","F://wangye//7.jpg","F://wangye//8.jpg","F://wangye//9.jpg"};
                      
                      
                     }
                       
              }
                   
              try {  
                   File f = new File(DefaultFileDictionary+"/images/"+label.getText()+".jpg");
                   fs.add(f);
                   savePngOrGif.saveAsPng(f);  
                   
                   savePngOrGif.setFilename(fs);
                   savePngOrGif.toGif(DefaultFileDictionary+"/images/"+label.getText().substring(0, 10)+".gif");    
              } catch (IOException ex) {
                  Exceptions.printStackTrace(ex);
              }
    
          }      
     }.start(); 
     
      
      
      
      
 
     
     
     }
    
    private Style CreateRasterStyle(double min, double max) {
        /* 
         * Create a Style to display the Grid 
         */

        int band = 10;
        String[] labels = new String[band];
        double[] quantities = new double[band];
        Color[] colors = new Color[]{
             //153,216,201,102,194,164,65,174,118,255,255,127,255,255,127,255,255,0,252,141,89,239,101,72,215,48,31
            new Color(153,216,201),
            //new Color(205, 55, 255),
            new Color(102,194,164),
           // new Color(105, 155, 205),
            new Color(65,174,118),
           // new Color(0, 255, 105),
            new Color(255,255,127),
           // new Color(0, 255, 0),
            new Color(255,255,127),
           // new Color(105, 155, 0),
            new Color(255,255,0),
           // new Color(205, 55, 105),
            new Color(252,141,89),
           // new Color(255, 0, 205),
            new Color(239,101,72),
            new Color(215,48,31)
        }; 
        
        Color[] color =colors;
        gs = new ColorGradient(50, 170);
        
        String[] dataStrings = new String[color.length];
        double ma ,mi;
                         
        if ( min < 1 && max > -1) {
            ma = min;
            mi = max;
        }
        else{
            ma = Math.round(min);
            mi = Math.round(max);
                             
       }
                       
                        
      for (int i = 0; i < color.length; i++) {
           String temp = null;
           temp = String.valueOf((ma - mi) / color.length * i + mi);
           dataStrings[i] = temp.substring(0, 4);
      }
      gs.setData(dataStrings);
      gs.setColor(color);
      int j = 0;
      for (int i = 0; i < colors.length+1; i++) {
          labels[i] = "a" + i;
          quantities[i] = min + (max - min) / (colors.length+1) * i;
     }
    Color[] c = new Color[]{Color.white,colors[0],colors[1],colors[2],colors[3],colors[4],colors[5],colors[6],colors[7],colors[8]};
    ColorMap cm = stylebuilder.createColorMap(labels, quantities, c, ColorMap.TYPE_INTERVALS);

    RasterSymbolizer rastersymbolizer = stylebuilder.createRasterSymbolizer(cm, 1);

    return stylebuilder.createStyle(rastersymbolizer);
    }
    private void addFile(FileObject file,  List<FileObject> result) {
        if (file != null) {
            result.add(file);
        }
    }

}