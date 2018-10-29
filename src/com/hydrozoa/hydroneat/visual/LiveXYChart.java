package com.hydrozoa.hydroneat.visual;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * @author hydrozoa
 */
public class LiveXYChart {
	
	private ConcurrentLinkedQueue<Double> toBeAddedY = new ConcurrentLinkedQueue<Double>();
	private ConcurrentLinkedQueue<Double> toBeAddedX = new ConcurrentLinkedQueue<Double>();
	
	private List<Double> chartDataY;
	private List<Double> chartDataX;
	
	private SwingWrapper<XYChart> sw;
	private XYChart chart;
	
	private String seriesName = "series";
	private int maxVisibleDataPoints;
	
	public LiveXYChart(String title, String xAxisLabel, String yAxisLabel, int maxVisibleDataPoints) {
		chartDataY = new LinkedList<Double>();
		chartDataX = new LinkedList<Double>();
		
		this.maxVisibleDataPoints = maxVisibleDataPoints;
		
		// Create Chart
		chart = QuickChart.getChart(title, xAxisLabel, yAxisLabel, seriesName, new double[] { 0 }, new double[] { 0 });
		chart.getStyler().setLegendVisible(false);
		
		// Show it
		sw = new SwingWrapper<XYChart>(chart);
		sw.displayChart(title);
		
		// Start thread that updates chart
		TimerTask chartUpdaterTask = new TimerTask() {
			@Override
			public void run() {
				updateData();
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						sw.repaintChart();
					}
				});
			}
		};
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);
	}
	
	private void updateData() {
		if (toBeAddedY.isEmpty()) {
			return;
		}
		
		chartDataX.addAll(toBeAddedX);
	    chartDataY.addAll(toBeAddedY);
	    
	    toBeAddedX.clear();
	    toBeAddedY.clear();

	    while (chartDataY.size() > maxVisibleDataPoints) {
	    	chartDataX.remove(0);
	    	chartDataY.remove(0);
	    }

	    chart.updateXYSeries(seriesName, chartDataX, chartDataY, null);
	}
	
	public void addDataPoint(double dataX, double dataY) {
		toBeAddedX.add(dataX);
		toBeAddedY.add(dataY);
	}
}