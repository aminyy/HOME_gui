/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.homFileType;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import net.casnw.home.gui.utils.Common;
import net.casnw.home.gui.utils.DataPoolOperation;
import net.casnw.home.modules.api.Module;
import net.casnw.home.modules.api.Utils;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.dom4j.Element;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ComponentWidget;

/**
 *
 * @author luolh & zxr
 */
public class GraphSceneImpl extends GraphScene<Module, String> implements ActionListener{

    private LayerWidget mainLayer;
    private WidgetAction editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor());
    private Module module;
    private LayerWidget connectionLayer;
    private LayerWidget interactionLayer;
    private SceneLayout layout;
    private LayerWidget backgroundLayer = new LayerWidget(this);
    private Router router = RouterFactory.createFreeRouter();
    private HomFileDataObject obj;
    private JComboBox mapChoices;
    private WidgetAction normalMoveAction = ActionFactory.createMoveAction();
    private int pos = 0;
    private String edge="sequence";
    private DataPoolOperation dpo;
   // private static final Border BORDER_4 = org.netbeans.api.visual.border.BorderFactory.createLineBorder (4);
  //  private EdgeMenu edgeMenu=new EdgeMenu(this); 
    private WidgetAction selectAction = ActionFactory.createSelectAction(new ObjectSelectProvider());
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider(this));
    private WidgetAction moveControlPointAction = ActionFactory.createFreeMoveControlPointAction();
    private int edgeID = 0; 
    public GraphSceneImpl(final HomFileDataObject obj) {
        this.obj = obj;
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);
        interactionLayer = new LayerWidget(this);
        addChild(interactionLayer);
        //setKeyEventProcessingType (EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
        layout = LayoutFactory.createSceneGraphLayout (this, new GridGraphLayout<Module,String> ());//布局
        layout.invokeLayout ();
        
        mapChoices = new JComboBox(new String[]{"sequence","contain"});
        mapChoices.addActionListener((ActionListener) this);
        mainLayer.addChild(createMoveableComponent(mapChoices));
        getActions().addAction(
                ActionFactory.createAcceptAction(new AcceptProvider() {//创建得理拖放

            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {//确定拖放操作是否能够发生，如果返回true 调用accept 
                Image dragImage = getImageFromTransferable(transferable);
                JComponent view = getView();
                Graphics2D g2 = (Graphics2D) view.getGraphics();
                Rectangle visRect = view.getVisibleRect();
                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
                g2.drawImage(dragImage,
                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
                                point.getLocation().getY()),
                        null);
                return ConnectorState.ACCEPT; //接受一个连接。
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                try {
                    Image image = getImageFromTransferable(transferable);
                    module = (Module) transferable.getTransferData(Module.DATA_FLAVOR);
                    module.setIcon(image);
                    Module m = (Module) module.clone();
                    Widget w = GraphSceneImpl.this.addNode(m);
                    w.setPreferredLocation(widget.convertLocalToScene(point));
                } catch (UnsupportedFlavorException | IOException | CloneNotSupportedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }));

        getActions().addAction(ActionFactory.createZoomAction());//鼠标中键的滚动缩放视图
        getActions().addAction(ActionFactory.createPanAction());//通过鼠标移动来卷起视图
        getActions().addAction(ActionFactory.createMoveAction());
      //  getActions().addAction(ActionFactory.createPopupMenuAction(edgeMenu)); 
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
       // getActions().addAction(new KeyboardMoveAction());

    }
    
      public void layout () {
        layout.invokeLayoutImmediately ();
    }

    @Override
    protected Widget attachNodeWidget(Module node) {//由执行addNode方法调用前的节点注册获得一个小部件，是要代表场景的节点。        
        IconNodeWidget widget = new IconNodeWidget(this);       
        widget.setImage(node.getIcon());            
        widget.setLabel(node.getName());       

        widget.getActions().addAction(
                ActionFactory.createExtendedConnectAction(//创建一个具有特定装饰的扩展连接动作
                        connectionLayer, new MyConnectProvider()));
        widget.getActions().addAction(
                ActionFactory.createAlignWithMoveAction(//用于在移动过程中对齐widget
                        mainLayer, interactionLayer,
                        ActionFactory.createDefaultAlignWithMoveDecorator()));
        //double-click, the event is consumed while double-clicking only:
        widget.getLabelWidget().getActions().addAction(editorAction);

        //single-click, the event is not consumed:
        widget.getActions().addAction(createSelectAction());

        //mouse-dragged, the event is consumed while mouse is dragged:
        widget.getActions().addAction(ActionFactory.createMoveAction());
        widget.getActions().addAction(createWidgetHoverAction ());

        //mouse-over, the event is consumed while the mouse is over the widget:
        widget.getActions().addAction(createObjectHoverAction());
//<editor-fold defaultstate="collapsed" desc="comment">
        
//</editor-fold>
        widget.getActions().addAction(ActionFactory.createPopupMenuAction(
                new WidgetPopupMenu(GraphSceneImpl.this,obj)));
        mainLayer.addChild(widget);
        return widget;       
    } 
        
    private Image getImageFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
        } catch (UnsupportedFlavorException ex) {
        }
        return o instanceof Image ? (Image) o : ImageUtilities.loadImage("org/netbeans/shapesample/palette/shape1.png");
    }
    
      protected void addEdge (Module sourceNode, Module targetNode,String e) {
        String id = e+ (edgeID ++);
        System.out.println("addEdge=====id＝＝"+e);
        addEdge (id);
        setEdgeSource (id, sourceNode);
        setEdgeTarget (id, targetNode);
    }

    @Override
    protected Widget attachEdgeWidget(String e) {//由addEdge方法称为前边缘被注册以获取部件，是要表示在场景边缘
       ConnectionWidget connection = new ConnectionWidget(this);
        connection.setRouter(router);
        if((e.substring(0, 7)).equals("contain")){
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);//包含
        }
        if(e.substring(0,8).equalsIgnoreCase("sequence")){
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_HOLLOW);//顺序
        }
        connection.getActions().addAction(reconnectAction);
        connection.getActions().addAction(createSelectAction());
        //connection.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
        connection.getActions ().addAction (ActionFactory.createMoveAction ());
        connection.getActions ().addAction (createWidgetHoverAction ());
       // connection.getActions().addAction(moveControlPointAction);
      // connection.getActions().addAction(ActionFactory.createPopupMenuAction(edgeMenu));
        connectionLayer.addChild(connection);
        return connection;
    }
  
    @Override
    protected void attachEdgeSourceAnchor(String e, Module oldSourceNode,Module sourceNode) {//由setEdgeSource方法调用，以通知有关更改边缘源中的图模型。
        System.out.println("call attachEdgeSourceAnchor");
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(e);
        Widget sourceNodeWidget = findWidget(sourceNode);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor(sourceNodeWidget);
        edgeWidget.setSourceAnchor(sourceAnchor);
    }

    @Override
    protected void attachEdgeTargetAnchor(String e, Module oldTargetNode, Module targetNode) {//由setEdgeTarget方法调用，以通知有关更改目标边缘图中的模型。
        System.out.println("call attachEdgeTargetAnchor");
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(e);
        Widget targetNodeWidget = findWidget(targetNode);
        Anchor targetAnchor = AnchorFactory.createRectangularAnchor(targetNodeWidget); //AnchorShape.TRIANGLE_HOLLOW
        edgeWidget.setTargetAnchor(targetAnchor);
    }

    private Widget createMoveableComponent(Component component ) {//可移动下拉选择框
        Widget widget = new Widget(this);
        widget.setLayout(LayoutFactory.createVerticalFlowLayout());
        widget.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        widget.getActions().addAction(normalMoveAction);
        LabelWidget label = new LabelWidget(this, "Choice:");
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        widget.addChild(label);
        widget.addChild(new ComponentWidget(this, component));
        pos += 10;
        widget.setPreferredLocation(new Point(10, pos));
        return widget;
    }

    public void actionPerformed(ActionEvent e) {
       System.out.println("actionPerformed start");
       mapChoices = (JComboBox) e.getSource();          
       if (mapChoices.getSelectedItem().equals("contain")) {
           edge="contain";
         System.out.println("actionPerformed===edge=contain="+edge);
       }
       if(mapChoices.getSelectedItem().equals("sequence")){
           edge="sequence";
          System.out.println("actionPerformed===edge=sequence="+edge);
       }
    }

    private class LabelTextFieldEditor implements TextFieldInplaceEditor {//编辑的文本字段的inteface

        @Override
        public boolean isEnabled(Widget widget) {
            return true;
        }

        @Override
        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        @Override
        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
        }

    }

    private class MyConnectProvider implements ConnectProvider {

        @Override
        public boolean isSourceWidget(Widget source) { //检查源连接的来源 
             System.out.println("==========isSourceWidget==");
             boolean isSource=true;
             Module node = (Module)GraphSceneImpl.this.findObject(source);
             System.out.println("node=="+node.getMmo().getModuleClass());
           //  System.out.println("flag=="+flag);
            if("contain".equalsIgnoreCase(edge)){//包含关系
              if(Utils.isContext(node.getMmo().getModuleClass())==1) isSource=true;
              if(Utils.isContext(node.getMmo().getModuleClass())==0) isSource=false;
            }
           if("sequence".equalsIgnoreCase(edge)){//顺序关系   
            isSource=  Utils.isContext(node.getMmo().getModuleClass())==1|| Utils.isContext(node.getMmo().getModuleClass())==0;   
               System.out.println("isSourceWidget==flag==0=="+isSource);
           }
           return isSource;    
            //return node instanceof Module && source != null ? true : false;       
        }

        @Override
        public ConnectorState isTargetWidget(Widget src, Widget trg) {//检查源和目标之间是否可以创建连接
            System.out.println("=========isTargetWidget===========");
             Module nodeS = (Module)GraphSceneImpl.this.findObject(src);
             Module nodeT = (Module)GraphSceneImpl.this.findObject(trg);
             
            return src != trg && trg instanceof IconNodeWidget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
        }

        @Override
        public boolean hasCustomTargetWidgetResolver(Scene arg0) {//目标解析，确定在什么地方调用
             System.out.println("=========hasCustomTargetWidgetResolver===========");
            return false;
        }

        @Override
        public Widget resolveTargetWidget(Scene arg0, Point arg1) {//找到目标
            System.out.println("=========resolveTargetWidget===========");
            return null;
        }
      
        @Override
        public void createConnection(Widget source, Widget target){
         System.out.println("=========createConnection===========");
         System.out.println(edge);
         String id = edge+ (edgeID ++);
         int index = 0;
         Element ele = null;
        System.out.println("createConnecton====createConnecton＝＝"+edge);
        addEdge (id);
        Module sourceNode=(Module)GraphSceneImpl.this.findObject(source);
        Module targetNode=(Module)GraphSceneImpl.this.findObject(target);        
        setEdgeSource (id, sourceNode);
        setEdgeTarget (id, targetNode);
          //修改hom文件
            try {             
             if("contain".equalsIgnoreCase(edge)){//包含
               index= (sourceNode.getEle()).elements().size(); //插入位置
               System.out.println("index="+index);
               ele=sourceNode.getEle();
               System.out.println("ele===flag=1===="+ele);
              }
             if("sequence".equalsIgnoreCase(edge)){//顺序
                System.out.println("sourceNode.getEle()======"+sourceNode.getEle());
                System.out.println("sourceNode.getEle().getParent()====="+sourceNode.getEle().getParent());
               ele=sourceNode.getEle().getParent();
                //ele=sourceNode.getEle();
               index= (sourceNode.getEle()).getParent().elements().size();
               System.out.println("index="+index);
               System.out.println("ele===flag=0===="+ele);
             }            
              Common.addNodeToFile(obj, targetNode, ele, index);

            } catch (IOException ex) {
                //JOptionPane.showMessageDialog(mapChoices, edge);
                Exceptions.printStackTrace(ex);
            }   
          
        }
    }
    
    private class ObjectSelectProvider implements SelectProvider {
        
        @Override
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }
        
        @Override
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }
        
        @Override
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = findObject(widget);
            
            if (object != null) {
                if (getSelectedObjects().contains(object))return;
                userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else
                userSelectionSuggested(Collections.emptySet(), invertSelection);
        }
    }
      

}

