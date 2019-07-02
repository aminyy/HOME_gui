package net.casnw.home.chart.trendLine;

import javax.swing.*;
import ChartDirector.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;

public class Scattertrend extends JFrame {

    private HashMap<String, Object[]> data = new HashMap<String, Object[]>();

    //Name of demo program
    public String toString() {
        return "趋势图";
    }

    //Main code for creating charts
    public void createChart() {
        ChartViewer viewer = new ChartViewer();
        // Create a XYChart object of size 600 x 375 pixels
        XYChart c = new XYChart(800, 510);
        c.setDefaultFonts("SIMSUN.TTC", "simhei.ttf");
        c.setRoundedFrame();

        c.setPlotArea(70, 55, 600, 350, c.linearGradientColor(0, 55, 0, 335,
                0xf9fcff, 0xaaccff), -1, Chart.Transparent, 0xffffff);
        c.yAxis().setWidth(3);
        c.xAxis().setWidth(3);
        c.yAxis().setAutoScale(0.2, 0);

        double slope;
        double intercept;
        double R2;
        TrendLayer trend1 = null;
        LegendBox legendBox = null;
        DecimalFormat df = new DecimalFormat(".00");

        for (String key : data.keySet()) {

            Object[] dataObject = data.get(key);
            double[] ds1 = new double[dataObject.length];
            double[] ds2 = new double[dataObject.length];

            if (data.keySet().size() > 1) {

                Iterator<String> it = data.keySet().iterator();
                while (it.hasNext()) {
                    String key1 = it.next();
                    String key2 = it.next();

                    c.xAxis().setTitle(key1, "Arial Bold Italic", 12);
                    c.yAxis().setTitle(key2, "Arial Bold Italic", 12);

                    Object[] dataObject1 = data.get(key1);
                    Object[] dataObject2 = data.get(key2);

                    for (int i = 0; i < dataObject1.length; i++) {
                        ds1[i] = Double.parseDouble(dataObject1[i].toString());
                    }

                    for (int i = 0; i < dataObject2.length; i++) {
                        ds2[i] = Double.parseDouble(dataObject2[i].toString());
                    }

                }
                ScatterLayer scatter1 = c.addScatterLayer(ds1, ds2, "", Chart.CircleSymbol, 9, 0xc00000);
                trend1 = c.addTrendLayer2(ds1, ds2, 0x008000);
                trend1.setLineWidth(3);

                //trend1.getCoefficient(WIDTH);
                //trend1.getCorrelation();
                trend1.addConfidenceBand(0.95, 0x806666ff);
                trend1.addPredictionBand(0.95, 0x8066ff66);

                legendBox = c.addLegend(75, 35, false, "arialbi.ttf", 9);
                //legendBox.setBackground(1);
                legendBox.setCols(1);

            }

        }

        slope = trend1.getSlope();
        //Double.valueOf(df.format(slope));
        intercept = trend1.getIntercept();
        //Double.valueOf(df.format(intercept));
        R2 = trend1.getCorrelation();
        //Double.valueOf(df.format(R2));
        legendBox.addKey("95% Line Confidence", 0x806666ff);
        legendBox.addKey("95% Point Confidence", 0x8066ff66);
        legendBox.addKey("y = ".concat(Double.toString(Double.valueOf(df.format(slope)))).concat("x + ").concat(Double.toString(Double.valueOf(df.format(intercept)))), 3);
        legendBox.addKey("R^2 = ".concat(Double.toString(Double.valueOf(df.format(R2)))), 3);

        // Output the chart
        viewer.setChart(c);

        c.makeChart("scatterTrend.png");

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable"));
        this.getContentPane().add(viewer);
        this.pack();

    }

    public void setData(HashMap<String, Object[]> data) {
        this.data = data;
    }

}
