package com.hydrozoa.hydroneat.test.chart;

import java.util.Random;

import com.hydrozoa.hydroneat.visual.LiveXYChart;

public class LiveChartTest {
	
	public static void main(String[] args) {
		LiveXYChart chart = new LiveXYChart("LiveChart test","generations","fitness", 100);
		
		Random r = new Random();
		
		for (int i = 0; i < 1000; i++) {
			chart.addDataPoint(new Double(i), r.nextDouble()*100);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
