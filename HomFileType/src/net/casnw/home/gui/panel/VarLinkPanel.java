/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.gui.panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.casnw.home.gui.homFileType.HomFileDataObject;
import net.casnw.home.gui.utils.DataPoolOperation;
import net.casnw.home.meta.DatatypeEnum;
import net.casnw.home.model.Componentable;
import net.casnw.home.model.AbsComponent;
import net.casnw.home.modules.api.Module;

/**
 *
 * @author Administrator
 */
public class VarLinkPanel extends javax.swing.JPanel {

    /**
     * Creates new form VarLinkPanel
     */
    private DefaultTableModel model = null;
    private DataPoolOperation dpo = null;
    private Module obj = null;
    private HomFileDataObject hdo;
    private String dataType;
    private String varName;
    private List deleteData = new ArrayList();
    private Vector cellData;

    public VarLinkPanel(final Module obj, final DataPoolOperation dpo, HomFileDataObject hdo, String varName, final String dataType) {
        initComponents();
        this.obj = obj;
        this.dpo = dpo;
        this.hdo = hdo;
        this.dataType = dataType;
        this.varName = varName;
        jLabel2.setText(varName);
        jLabel4.setText(dataType);
        Vector headers = new Vector();
        headers.add("模块");
        headers.add("变量名");

        cellData = new Vector();
        cellData = dpo.getComAndVars(obj.getName(), varName);
        model = new DefaultTableModel(cellData, headers);
        jTable1.setModel(model);
        //模块和变量都为下拉列表
        final JComboBox compList = new JComboBox();
        final JComboBox varList = new JComboBox();
        compList.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox jcb = (JComboBox) e.getSource();
                    String comName = (String) (jcb.getSelectedItem());
                    //根据component name,得到模块中变量列表
                    varList.removeAllItems();
                    for (String varName : dpo.getVariables(obj.getName(), comName, dataType)) {
                        varList.addItem(varName);
                    }

                }

            }
        });

        for (Componentable com : dpo.getComponents(obj.getName())) {
            compList.addItem(com.getInstanceName());

        }

        TableCellEditor compEdt = new DefaultCellEditor(compList);
        jTable1.getColumnModel().getColumn(0).setCellEditor(compEdt);

        TableCellEditor varEdt = new DefaultCellEditor(varList);
        jTable1.getColumnModel().getColumn(1).setCellEditor(varEdt);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jTable1.columnModel.title3")); // NOI18N
        }

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(VarLinkPanel.class, "VarLinkPanel.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(41, 41, 41))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //新建容器变量与模块变量的对应关系
        Object[] rowData = {null, null};
        model.addRow(rowData);
        jTable1.updateUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //将jtable中的新旧数据进行对比
        Vector newCellData = new Vector();
        newCellData = model.getDataVector();
        //去除重复项
        List changeData = new ArrayList();
        for (int i = 0; i < model.getDataVector().size(); i++) {
            Vector aa = (Vector) model.getDataVector().get(i);
            if (aa.get(0) != null && aa.get(1) != null) {
                String compName = aa.get(0).toString();
                String varName = aa.get(1).toString();
                String[] data = {compName, varName};
                for (int j = 0; j < changeData.size(); j++) {
                    String[] a = (String[]) changeData.get(j);
                    if (!(a[0].equalsIgnoreCase(compName) && a[1].equalsIgnoreCase(varName))) {
                        changeData.add(data);
                    }
                }

            }
        }
        dpo.updateAccess(obj, changeData, hdo, varName, dataType);
        //dpo.update();
        //将修改的参数存入xml文件

        JOptionPane.showMessageDialog(this, "保存成功！", "消息", 0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int index =jTable1.getSelectedRow();
        String componentName = jTable1.getValueAt(index, 0).toString();
        String varName = jTable1.getValueAt(index, 1).toString();
        String[] data = {componentName,varName };
        //去除重复项
        deleteData.add(data);
        model.removeRow(jTable1.getSelectedRow());       
        jTable1.updateUI();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
