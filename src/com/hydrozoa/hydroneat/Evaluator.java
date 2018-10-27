package com.hydrozoa.hydroneat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author hydrozoa
 */
public abstract class Evaluator {
	
	private NEATConfiguration config;
	private FitnessGenomeComparator comparator = new FitnessGenomeComparator();
	
	protected List<Genome> genomes;						// stores all genomes of current generation
	protected List<Genome> nextGeneration;				// stores next generation of genomes (used during evaluation)
	protected List<FitnessGenome> evaluatedGenomes;		// stores all genomes with fitness of current generation (used during evaluation). Incidentally, this list contains results from previous generation.
	protected FitnessGenome fittestGenome;				// fittest genome w/ score form last run generation
	
	protected List<FitnessGenome> lastGenerationResults;	// contains a sorted list of the previous generations genomes
	
	protected Counter nodeInnovation;
	protected Counter connectionInnovation;
	
	public Evaluator(NEATConfiguration configuration, GenesisGenomeProvider generator, Counter nodeInnovation, Counter connectionInnovation) {
		this.config = configuration;
		
		genomes = new ArrayList<Genome>(configuration.getPopulationSize());
		for (int i = 0; i < configuration.getPopulationSize(); i++) {
			Genome g = generator.generateGenesisGenome();
			genomes.add(g);
		}
		
		evaluatedGenomes = new LinkedList<FitnessGenome>();
		nextGeneration = new LinkedList<Genome>();
		
		lastGenerationResults = new LinkedList<FitnessGenome>();
		
		this.nodeInnovation = nodeInnovation;
		this.connectionInnovation = connectionInnovation;
	}
	
	public void evaluateGeneration(Random r) {
		lastGenerationResults.clear();
		evaluatedGenomes.clear();
		
		/* Score each genome */
		for (int i = 0; i < genomes.size(); i++) {
			Genome g = genomes.get(i);
			FitnessGenome fitnessGenome = new FitnessGenome(g, evaluateGenome(g));
			evaluatedGenomes.add(fitnessGenome);
		}
		
		/* Sort evaluated genomes by fitness */
		Collections.sort(evaluatedGenomes, comparator);
		Collections.reverse(evaluatedGenomes);
		
		lastGenerationResults.addAll(evaluatedGenomes);
		
		fittestGenome = evaluatedGenomes.get(0);
		
		/* Kill off worst 75% of genomes */
		int cutoffIndex = evaluatedGenomes.size() / 10;
		Iterator<FitnessGenome> it = evaluatedGenomes.iterator();
		int index = 0;
		while (it.hasNext()) {
			it.next();
			if (index > cutoffIndex) {
				it.remove();
			}
			index++;
		}
		
		/* Find next generation population */
		nextGeneration.clear();
		
		// First, take champion of this generation and pass on to next generation
		Genome champion = evaluatedGenomes.get(0).genome;
		nextGeneration.add(champion);
		
		// Next, fill in next generation by random mating and mutation
		while (nextGeneration.size() < config.getPopulationSize()) {
			if (r.nextFloat() > config.ASEXUAL_REPRODUCTION_RATE) { // sexual reproduction
				FitnessGenome parent1 = evaluatedGenomes.get(r.nextInt(evaluatedGenomes.size()));
				FitnessGenome parent2 = evaluatedGenomes.get(r.nextInt(evaluatedGenomes.size()));
				Genome child;
				if (parent1.fitness > parent2.fitness) {
					child = Genome.crossover(parent1.genome, parent2.genome, r, config.DISABLED_GENE_INHERITING_CHANCE);
				} else {
					child = Genome.crossover(parent2.genome, parent1.genome, r, config.DISABLED_GENE_INHERITING_CHANCE);
				}
				if (r.nextFloat() < config.MUTATION_RATE) {
					child.mutation(config.PERTURBING_RATE, r);
				}
				if (r.nextFloat() < config.ADD_CONNECTION_RATE) {	// add mutation from adding connection and nodes
					child.addConnectionMutation(r, connectionInnovation, 100);
				}
				if (r.nextFloat() < config.ADD_NODE_RATE) {	// add mutation from adding node
					child.addNodeMutation(r, connectionInnovation, nodeInnovation);
				}
				nextGeneration.add(child);
			} else {												// asexual reproduction
				FitnessGenome parent = evaluatedGenomes.get(r.nextInt(evaluatedGenomes.size()));
				Genome child = new Genome(parent.genome);
				child.mutation(config.PERTURBING_RATE, r);
				nextGeneration.add(child);
			}
		}
		
		// Transfer next generation to current generation
		genomes.clear();
		for (int i = 0; i < nextGeneration.size(); i++) {
			genomes.add(nextGeneration.get(i));
		}
	}
	
	public abstract float evaluateGenome(Genome g);
	
	/**
	 * @return Fittest genome from the previous generation.
	 */
	public FitnessGenome getFittestGenome() {
		return fittestGenome;
	}
	
	public int getGenomeAmount() {
		return genomes.size();
	}
	
	/**
	 * @return	All genomes in the current generation. These are not evaluated yet! 
	 */
	public Iterable<Genome> getGenomes() {
		return genomes;
	}
	
	/**
	 * @return	Results from previously evaluated generation, or null if no evaluation has taken place.
	 */
	public Iterable<FitnessGenome> getLastGenerationResults() {
		return lastGenerationResults;
	}
}