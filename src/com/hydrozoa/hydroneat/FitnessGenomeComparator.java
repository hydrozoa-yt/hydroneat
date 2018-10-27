package com.hydrozoa.hydroneat;

import java.util.Comparator;

/**
 * @author hydrozoa
 */
public class FitnessGenomeComparator implements Comparator<FitnessGenome> {
	
	/**
	 * @return 	a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
	 */
	@Override
	public int compare(FitnessGenome one, FitnessGenome two) {
		if (one.fitness > two.fitness) {
			return 1;
		} else if (one.fitness < two.fitness) {
			return -1;
		}
		return 0;
	}
	
}
