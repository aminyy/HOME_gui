package net.casnw.home.chart.line;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Symbolline2 extends JFrame {

    private HashMap<String, Object[]> data = new HashMap<String, Object[]>();

    private String[] labels;
    private int[] color = {0xff3333, 0x008800, 0x3333cc};

    //Name of demo program
    public String toString() {
        return "折线图";
    }

    //Main code for creating charts
    public void createChart() {
        ChartViewer viewer = new ChartViewer();
        // Create a XYChart object of size 600 x 375 pixels
        XYChart c = new XYChart(800, 510);
        c.setDefaultFonts("SIMSUN.TTC", "simhei.ttf");
        c.setRoundedFrame();

        // Add a title to the chart using 18 pts Times Bold Italic font
        //c.addTitle("", "Times New Roman Bold Italic", 18);
        // Set the plotarea at (50, 55) and of 500 x 280 pixels in size. Use a
        // vertical gradient color from light blue (f9f9ff) to sky blue (aaccff) as
        // background. Set border to transparent and grid lines to white (ffffff).
        c.setPlotArea(70, 55, 600, 350, c.linearGradientColor(0, 55, 0, 335,
                0xf9fcff, 0xaaccff), -1, Chart.Transparent, 0xffffff);

        // Add a legend box at (50, 28) using horizontal layout. Use 10pts Arial Bold
        // as font, with transparent background.
        c.addLegend(75, 30, false, "Arial Bold", 10).setBackground(Chart.Transparent);
        c.yAxis().setAutoScale(0.2, 0);
        
        // Set the x axis labels
        c.xAxis().setLabels(labels);
        c.xAxis().setLabelStep(6);

        // Set y-axis tick density to 30 pixels. ChartDirector auto-scaling will use
        // this as the guideline when putting ticks on the y-axis.
        c.yAxis().setTickDensity(20);
        c.yAxis2().setTickDensity(20);

        // Set axis label style to 8pts Arial Bold
        //c.xAxis().setLabelStyle("Arial Bold", 8);
        //c.yAxis().setLabelStyle("Arial Bold", 8);
        // Set axis line width to 2 pixels
        c.yAxis().setWidth(4);
        c.yAxis2().setWidth(4);

        // Add axis title using 10pts Arial Bold Italic font
        //c.yAxis().setTitle("Value");
        c.xAxis().setTitle("Time","Arial Bold Italic", 12);

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer();
        LineLayer layer2 = c.addLineLayer2();
        
        int j = 0;

        for (String key : data.keySet()) {

            Object[] dataObject = data.get(key);
            double[] dataSeries = new double[dataObject.length];

            for (int i = 0; i < dataObject.length; i++) {
                dataSeries[i] = Double.parseDouble(dataObject[i].toString());
            }

            //如果选择两列，生成双Y轴的图形
            if (data.keySet().size() > 1) {
                Iterator<String> it = data.keySet().iterator();
                while (it.hasNext()) {
                    String key1 = it.next();
                    String key2 = it.next();
                    c.yAxis().setTitle(key1, "Arial Bold Italic", 12);
                    c.yAxis2().setTitle(key2, "Arial Bold Italic", 12);

                    Object[] dataObject1 = data.get(key1);
                    Object[] dataObject2 = data.get(key2);

                    double[] dataSeries1 = new double[dataObject1.length];

                    for (int i = 0; i < dataObject1.length; i++) {
                        dataSeries1[i] = Double.parseDouble(dataObject1[i].toString());
                    }

                    double[] dataSeries2 = new double[dataObject2.length];

                    for (int i = 0; i < dataObject2.length; i++) {
                        dataSeries2[i] = Double.parseDouble(dataObject2[i].toString());
                    }

                    Arrays.sort(dataSeries1);
                    double d1min = dataSeries1[0];
                    double d1max = dataSeries1[dataSeries1.length - 1];
                    c.yAxis().setLinearScale((int) d1min * 0.9, (int) d1max * 1.1 + 1, (int) (d1max - d1min) / 10);
                    //layer.addDataSet(dataSeries1, color[1], key1).setDataSymbol(2, 5);

                    Arrays.sort(dataSeries2);
                    double d2min = dataSeries2[0];
                    double d2max = dataSeries2[dataSeries2.length - 1];
                    c.yAxis2().setLinearScale((int) d2min * 0.9, (int) d2max * 1.1 + 1, (int) (d2max - d2min) / 10);
                    //layer.addDataSet(dataSeries1, color[1], key2).setUseYAxis2();

                }

            }

            if (j == 0) {
                c.yAxis().setColors(0xc00000, 0xc00000, 0xc00000);
                Object[] dataObject1 = data.get(key);
                layer.addDataSet(dataSeries, color[j], key).setDataSymbol(j + 1, 5);
            }
            if (j == 1) {
                c.yAxis2().setColors(0x008000, 0x008000, 0x008000);
                layer2.addDataSet(dataSeries, color[j], key).setDataSymbol(j + 1, 5);
                layer2.setUseYAxis2();

            }
            j++;

        }

        // Output the chart
        viewer.setChart(c);
        c.makeChart("lines.png");

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable"));
        this.getContentPane().add(viewer);
        this.pack();

        //save image
    }

    public void setData(HashMap<String, Object[]> data) {
        this.data = data;
    }

    /*数据文件中，第一列为Integer，其余数字为Double，合成的时间列为Date*/
    public void setXaxis(String[] xData) {
        labels = new String[xData.length];
        labels = xData;

    }

}
