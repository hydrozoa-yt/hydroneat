package com.hydrozoa.hydroneat;

/**
 * Used to pass along parameters for a search. The default values are the ones proposed in the original paper.
 * You'll notice that there is no parameter for changing interspecies mating rate. This is because this implementation doesn't support that.
 * 
 * @author hydrozoa
 */
public class NEATConfiguration {
	
	/***
	 * constant used in genomic distance calculation - this is the weight of excess genes
	 */
	public float C1 = 1.0f;
	
	/***
	 * constant used in genomic distance calculation - this is the weight of disjoint genes
	 */
	public float C2 = 1.0f;
	
	/**
	 * constant used in genomic distance calculation - this is the weight of average connection weight difference
	 */
	public float C3 = 0.4f;
	
	/**
	 * genomic distance we allow before two genomes are in seperate species - two genomes belong to the same species if genomic difference is less than this number
	 */
	public float DT = 3.0f;	
	
	/**
	 * Fraction of children genomes resulting from mutation without crossover. The remaining children come from mating with corssover.
	 */
	public float ASEXUAL_REPRODUCTION_RATE = 0.25f;
	
	/**
	 * chance for each child to have it's weights mutated, each weight in the genome having a PERTURBING_RATE chance of being uniformly perturbed, and 1-PERTURBING_RATE chance of being assigned a new random value
	 */
	public float MUTATION_RATE = 0.8f;	
	
	/**
	 * This applies to mutation of genomes.
	 * Each child has a MUTATION_RATE chance of mutating in each generation
	 */
	public float PERTURBING_RATE = 0.9f;
	
	/**
	 * Chance of a weight being disabled if it is disabled in either parent
	 */
	public float DISABLED_GENE_INHERITING_CHANCE = 0.75f;
	
	/**
	 * Chance of mutating a child in a way that adds a node to the genome.
	 */
	public float ADD_CONNECTION_RATE = 0.05f;
	
	/**
	 * Chance of mutating a child in a way that adds a connection to the genome.
	 */
	public float ADD_NODE_RATE = 0.03f;
	
	/**
	 * Percentage of offspring generated using crossover of two parents - the rest comes from asexual mutation
	 */
	public float OFFSPRING_FROM_CROSSOVER = 0.75f;
	
	private int populationSize;
	
	public NEATConfiguration(int popSize) {
		this.populationSize = popSize;
	}

	public int getPopulationSize() {
		return populationSize;
	}
}
