package com.hydrozoa.hydroneat;

/**
 * Used for keeping track of innovation numbers in genomes as they are evolving. 
 * This utility will always return an integer that is higher than the previous.
 * 
 * @author hydrozoa
 */
public class Counter {
	
	private int currentInnovation = 0;
	
	public int getInnovation() {
		return currentInnovation++;
	}
}
