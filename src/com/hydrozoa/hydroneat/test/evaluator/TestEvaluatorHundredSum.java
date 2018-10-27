package com.hydrozoa.hydroneat.test.evaluator;

import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.Evaluator;
import com.hydrozoa.hydroneat.FitnessGenome;
import com.hydrozoa.hydroneat.GenesisGenomeProvider;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NEATConfiguration;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;
import com.hydrozoa.hydroneat.test.GenomePrinter;

/**
 * Tests a simple evaluator that runs for 1000 generations, and scores fitness based on how close the sum of the weights is to 100.
 * 
 * @author hydrozoa
 */
public class TestEvaluatorHundredSum {
	
	public static void main(String[] args) {
		Random r = new Random();
		
		Counter nodeInnovation = new Counter();
		Counter connectionInnovation = new Counter();
		
		Genome genome = new Genome();
		int n1 = nodeInnovation.getInnovation();
		int n2 = nodeInnovation.getInnovation();
		int n3 = nodeInnovation.getInnovation();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, n1));
		genome.addNodeGene(new NodeGene(TYPE.INPUT, n2));
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, n3));
		
		int c1 = connectionInnovation.getInnovation();
		int c2 = connectionInnovation.getInnovation();
		genome.addConnectionGene(new ConnectionGene(n1,n3,0.5f,true,c1));
		genome.addConnectionGene(new ConnectionGene(n2,n3,0.5f,true,c2));
		
		GenesisGenomeProvider provider = new GenesisGenomeProvider() {
			@Override
			public Genome generateGenesisGenome() {
				Genome g = new Genome(genome);
				for (ConnectionGene connection : g.getConnectionGenes().values()) {
					connection.setWeight((float)r.nextGaussian());
				}
				return g;
			}
		};
		
		NEATConfiguration conf = new NEATConfiguration(100);
		
		Evaluator eval = new Evaluator(conf, provider, nodeInnovation, connectionInnovation) {
			@Override
			public float evaluateGenome(Genome g) {
				float weightSum = 0f;
				for (ConnectionGene connection : g.getConnectionGenes().values()) {
					if (connection.isExpressed()) {
						weightSum += Math.abs(connection.getWeight());
					}
				}
				float difference = Math.abs(100f-weightSum);
				return 1000f/difference;
			}
		};
		
		GenomePrinter gPrinter = new GenomePrinter();
		
		for (int i = 0; i < 10000; i++) {
			eval.evaluateGeneration(r);
			System.out.println("Generation: "+i);
			System.out.println("\t Highest fitness: "+eval.getFittestGenome().fitness);
			System.out.println("\t Amount of genomes: "+eval.getGenomeAmount());
			System.out.println("\t Printing all genomes");
			int k = 0;
			for (FitnessGenome fitnessGenome : eval.getLastGenerationResults()) {
				k++;
				float weightSum = 0f;
				for (ConnectionGene connection : fitnessGenome.genome.getConnectionGenes().values()) {
					if (connection.isExpressed()) {
						weightSum += Math.abs(connection.getWeight());
					}
				}
				System.out.println("\t\t Index="+k+"\t N"+fitnessGenome.genome.getNodeGenes().size()+"\t C"+fitnessGenome.genome.getConnectionGenes().size()+"\t fitness="+fitnessGenome.fitness+"\t sum of weights="+weightSum);
			}
			if (i % 100 == 0) {
				//gPrinter.showGenome(eval.getFittestGenome().genome, "fittest   gen="+i+" score="+eval.getFittestGenome().fitness);
			}
		}
	}
}