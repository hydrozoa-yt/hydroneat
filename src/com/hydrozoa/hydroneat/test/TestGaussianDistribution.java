package com.hydrozoa.hydroneat.test;

import java.util.Random;

/**
 * Prints values from {@link Random#nextGaussian()}.
 * @author hydrozoa
 */
public class TestGaussianDistribution {
	
	public static void main(String[] args) {
		Random random = new Random();
		
		double value;
		for (int i = 0; i < 1000; i++) {
			value = random.nextGaussian();
			System.out.println(value);
		}
	}
}
