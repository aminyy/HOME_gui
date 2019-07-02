package net.casnw.home.chart.line;

import ChartDirector.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Zoomscrolltrack2 extends JFrame {

    // Data arrays for the scrollable / zoomable chart.
    private Date[] timeStamps;
    private HashMap<String, Object[]> data = new HashMap<String, Object[]>();

    // The earliest date and the duration in seconds for horizontal scrolling
    private Date minDate;
    private double dateRange;

    // The vertical range of the chart for vertical scrolling
    //  private double maxValue;
    //  private double minValue;
    // The current visible duration of the view port in seconds
    private double currentDuration = 360 * 86400;
    // In this demo, the maximum zoom-in is set to 10 days
    private double minDuration = 10 * 86400;

    // Will set to true at the end of initialization
    private boolean hasFinishedInitialization;
    private int[] color = {0xff3333, 0x008800, 0x3333cc};

    //
    // Controls in the JFrame
    //
    private JButton pointerPB;
    private JButton zoomInPB;
    private JButton zoomOutPB;
    private JComboBox startYear;
    private JComboBox startMonth;
    private JComboBox startDay;
    private JComboBox endYear;
    private JComboBox endMonth;
    private JComboBox endDay;
    private ChartViewer chartViewer1;
    private JScrollBar hScrollBar1;

    public void setData(HashMap<String, Object[]> data) {
        this.data = data;
    }

    public void setTimeStaps(Date[] date) {
        this.timeStamps = date;
    }

    /*数据文件中，第一列为Integer，其余数字为Double，合成的时间列为Date*/
    public void setXaxis(Object[] xData) {
    }

    //
    // Set up the JFrame
    //
    public void setupFrame() {

        // Do nothing if already initialized
        if (hasFinishedInitialization) {
            return;
        }

        // Set JFrame title to name of this demo program
        setTitle(toString());
        setResizable(false);

        // Top label bar
        JLabel topLabel = new JLabel("Advanced Software Engineering");
        topLabel.setForeground(new java.awt.Color(255, 255, 51));
        topLabel.setBackground(new java.awt.Color(0, 0, 128));
        topLabel.setBorder(new javax.swing.border.EmptyBorder(2, 0, 2, 5));
        topLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        topLabel.setOpaque(true);
        getContentPane().add(topLabel, java.awt.BorderLayout.NORTH);

        // Left panel
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());

        // Pointer push button
        pointerPB = new JButton("Pointer", loadImageIcon("pointer.gif"));
        pointerPB.setHorizontalAlignment(SwingConstants.LEFT);
        pointerPB.setMargin(new Insets(5, 5, 5, 5));
        pointerPB.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointerPB_Clicked();
            }
        });
        leftPanel.add(pointerPB).setBounds(1, 0, 148, 24);

        // Zoom In push button
        zoomInPB = new JButton("Zoom In", loadImageIcon("zoomin.gif"));
        zoomInPB.setHorizontalAlignment(SwingConstants.LEFT);
        zoomInPB.setMargin(new Insets(5, 5, 5, 5));
        zoomInPB.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInPB_Clicked();
            }
        });
        leftPanel.add(zoomInPB).setBounds(1, 24, 148, 24);

        // Zoom out push button
        zoomOutPB = new JButton("Zoom Out", loadImageIcon("zoomout.gif"));
        zoomOutPB.setHorizontalAlignment(SwingConstants.LEFT);
        zoomOutPB.setMargin(new Insets(5, 5, 5, 5));
        zoomOutPB.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutPB_Clicked();
            }
        });
        leftPanel.add(zoomOutPB).setBounds(1, 48, 148, 24);

        // Start Date label
        leftPanel.add(new JLabel("Start Date")).setBounds(5, 180, 130, 20);

        // Start Year, Start Month, Start Day combo boxes
        startYear = new JComboBox();
        startMonth = new JComboBox();
        startDay = new JComboBox();
        leftPanel.add(startYear).setBounds(1, 200, 60, 20);
        leftPanel.add(startMonth).setBounds(61, 200, 44, 20);
        leftPanel.add(startDay).setBounds(105, 200, 44, 20);
        ActionListener startDateHandler = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDate_ValueChanged();
            }
        };
        startYear.addActionListener(startDateHandler);
        startMonth.addActionListener(startDateHandler);
        startDay.addActionListener(startDateHandler);

        // End Date label
        leftPanel.add(new JLabel("End Date")).setBounds(5, 232, 130, 20);

        // End Year, End Month, End Day combo boxes
        endYear = new JComboBox();
        endMonth = new JComboBox();
        endDay = new JComboBox();
        leftPanel.add(endYear).setBounds(1, 252, 60, 20);
        leftPanel.add(endMonth).setBounds(61, 252, 44, 20);
        leftPanel.add(endDay).setBounds(105, 252, 44, 20);
        ActionListener endDateHandler = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDate_ValueChanged();
            }
        };
        endYear.addActionListener(endDateHandler);
        endMonth.addActionListener(endDateHandler);
        endDay.addActionListener(endDateHandler);

        // Total expected panel size
        leftPanel.setPreferredSize(new Dimension(150, 264));

        // Chart Viewer
        chartViewer1 = new ChartViewer();
        chartViewer1.setBackground(new java.awt.Color(255, 255, 255));
        chartViewer1.setOpaque(true);
        chartViewer1.setPreferredSize(new Dimension(656, 366));
        chartViewer1.setHorizontalAlignment(SwingConstants.CENTER);
        chartViewer1.setHotSpotCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chartViewer1.addViewPortListener(new ViewPortAdapter() {
            public void viewPortChanged(ViewPortChangedEvent e) {
                chartViewer1_ViewPortChanged(e);
            }
        });
        chartViewer1.addTrackCursorListener(new TrackCursorAdapter() {
            public void mouseMovedPlotArea(MouseEvent e) {
                chartViewer1_MouseMovedPlotArea(e);
            }
        });
        chartViewer1.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                chartViewer1_MouseWheel(e);
            }
        });

        // Horizontal Scroll bar
        hScrollBar1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 100000000, 0, 1000000000);
        hScrollBar1.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                hScrollBar1_ValueChanged();
            }
        });

        // Put the ChartViewer and the scroll bars in the right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(chartViewer1, java.awt.BorderLayout.CENTER);
        rightPanel.add(hScrollBar1, java.awt.BorderLayout.SOUTH);

        // Put the leftPanel and rightPanel on the JFrame
        getContentPane().add(leftPanel, java.awt.BorderLayout.WEST);
        getContentPane().add(rightPanel, java.awt.BorderLayout.CENTER);

        // Set all UI fonts (except labels)
        Font uiFont = new Font("Dialog", Font.PLAIN, 11);
        for (int i = 0; i < leftPanel.getComponentCount(); ++i) {
            Component c = leftPanel.getComponent(i);
            if (!(c instanceof JLabel)) {
                c.setFont(uiFont);
            }
        }

        // Initialize date controls to reflect the date range in the data
        initDateControls();

        // Initialize the ChartViewer
        initChartViewer(chartViewer1);

        // Trigger a view port update to draw chart.
        pack();
        hasFinishedInitialization = true;
        chartViewer1.updateViewPort(true, true);
    }

    //
    // A utility to load an image icon from the Java class path
    //
    private ImageIcon loadImageIcon(String path) {
        try {
            return new ImageIcon(getClass().getClassLoader().getResource(path));
        } catch (Exception e) {
            return null;
        }
    }

    //
    // Initialize date controls
    //
    private void initDateControls() {
        // Set the date range of the startYear and endYear combo boxes to match the scrollable date range.
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(timeStamps[0]);
        int minYear = calendar.get(Calendar.YEAR);
        calendar.setTime(timeStamps[timeStamps.length - 1]);
        int maxYear = calendar.get(Calendar.YEAR);

        startYear.removeAllItems();
        endYear.removeAllItems();
        for (int i = minYear; i <= maxYear; ++i) {
            startYear.addItem(new Integer(i));
            endYear.addItem(new Integer(i));
        }

        // Initialize the startMonth and endMonth combo boxes.
        for (int i = 1; i <= 12; ++i) {
            startMonth.addItem(new Integer(i));
            endMonth.addItem(new Integer(i));
        }
    }

    //
    // Initialize the WinChartViewer
    //
    private void initChartViewer(ChartViewer viewer) {
        // Set the full x range to be the duration of the data
        viewer.setFullRange("x", timeStamps[0], timeStamps[timeStamps.length - 1]);

        // Initialize the view port to show the latest 20% of the time range
        viewer.setViewPortWidth(0.2);
        viewer.setViewPortLeft(1 - viewer.getViewPortWidth());

        // Set the maximum zoom to 10 points
        viewer.setZoomInWidthLimit(10.0 / timeStamps.length);

        // Initially set the mouse usage to "Pointer" mode (Drag to Scroll mode)
        pointerPB.doClick();
    }

    //
    // The ViewPortChanged event handler. This event occurs if the user scrolls or zooms in
    // or out the chart by dragging or clicking on the chart. It can also be triggered by
    // calling WinChartViewer.updateViewPort.
    //
    private void chartViewer1_ViewPortChanged(ViewPortChangedEvent e) {
        // In addition to updating the chart, we may also need to update other controls that
        // changes based on the view port.
        updateControls(chartViewer1);

        // Update the chart if necessary
        if (e.needUpdateChart()) {
            drawChart(chartViewer1);
        }

        // We need to update the track line too. If the mouse is moving on the chart (eg. if 
        // the user drags the mouse on the chart to scroll it), the track line will be updated
        // in the MouseMovedPlotArea event. Otherwise, we need to update the track line here.
        if ((!chartViewer1.isInMouseMoveEvent()) && chartViewer1.isMouseOnPlotArea()) {
            trackLineLabel((XYChart) chartViewer1.getChart(), chartViewer1.getPlotAreaMouseX());
            chartViewer1.updateDisplay();
        }
    }

    //
    // Update controls when the view port changed
    //
    private void updateControls(ChartViewer viewer) {
        // Update the start date and end date control to reflect the view port.
        Date startDate = Chart.NTime(viewer.getValueAtViewPort("x", viewer.getViewPortLeft()));
        setDateCtrl(startDate, startYear, startMonth, startDay);
        Date endDate = Chart.NTime(viewer.getValueAtViewPort("x", viewer.getViewPortLeft()
                + viewer.getViewPortWidth()));
        setDateCtrl(endDate, endYear, endMonth, endDay);

        // Update the scroll bar to reflect the view port position and width of the view port.
        hScrollBar1.setEnabled(chartViewer1.getViewPortWidth() < 1);
        hScrollBar1.setVisibleAmount((int) Math.ceil(chartViewer1.getViewPortWidth()
                * (hScrollBar1.getMaximum() - hScrollBar1.getMinimum())));
        hScrollBar1.setBlockIncrement(hScrollBar1.getVisibleAmount());
        hScrollBar1.setUnitIncrement((int) Math.ceil(hScrollBar1.getVisibleAmount() * 0.1));
        hScrollBar1.setValue((int) Math.round(chartViewer1.getViewPortLeft()
                * (hScrollBar1.getMaximum() - hScrollBar1.getMinimum())) + hScrollBar1.getMinimum());
    }

    //
    // Sets the year/month/day combo boxes to the given date.
    //
    private void setDateCtrl(Date d, JComboBox year, JComboBox month, JComboBox day) {
        GregorianCalendar startYMD = new GregorianCalendar();
        startYMD.setTime(d);

        year.setSelectedItem(new Integer(startYMD.get(Calendar.YEAR)));
        month.setSelectedIndex(startYMD.get(Calendar.MONTH));
        normalizeDateCtrl(year, month, day);
        day.setSelectedIndex(startYMD.get(Calendar.DAY_OF_MONTH) - 1);
    }

    //
    // Normalize the day combo boxes so that it shows 28 to 31 days depending on the month/year.
    //
    private void normalizeDateCtrl(JComboBox year, JComboBox month, JComboBox day) {
        final int[] daysTable = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int daysInMonth = daysTable[startMonth.getSelectedIndex()];
        if ((daysInMonth == 28) && (((Integer) year.getSelectedItem()).intValue() % 4 == 0)) {
            ++daysInMonth;
        }

        while (daysInMonth < day.getItemCount()) {
            day.removeItemAt(day.getItemCount() - 1);
        }
        while (daysInMonth > day.getItemCount()) {
            day.addItem(new Integer(day.getItemCount() + 1));
        }
    }

    //
    // Draw the chart.
    //
    private void drawChart(ChartViewer viewer) {
        // Get the start date and end date that are visible on the chart.
        Date viewPortStartDate = Chart.NTime(viewer.getValueAtViewPort("x", viewer.getViewPortLeft()));
        Date viewPortEndDate = Chart.NTime(viewer.getValueAtViewPort("x", viewer.getViewPortLeft()
                + viewer.getViewPortWidth()));

        // Get the array indexes that corresponds to the visible start and end dates
        int startIndex = (int) Math.floor(Chart.bSearch(timeStamps, viewPortStartDate));
        int endIndex = (int) Math.ceil(Chart.bSearch(timeStamps, viewPortEndDate));
        int noOfPoints = endIndex - startIndex + 1;
        ;
        //
        // At this stage, we have extracted the visible data. We can use those data to plot the chart.
        //
        //================================================================================
        // Configure overall chart appearance.
        //================================================================================
        // Create an XYChart object of size 640 x 350 pixels
        XYChart c = new XYChart(640, 350);

        // Re-cycle the resources of the existing chart, if any. This can improve performance 
        // by reducing the frequency of garbage collections. 		
        c.recycle(chartViewer1.getChart());

        // Set the plotarea at (55, 50) with width 80 pixels less than chart width, and height 85 pixels
        // less than chart height. Use a vertical gradient from light blue (f0f6ff) to sky blue (a0c0ff)
        // as background. Set border to transparent and grid lines to white (ffffff).
        c.setPlotArea(55, 50, c.getWidth() - 80, c.getHeight() - 85, c.linearGradientColor(0, 50, 0,
                c.getHeight() - 35, 0xf0f6ff, 0xa0c0ff), -1, Chart.Transparent, 0xffffff, 0xffffff);

        // As the data can lie outside the plotarea in a zoomed chart, we need enable clipping.
        c.setClipping();

        // Add a title to the chart using 18 pts Times New Roman Bold Italic font
        String title = "test";
        String ytitle = "test";
        c.addTitle(title, "Times New Roman Bold Italic", 18);

        // Add a legend box at (55, 25) using horizontal layout. Use 8pts Arial Bold as font. Set the
        // background and border color to Transparent and use line style legend key.
        LegendBox b = c.addLegend(55, 25, false, "Arial Bold", 8);
        b.setBackground(Chart.Transparent);
        b.setLineStyleKey();

        // Set the axis stem to transparent
        c.xAxis().setColors(Chart.Transparent);
        c.yAxis().setColors(Chart.Transparent);

        // Add axis title using 10pts Arial Bold Italic font
        c.yAxis().setTitle(ytitle, "Arial Bold Italic", 10);

        //================================================================================
        // Add data to chart
        //================================================================================
        //
        // In this example, we represent the data by lines. You may modify the code below to use other
        // representations (areas, scatter plot, etc).
        //
        // Add a line layer for the lines, using a line width of 2 pixels
        LineLayer layer = c.addLineLayer2();
        layer.setLineWidth(2);

        // In this demo, we do not have too many data points. In real code, the chart may contain a lot
        // of data points when fully zoomed out - much more than the number of horizontal pixels in this
        // plot area. So it is a good idea to use fast line mode.
        layer.setFastLineMode();

        // Extract the part of the data array that are visible.
        Date[] viewPortTimeStamps = (Date[]) Chart.arraySlice(timeStamps, startIndex, noOfPoints);

        layer.setXData(viewPortTimeStamps);
        int j = 0;

        for (String key : data.keySet()) {

            //   double[] dataSeries = (double[]) Chart.arraySlice(data.get(key), startIndex, noOfPoints);
            Object[] dataObject = data.get(key);
            double[] dataSeries = new double[dataObject.length];
            for (int i = 0; i < dataObject.length; i++) {
                dataSeries[i] = Double.parseDouble(dataObject[i].toString());
            }

            layer.addDataSet(dataSeries, color[j], key).setDataSymbol(j + 1, 8);
            j++;

        }

        //================================================================================
        // Configure axis scale and labelling
        //================================================================================
        // Set the x-axis as a date/time axis with the scale according to the view port x range.
        viewer.syncDateAxisWithViewPort("x", c.xAxis());

        //
        // In this demo, the time range can be from a few years to a few days. We demonstrate how to set
        // up different date/time format based on the time range.
        //
        // If all ticks are yearly aligned, then we use "yyyy" as the label format.
        c.xAxis().setFormatCondition("align", 360 * 86400);
        c.xAxis().setLabelFormat("{value|yyyy}");

        // If all ticks are monthly aligned, then we use "mmm yyyy" in bold font as the first label of a
        // year, and "mmm" for other labels.
        c.xAxis().setFormatCondition("align", 30 * 86400);
        c.xAxis().setMultiFormat(Chart.StartOfYearFilter(), "<*font=bold*>{value|mmm yyyy}",
                Chart.AllPassFilter(), "{value|mmm}");

        // If all ticks are daily algined, then we use "mmm dd<*br*>yyyy" in bold font as the first
        // label of a year, and "mmm dd" in bold font as the first label of a month, and "dd" for other
        // labels.
        c.xAxis().setFormatCondition("align", 86400);
        c.xAxis().setMultiFormat(Chart.StartOfYearFilter(),
                "<*block,halign=left*><*font=bold*>{value|mmm dd<*br*>yyyy}", Chart.StartOfMonthFilter(),
                "<*font=bold*>{value|mmm dd}");
        c.xAxis().setMultiFormat2(Chart.AllPassFilter(), "{value|dd}");

        // For all other cases (sub-daily ticks), use "hh:nn<*br*>mmm dd" for the first label of a day,
        // and "hh:nn" for other labels.
        c.xAxis().setFormatCondition("else");
        c.xAxis().setMultiFormat(Chart.StartOfDayFilter(), "<*font=bold*>{value|hh:nn<*br*>mmm dd}",
                Chart.AllPassFilter(), "{value|hh:nn}");

        //================================================================================
        // Output the chart
        //================================================================================
        viewer.setChart(c);
    }

    //
    // Click event for the pointerPB.
    //
    private void pointerPB_Clicked() {
        pointerPB.setBackground(new Color(0x80, 0xff, 0x80));
        zoomInPB.setBackground(null);
        zoomOutPB.setBackground(null);
        chartViewer1.setMouseUsage(Chart.MouseUsageScrollOnDrag);
    }

    //
    // Click event for the zoomInPB.
    //
    private void zoomInPB_Clicked() {
        pointerPB.setBackground(null);
        zoomInPB.setBackground(new Color(0x80, 0xff, 0x80));
        zoomOutPB.setBackground(null);
        chartViewer1.setMouseUsage(Chart.MouseUsageZoomIn);
    }

    //
    // Click event for the zoomOutPB.
    //
    private void zoomOutPB_Clicked() {
        pointerPB.setBackground(null);
        zoomInPB.setBackground(null);
        zoomOutPB.setBackground(new Color(0x80, 0xff, 0x80));
        chartViewer1.setMouseUsage(Chart.MouseUsageZoomOut);
    }

    //
    // Horizontal ScrollBar ValueChanged event handler
    //
    private void hScrollBar1_ValueChanged() {
        if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent()) {
            // Get the view port left as according to the scroll bar
            double newViewPortLeft = ((double) (hScrollBar1.getValue() - hScrollBar1.getMinimum()))
                    / (hScrollBar1.getMaximum() - hScrollBar1.getMinimum());

            // Check if view port has really changed - sometimes the scroll bar may issue redundant
            // value changed events when value has not actually changed.
            if (Math.abs(chartViewer1.getViewPortLeft() - newViewPortLeft)
                    > 0.00001 * chartViewer1.getViewPortWidth()) {
                // Set the view port based on the scroll bar
                chartViewer1.setViewPortLeft(newViewPortLeft);

                // Update the chart display without updating the image maps. We delay updating
                // the image map because the chart may still be unstable (still scrolling).
                chartViewer1.updateViewPort(true, false);
            }
        }
    }

    //
    // Start Date control event handler
    //
    private void startDate_ValueChanged() {
        // The date control can update the view port only if it is not currently being updated in the
        // view port changed event (that is, only if the date is changed due to user action).
        if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent()) {
            Date startDate = new GregorianCalendar(((Integer) startYear.getSelectedItem()).intValue(),
                    startMonth.getSelectedIndex(), startDay.getSelectedIndex() + 1).getTime();

            // The updated view port width
            double vpWidth = chartViewer1.getViewPortLeft() + chartViewer1.getViewPortWidth()
                    - chartViewer1.getViewPortAtValue("x", Chart.CTime(startDate));

            // Make sure the updated view port width is within bounds
            vpWidth = Math.max(chartViewer1.getZoomInWidthLimit(), Math.min(vpWidth,
                    chartViewer1.getViewPortLeft() + chartViewer1.getViewPortWidth()));

            // Update view port and trigger a view port changed event to update the chart
            chartViewer1.setViewPortLeft(chartViewer1.getViewPortLeft()
                    + chartViewer1.getViewPortWidth() - vpWidth);
            chartViewer1.setViewPortWidth(vpWidth);
            chartViewer1.updateViewPort(true, false);
        }
    }

    //
    // End Date control event handler
    //
    private void endDate_ValueChanged() {
        // The date control can update the view port only if it is not currently being updated in the
        // view port changed event (that is, only if the date is changed due to user action).
        if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent()) {
            Date endDate = new GregorianCalendar(((Integer) endYear.getSelectedItem()).intValue(),
                    endMonth.getSelectedIndex(), endDay.getSelectedIndex() + 1).getTime();

            // The updated view port width
            double vpWidth = chartViewer1.getViewPortAtValue("x", Chart.CTime(endDate))
                    - chartViewer1.getViewPortLeft();

            // Make sure the updated view port width is within bounds
            vpWidth = Math.max(chartViewer1.getZoomInWidthLimit(), Math.min(vpWidth,
                    1 - chartViewer1.getViewPortLeft()));

            // Update view port and trigger a view port changed event to update the chart
            chartViewer1.setViewPortWidth(vpWidth);
            chartViewer1.updateViewPort(true, false);
        }
    }

    //
    // Mouse Wheel event handler
    //
    private void chartViewer1_MouseWheel(MouseWheelEvent e) {
        // We zoom in or out by 10% depending on the mouse wheel direction.
        double rx = e.getWheelRotation() < 0 ? 0.9 : 1 / 0.9;
        double ry = rx;

        // We do not zoom in beyond the zoom in width or height limit.
        rx = Math.max(rx, chartViewer1.getZoomInWidthLimit() / chartViewer1.getViewPortWidth());
        ry = Math.max(ry, chartViewer1.getZoomInWidthLimit() / chartViewer1.getViewPortHeight());
        if ((rx == 1) && (ry == 1)) {
            return;
        }

        XYChart c = (XYChart) chartViewer1.getChart();

        //
        // Set the view port position and size so that it is zoom in/out around the mouse by the 
        // desired ratio.
        //
        double mouseOffset = (e.getX() - c.getPlotArea().getLeftX())
                / (double) c.getPlotArea().getWidth();
        chartViewer1.setViewPortLeft(chartViewer1.getViewPortLeft() + mouseOffset * (1 - rx)
                * chartViewer1.getViewPortWidth());
        chartViewer1.setViewPortWidth(chartViewer1.getViewPortWidth() * rx);

        double mouseOffsetY = (e.getY() - c.getPlotArea().getTopY())
                / (double) c.getPlotArea().getHeight();
        chartViewer1.setViewPortTop(chartViewer1.getViewPortTop() + mouseOffsetY * (1 - ry)
                * chartViewer1.getViewPortHeight());
        chartViewer1.setViewPortHeight(chartViewer1.getViewPortHeight() * ry);

        // Trigger a view port changed event to update the chart
        chartViewer1.updateViewPort(true, false);
    }

    //
    // Draw track cursor when mouse is moving over plotarea
    //
    private void chartViewer1_MouseMovedPlotArea(MouseEvent e) {
        ChartViewer viewer = (ChartViewer) e.getSource();
        trackLineLabel((XYChart) viewer.getChart(), viewer.getPlotAreaMouseX());
        viewer.updateDisplay();

        // Hide the track cursor when the mouse leaves the plot area
        viewer.removeDynamicLayer("MouseExitedPlotArea");
    }

    //
    // Draw track line with data labels
    //
    private void trackLineLabel(XYChart c, int mouseX) {
        // Clear the current dynamic layer and get the DrawArea object to draw on it.
        DrawArea d = c.initDynamicLayer();

        // The plot area object
        PlotArea plotArea = c.getPlotArea();

        // Get the data x-value that is nearest to the mouse, and find its pixel coordinate.
        double xValue = c.getNearestXValue(mouseX);
        int xCoor = c.getXCoor(xValue);

        // Draw a vertical track line at the x-position
        d.vline(plotArea.getTopY(), plotArea.getBottomY(), xCoor, d.dashLineColor(0x000000, 0x0101));

        // Draw a label on the x-axis to show the track line position.
        String xlabel = "<*font,bgColor=000000*> " + c.xAxis().getFormattedLabel(xValue, "mmm dd, yyyy")
                + " <*/font*>";
        TTFText t = d.text(xlabel, "Arial Bold", 8);

        // Restrict the x-pixel position of the label to make sure it stays inside the chart image.
        int xLabelPos = Math.max(0, Math.min(xCoor - t.getWidth() / 2, c.getWidth() - t.getWidth()));
        t.draw(xLabelPos, plotArea.getBottomY() + 6, 0xffffff);

        // Iterate through all layers to draw the data labels
        for (int i = 0; i < c.getLayerCount(); ++i) {
            Layer layer = c.getLayerByZ(i);

            // The data array index of the x-value
            int xIndex = layer.getXIndexOf(xValue);

            // Iterate through all the data sets in the layer
            for (int j = 0; j < layer.getDataSetCount(); ++j) {
                ChartDirector.DataSet dataSet = layer.getDataSetByZ(j);

                // Get the color and position of the data label
                int color = dataSet.getDataColor();
                int yCoor = c.getYCoor(dataSet.getPosition(xIndex), dataSet.getUseYAxis());

                // Draw a track dot with a label next to it for visible data points in the plot area
                if ((yCoor >= plotArea.getTopY()) && (yCoor <= plotArea.getBottomY()) && (color
                        != Chart.Transparent) && (!(dataSet.getDataName() == null || dataSet.getDataName().length()
                        == 0))) {

                    d.circle(xCoor, yCoor, 4, 4, color, color);

                    String label = "<*font,bgColor=" + Integer.toHexString(color) + "*> " + c.formatValue(
                            dataSet.getValue(xIndex), "{value|P4}") + " <*/font*>";
                    t = d.text(label, "Arial Bold", 8);

                    // Draw the label on the right side of the dot if the mouse is on the left side the chart,
                    // and vice versa. This ensures the label will not go outside the chart image.
                    if (xCoor <= (plotArea.getLeftX() + plotArea.getRightX()) / 2) {
                        t.draw(xCoor + 5, yCoor, 0xffffff, Chart.Left);
                    } else {
                        t.draw(xCoor - 5, yCoor, 0xffffff, Chart.Right);
                    }
                }
            }
        }
    }

    //
    // Implementation of the DemoModule interface to allow this demo to run inside the 
    // ChartDirectorDemo browser
    //
    // Name of demo program
    public String toString() {
        return "Zoom/Scroll with Track Line (2)";
    }

    // Number of charts produced in this demo
    public int getNoOfCharts() {
        // This demo open its own frame instead of using the right pane of the ChartDirectorDemo 
        // browser for display, so we just load the frame, then returns 0.
        setupFrame();
        setVisible(true);
        return 0;
    }

}
