/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.hdata;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import net.casnw.home.hdata.io.ChatMenuItem;
import net.casnw.home.hdata.io.TableSorter;
import net.casnw.home.hdata.io.TimeColumItem;
import net.casnw.home.io.DataReader;
import net.casnw.home.modules.api.Utils;

import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_Home_VISUAL",
        iconBase = "net/casnw/home/hdata/calendar.png",
        mimeType = "text/x-dat",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "HomeVisual",
        position = 2000
)

public final class HdataVisualElement extends JPanel implements MultiViewElement {

    private HdataDataObject obj;
    private JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private DefaultTableModel model = null;

    public HdataVisualElement(Lookup lkp) {
        obj = lkp.lookup(HdataDataObject.class);
        assert obj != null;
        initComponents();
        String dataFile = obj.getPrimaryFile().getPath();
    }

    @Override

    public String getName() {
        return "HomeVisualElement";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel3.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jTextField1.text")); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel1.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\t" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(3, 3));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel5.text")); // NOI18N

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\t" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(3, 3));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel6.text")); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\t" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel7.text")); // NOI18N

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\t" }));
        jComboBox4.setPreferredSize(new java.awt.Dimension(3, 3));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jLabel8.text")); // NOI18N

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\t" }));
        jComboBox5.setPreferredSize(new java.awt.Dimension(3, 3));

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(HdataVisualElement.class, "HdataVisualElement.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(1317, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1895, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //将文件中的数据读取到表格中
        DataReader dr = new DataReader(obj.getPrimaryFile().getPath());
        Vector tableVec = new Vector();
        Vector headers = new Vector();

        int line = 0;
        while (dr.hasNext()) {
            List<String> lineData = new ArrayList();
            lineData = dr.getAllNext2String();
            if (headers.size() == 0) {
                headers.add("行号");
                for (String d : lineData) {
                    headers.add(d);
                }
            } else {
                if (lineData.size() != 0) {
                    Vector lineVec = new Vector();
                    lineVec.add(line);
                    for (String d : lineData) {
                        lineVec.add(Double.parseDouble(d));
                    }
                    tableVec.add(lineVec);
                }
            }
            line++;

        }

        model = new DataTableModel(tableVec, headers);
        ((DataTableModel) model).addColumnType(0, Integer.class);

        for (int i = 1; i < headers.size(); i++) {
            Class dataType;
            if (Utils.isNumeric(model.getValueAt(0, i).toString())) {
                dataType = Double.class;
            } else {
                dataType = model.getValueAt(0, i).getClass();
            }

            ((DataTableModel) model).addColumnType(i, dataType);
        }

        TableSorter sorter = new TableSorter(model);
        jTable1.setModel(sorter);
        sorter.setTableHeader(jTable1.getTableHeader());
        jTable1.setCellSelectionEnabled(true);
        jTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        MouseAdapter mouseListener = getMouseListener(jTable1);//添加鼠标右键选择行
        //加鼠标右击事件
        jTable1.addMouseListener(mouseListener);
        for (int i = 0; i < headers.size() - 1; i++) {
            jComboBox1.addItem(headers.get(i + 1).toString());
            jComboBox2.addItem(headers.get(i + 1).toString());
            jComboBox3.addItem(headers.get(i + 1).toString());
            jComboBox4.addItem(headers.get(i + 1).toString());
            jComboBox5.addItem(headers.get(i + 1).toString());

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String[] date = new String[model.getRowCount()];
        int year = jComboBox1.getSelectedIndex();
        int month = jComboBox2.getSelectedIndex();
        int day = jComboBox3.getSelectedIndex();
        int hour = jComboBox4.getSelectedIndex();
        int minute = jComboBox5.getSelectedIndex();
        Calendar cal = Calendar.getInstance();
        String format = "";
        if (year != 0) {
            format = "yyyy";
        }
        if (month != 0) {
            format = format + "-MM";
        }
        if (day != 0) {
            format = format + "-dd";
        }
        if (hour != 0) {
            format = format + " hh";
        }
        if (minute != 0) {
            format = format + ":mm";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);

        //year=1;month=2;day=5;hour=10;minite=12
        for (int i = 0; i < model.getRowCount(); i++) {
            cal.clear();
            if (year != 0) {
                double a = Double.parseDouble(model.getValueAt(i, year).toString());
                cal.set(1, (int) a);
            }
            if (month != 0) {
                double a = Double.parseDouble(model.getValueAt(i, month).toString()) - 1;
                cal.set(2, (int) a);
            }
            if (day != 0) {
                cal.set(5, (int) Double.parseDouble(model.getValueAt(i, day).toString()));
            }
            if (hour != 0) {
                cal.set(10, (int) Double.parseDouble(model.getValueAt(i, hour).toString()));
            }
            if (minute != 0) {
                cal.set(12, (int) Double.parseDouble(model.getValueAt(i, minute).toString()));
            }

            date[i] = df.format(cal.getTime());

            
            // System.out.println("date=" + df.format(date[i]));

        }

        ((DataTableModel) model).addColumnType(model.getColumnCount(), String.class);

        model.addColumn("timeStamps", date);

        jTable1.updateUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed
    public JPopupMenu makePopup(Map<String, Object[]> data) {
        JPopupMenu popupMenu = new JPopupMenu();

        //  TimeColumItem timeItem = new TimeColumItem("合并时间列", (HashMap<String, Object[]>) data);
        ChatMenuItem selectItem = new ChatMenuItem("折线图", (HashMap<String, Object[]>) data, jTable1);
        ChatMenuItem updateItem = new ChatMenuItem("趋势线", (HashMap<String, Object[]>) data, jTable1);
        ChatMenuItem insertItem = new ChatMenuItem("柱形图", (HashMap<String, Object[]>) data, jTable1);
        ChatMenuItem deleteItem = new ChatMenuItem("等高线", (HashMap<String, Object[]>) data, jTable1);
        
        //  ChatMenuItem hbmItem = new ChatMenuItem("导出", mCollection);
        // ChatMenuItem hbmPojoItem = new ChatMenuItem("Pojo Class", mCollection);

        selectItem.addActionListener(null);

        popupMenu.addSeparator();
        //  popupMenu.add(timeItem);
        popupMenu.add(selectItem);
        popupMenu.add(updateItem);
        popupMenu.add(insertItem);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        //popupMenu.add(hbmItem);
        //popupMenu.add(hbmPojoItem);
        return popupMenu;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
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

    private MouseAdapter getMouseListener(final JTable jTable) {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                //右击弹出菜单
                if (SwingUtilities.isRightMouseButton(evt)) {
                    JPopupMenu popMenu = null;
                    JTable table = (JTable) evt.getComponent();
                    //获取鼠标右键选中的行
                    int row = table.rowAtPoint(evt.getPoint());
                    System.out.println("select row=" + row);
                    if (row == -1) {
                        System.out.println("no select");
                        return;
                    }
                    //获取已选中的行
                    int[] rows = table.getSelectedRows();
                    int[] cols = table.getSelectedColumns();
                    if (rows.length == 0) {
                        System.out.println("no select");
                        return;
                    }
                    boolean inSelected = false;
                    //判断当前右键所在行是否已选中
                    for (int r : rows) {
                        if (row == r) {
                            inSelected = true;
                            break;
                        }
                    }
                    //当前鼠标右键点击所在行不被选中则高亮显示选中行
                    if (!inSelected) {
                        table.setRowSelectionInterval(row, row);
                    }
                    //取出鼠标选择的数据
                    //XYSeriesCollection mCollection = new XYSeriesCollection();

                    Map<String, Object[]> selectData = new HashMap<>();
                    /* //时间列,先默认选第一行为时间列，后期更新
                     Object[] time = new Object[rows.length];
                     int i = 0;
                     for (int r : rows) {
                     //  mSeries.add(Double.parseDouble(table.getValueAt(r, 0).toString()),
                     //          Double.parseDouble(table.getValueAt(r, c).toString()));
                     time[i] = Double.parseDouble(table.getValueAt(r, 0).toString());
                     i++;

                     }
                     selectData.put(table.getColumnName(0), time);*/
                    int i;
                    for (int c : cols) {
                        //XYSeries mSeries = new XYSeries(table.getColumnName(c));
                        Object[] obj = new Object[rows.length];
                        i = 0;
                        for (int r : rows) {
                            //  mSeries.add(Double.parseDouble(table.getValueAt(r, 0).toString()),
                            //          Double.parseDouble(table.getValueAt(r, c).toString()));
                            obj[i] = Double.parseDouble(table.getValueAt(r, c).toString());
                            i++;

                        }
                        selectData.put(table.getColumnName(c), obj);
                        //mCollection.addSeries(mSeries);
                    }

                    //生成右键菜单
                    popMenu = makePopup(selectData);
                    popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                }

            }
        };
    }

}
