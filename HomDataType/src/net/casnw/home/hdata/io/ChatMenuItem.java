/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.hdata.io;

import ChartDirector.ChartViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.casnw.home.chart.line.Zoomscrolltrack2;
import java.util.*;
import net.casnw.home.chart.bar.Cylinderlightbar;
import net.casnw.home.chart.contour.Contour;
import net.casnw.home.chart.line.Symbolline2;
import net.casnw.home.chart.line.Zoomscrolltrack2;
import net.casnw.home.chart.trendLine.Scattertrend;
import net.casnw.home.chart.trendLine.Threedline;
import org.openide.util.Exceptions;

/**
 *
 * @author minyufang
 */
public class ChatMenuItem extends JMenuItem implements ActionListener {

    HashMap<String, Object[]> data = new HashMap<String, Object[]>();
    private JTable table;

    public String getDateFormat(String str) {
        String dateFormat = "yyyy-MM-dd";
        switch (str.length()) {
            case 4:
                dateFormat = "yyyy";
                break;
            case 7:
                dateFormat = "yyyy-MM";
                break;
            case 10:
                dateFormat = "yyyy-MM-dd";
                break;
            case 13:
                dateFormat = "yyyy-MM-dd HH";
                break;
            case 16:
                dateFormat = "yyyy-MM-dd HH:mm";
                break;
        }
        return dateFormat;
    }

    public ChatMenuItem(String text, HashMap<String, Object[]> data, JTable table) {
        super(text);
        this.data = data;
        this.table = table;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Item clicked: " + e.getActionCommand());

        switch (e.getActionCommand()) {
            case "折线图":
                createLineChat();
                break;
            case "趋势线":
                createTrendLineChart();
                break;
            case "柱形图":
                createBarChart();
                break;
            case "等高线":
                createContourChart();
                break;

            default:
                break;

        }
    }

    public void createContourChart() {
        Contour demo = new Contour();
        demo.createChart();
        demo.setVisible(true);

    }

    public void createTrendLineChart() {
     
        Scattertrend demo = new Scattertrend();
        demo.setData(data);
        demo.createChart();
        demo.setVisible(true);

    }

    public void createBarChart() {
        int index = getXaxisIndex();
        int[] rows = table.getSelectedRows();
        String[] xdata = new String[rows.length];
        Object[] data1 = new Object[rows.length];
        int i = 0;
        for (String key : data.keySet()) {
            data1 = data.get(key);
            break;
        }
        for (int r : rows) {
            xdata[i] = table.getModel().getValueAt(r, index).toString();
            i++;
        }

        Cylinderlightbar demo = new Cylinderlightbar();
        //ChartViewer viewer = new ChartViewer();

        //demo.setDefaultCloseOperation(EXIT_ON_CLOSE);
        demo.setData(data1);
        //demo.setTimeStaps(date);
        demo.setLable(xdata);
        demo.createChart();

        demo.setVisible(true);
    }

    public int getXaxisIndex() {
        int column = table.getModel().getColumnCount();
        Object[] possibleValues = new Object[column];
        for (int i = 0; i < column; i++) {
            possibleValues[i] = table.getModel().getColumnName(i);
        }

        //用户选择以那一列转为x轴
        Object selectedValue = JOptionPane.showInputDialog(null,
                "选择一列作为图的x轴:\n", "选择X轴", JOptionPane.QUESTION_MESSAGE,
                null, possibleValues, possibleValues[0]);
        int index = 0;

        for (Object obj : possibleValues) {
            if (obj == selectedValue) {
                break;
            }
            index++;
        }
        return index;
    }

    public void createLineChat() {
        String xaxisType = "java.lang.String";

        int index = getXaxisIndex();
        /*数据文件中，第一列为Integer，其余数字为Double，合成的时间列为Date*/
        System.out.println("x axis=" + table.getModel().getColumnClass(index));
        //如果x轴为时间类型
        if (table.getModel().getColumnClass(index).toString().equalsIgnoreCase(xaxisType)) {
            Zoomscrolltrack2 demo = new Zoomscrolltrack2();
            demo.setData(data);
            int[] rows = table.getSelectedRows();
            Date[] date = new Date[rows.length];
            Object[] xdata = new Object[rows.length];
            SimpleDateFormat format = null;
            int i = 0;
            for (int r : rows) {
                try {
                    String str = table.getModel().getValueAt(r, index).toString();
                    if (i == 0) {
                        format = new SimpleDateFormat(getDateFormat(str));
                    }
                    Date d;
                    d = format.parse(str);
                    xdata[i] = table.getModel().getValueAt(r, index);
                    date[i] = d;

                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
                i++;
            }
            demo.setTimeStaps(date);
            demo.setupFrame();
            demo.setVisible(true);
        } else {
            Symbolline2 demo = new Symbolline2();
            //Zoomscrolltrack2 demo = new Zoomscrolltrack2();
            demo.setData(data);
            int[] rows = table.getSelectedRows();
            String[] xdata = new String[rows.length];
            int i = 0;
            for (int r : rows) {
                xdata[i] = table.getModel().getValueAt(r, index).toString();
                i++;
            }
            demo.setXaxis(xdata);
            demo.createChart();

            demo.setVisible(true);
        }

        // demo.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //demo.setXaxis(xdata);
    }

}
