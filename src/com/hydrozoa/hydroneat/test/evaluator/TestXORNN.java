package com.hydrozoa.hydroneat.test.evaluator;

import java.text.DecimalFormat;
import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.Evaluator;
import com.hydrozoa.hydroneat.GenesisGenomeProvider;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NEATConfiguration;
import com.hydrozoa.hydroneat.NeuralNetwork;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;
import com.hydrozoa.hydroneat.test.GenomePrinter;

/**
 * @author hydrozoa
 */
public class TestXORNN {
	
	public static void main(String[] args) {
		Random r = new Random();
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(9);
		
		// first column and second column contains operands, the third column is for bias input
		float[][] input = {		{	0f,		0f,		1f},
								{	0f,		1f,		1f},
								{	1f,		0f,		1f},
								{	1f,		1f,		1f}};
		
		float[] correctResult = {0f, 1f, 1f, 0f};
		
		Counter nodeInn = new Counter();
		Counter connInn = new Counter();
		
		Genome genome = new Genome();
		
		genome.addNodeGene(new NodeGene(TYPE.INPUT, nodeInn.getInnovation()));				// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.INPUT, nodeInn.getInnovation()));				// node id is 1
		genome.addNodeGene(new NodeGene(TYPE.INPUT, nodeInn.getInnovation()));				// node id is 2
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, nodeInn.getInnovation()));				// node id is 3 		BIAS NEURON, input always = 1
		
		genome.addConnectionGene(new ConnectionGene(0, 3, 1f, true, connInn.getInnovation()));	// conn id is 0
		genome.addConnectionGene(new ConnectionGene(1, 3, 1f, true, connInn.getInnovation()));	// conn id is 1
		genome.addConnectionGene(new ConnectionGene(2, 3, 1f, true, connInn.getInnovation()));	// conn id is 0
		
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
		
		NEATConfiguration conf = new NEATConfiguration(500);
		
		Evaluator eva = new Evaluator(conf, provider, nodeInn, connInn) {
			@Override
			public float evaluateGenome(Genome g) {
				NeuralNetwork net = new NeuralNetwork(g);
				
				//System.out.println("===========NEW NETWORK=============");
				
				float totalDistance = 0f;
				for (int i = 0; i < input.length; i++) {
					float[] inputs = {input[i][0], input[i][1], input[i][2]};
					//System.out.println("Giving input "+Arrays.toString(inputs));
					float[] outputs = net.calculate(inputs);
					//System.out.println("Received output "+Arrays.toString(outputs));
					float distance = (float) Math.abs(correctResult[i]-outputs[0]);
					//System.out.println("Error: "+distance);
					totalDistance += Math.pow(distance, 2);
				}
				
				//System.out.println("Total distance: "+totalDistance);
				
				return 100f-totalDistance;
			}
		};
		
		GenomePrinter gPrinter = new GenomePrinter();
		
		for (int i = 0; i < 10000; i++) {
			eva.evaluateGeneration(r);
			System.out.println("Generation: "+i);
			System.out.println("\tHighest fitness: "+df.format(eva.getFittestGenome().fitness));
			System.out.println("\tAmount of genomes: "+eva.getGenomeAmount());
			System.out.println("\tGuesses from best network: ");
			
			System.out.print("\t\t");
			NeuralNetwork net = new NeuralNetwork(eva.getFittestGenome().genome);
			for (int k = 0; k < input.length; k++) {
				float[] inputs = {input[k][0], input[k][1], input[k][2]};
				float[] outputs = net.calculate(inputs);
				
				float guess = outputs[0];
				System.out.print(df.format(guess)+", ");
			}
			System.out.print("\n");
			
//			System.out.println("\tPrinting all genomes");
//			int k = 0;
//			for (FitnessGenome fitnessGenome : eva.getLastGenerationResults()) {
//				k++;
//				System.out.println("\t\t Index="+k+"\t N"+fitnessGenome.genome.getNodeGenes().size()+"\t C"+fitnessGenome.genome.getConnectionGenes().size()+"\t fitness="+fitnessGenome.fitness);
//			}
			
			if (i % 1000 == 0) {
				gPrinter.showGenome(eva.getFittestGenome().genome, ""+i);
			}
		}
	}
}