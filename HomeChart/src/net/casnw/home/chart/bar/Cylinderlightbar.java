package net.casnw.home.chart.bar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class Cylinderlightbar extends JFrame {

    private double[] data;
    private String[] labels;

    //Name of demo program
    public String toString() {
        return "Cylinder Bar Shading";
    }

    //Number of charts produced in this demo
    public int getNoOfCharts() {
        return 1;
    }

    //Main code for creating charts
    public void setData(Object[] data) {

        this.data = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = (double) data[i];
        }
    }

    public void setLable(String[] labels) {
        this.labels = labels;
    }

    public void createChart() {
        ChartViewer viewer = new ChartViewer();
        XYChart c = new XYChart(600, 380, Chart.brushedSilverColor(),
                Chart.Transparent, 2);

        // Add a title to the chart using 18pts Times Bold Italic font. Set
        // top/bottom margins to 8 pixels.
        c.addTitle("", "Times New Roman Bold Italic", 18
        ).setMargin2(0, 0, 8, 8);

        // Set the plotarea at (70, 55) and of size 460 x 280 pixels. Use transparent
        // border and black grid lines. Use rounded frame with radius of 20 pixels.
        c.setPlotArea(70, 55, 460, 280, -1, -1, Chart.Transparent, 0x000000);
        c.setRoundedFrame(0xffffff, 20);

        // Add a multi-color bar chart layer using the supplied data. Set cylinder
        // bar shape.
        c.addBarLayer3(data).setBarShape(Chart.CircleShape);

        // Set the labels on the x axis.
        c.xAxis().setLabels(this.labels);

        // Show the same scale on the left and right y-axes
        c.syncYAxis();

        // Set the left y-axis and right y-axis title using 10pt Arial Bold font
        c.yAxis().setTitle("", "Arial Bold", 10);
        //c.yAxis2().setTitle("USD (millions)", "Arial Bold", 10);

        // Set y-axes to transparent
        c.yAxis().setColors(Chart.Transparent);
        c.yAxis2().setColors(Chart.Transparent);

        // Disable ticks on the x-axis by setting the tick color to transparent
        c.xAxis().setTickColor(Chart.Transparent);

        // Set the label styles of all axes to 8pt Arial Bold font
        c.xAxis().setLabelStyle("Arial Bold", 8);
        c.yAxis().setLabelStyle("Arial Bold", 8);
        c.yAxis2().setLabelStyle("Arial Bold", 8);

        // Output the chart
        viewer.setChart(c);

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
                "title='Year {xLabel}: US$ {value}M'"));
        this.getContentPane().add(viewer);
        this.pack();

    }

}
