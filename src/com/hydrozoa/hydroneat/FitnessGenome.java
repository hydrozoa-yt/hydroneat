package com.hydrozoa.hydroneat;

/**
 * Ties a fitness to a Genome. 
 * This is useful in implementation of Evaluators.
 * 
 * @author hydrozoa
 */
public class FitnessGenome {
	
	public float fitness;
	public Genome genome;
	
	public FitnessGenome(Genome genome, float fitness) {
		this.genome = genome;
		this.fitness = fitness;
	}
}
