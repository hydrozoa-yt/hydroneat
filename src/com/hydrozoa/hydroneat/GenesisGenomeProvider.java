package com.hydrozoa.hydroneat;

/**
 * Passed along to the Evaluator when beginning a new search. This facility will generate all genomes
 * in the starting population of the search.
 * 
 * @author hydrozoa
 */
public interface GenesisGenomeProvider {
	
	public Genome generateGenesisGenome();

}
