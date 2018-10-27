package com.hydrozoa.hydroneat.test.evaluator;

import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.Evaluator;
import com.hydrozoa.hydroneat.FitnessGenome;
import com.hydrozoa.hydroneat.GenesisGenomeProvider;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NEATConfiguration;
import com.hydrozoa.hydroneat.NeuralNetwork;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;
import com.hydrozoa.hydroneat.test.GenomePrinter;

/**
 * Tests a simple evaluator that runs for 100 generations, and scores fitness based on the amount of connections in the network.
 * The expectation here is that Genomes will tend to grow larger, since they're rewarded for having more connections.
 * 
 * @author hydrozoa
 */
public class TestEvaluatorManyConnections {
	
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
		
		NEATConfiguration conf = new NEATConfiguration(100);
		
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
		
		Evaluator eval = new Evaluator(conf, provider, nodeInnovation, connectionInnovation) {
			@Override
			public float evaluateGenome(Genome genome) {
				
				float[] input = {1f, 1f};
				
				NeuralNetwork net = new NeuralNetwork(genome);
				float output[] = net.calculate(input);
				
				//System.out.println("Output="+output[0]);
				
				return genome.getConnectionGenes().values().size();
			}
		};
		
		GenomePrinter gPrinter = new GenomePrinter();
		
		for (int i = 0; i < 1000; i++) {
			eval.evaluateGeneration(r);
			System.out.println("Generation: "+i);
			System.out.println("\t Highest fitness: "+eval.getFittestGenome().fitness);
			System.out.println("\t Amount of genomes: "+eval.getGenomeAmount());
			System.out.println("\t Printing all genomes");
			int k = 0;
			for (FitnessGenome fitnessGenome : eval.getLastGenerationResults()) {
				k++;
				System.out.println("\t\t Index="+k+"\t N"+fitnessGenome.genome.getNodeGenes().size()+"\t C"+fitnessGenome.genome.getConnectionGenes().size()+"\t fitness="+fitnessGenome.fitness);
			}
			//gPrinter.showGenome(eval.getFittestGenome().genome, "fittest   gen="+i+" score="+eval.getFittestGenome().fitness);
		}
	}
}