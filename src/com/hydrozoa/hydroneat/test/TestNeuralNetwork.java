package com.hydrozoa.hydroneat.test;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NeuralNetwork;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

public class TestNeuralNetwork {
	
	public static void main(String[] args) {
		Genome genome;
		NeuralNetwork net;
		
		float[] input;
		float[] output;
		
		System.out.println("======================TEST 1===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		
		genome.addConnectionGene(new ConnectionGene(0, 1, 0.5f, true, 0));	// conn id is 0
		
		net = new NeuralNetwork(genome);
		input = new float[]{1f};
		for (int i = 0; i < 3 ; i++) {
			output = net.calculate(input);
			System.out.println("output is of length="+output.length+" and has output[0]="+output[0]+" expecting 0.9192");
		}
		
		System.out.println();
		System.out.println("======================TEST 2===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		
		genome.addConnectionGene(new ConnectionGene(0, 1, 0.1f, true,0));	// conn id is 0
		
		net = new NeuralNetwork(genome);
		input = new float[]{-0.5f};
		for (int i = 0; i < 3 ; i++) {
			output = net.calculate(input);
			System.out.println("output is of length="+output.length+" and has output[0]="+output[0]+" expecting 0.50973");
		}
		
		System.out.println();
		System.out.println("======================TEST 3===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		genome.addNodeGene(new NodeGene(TYPE.HIDDEN, 2));					// node id is 2
		
		genome.addConnectionGene(new ConnectionGene(0, 2, 0.4f, true, 0));	// conn id is 0
		genome.addConnectionGene(new ConnectionGene(2, 1, 0.7f, true, 1));	// conn id is 1
		
		net = new NeuralNetwork(genome);
		input = new float[]{0.9f};
		for (int i = 0; i < 3 ; i++) {
			output = net.calculate(input);
			System.out.println("output is of length="+output.length+" and has output[0]="+output[0]+" expecting 0.9524");
		}
		
		System.out.println();
		System.out.println("======================TEST 4===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 1));					// node id is 1
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 2));					// node id is 2
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 3));					// node id is 3
		genome.addNodeGene(new NodeGene(TYPE.HIDDEN, 4));					// node id is 4
		
		genome.addConnectionGene(new ConnectionGene(0, 4, 0.4f, true, 0));	// conn id is 0
		genome.addConnectionGene(new ConnectionGene(1, 4, 0.7f, true, 1));	// conn id is 1
		genome.addConnectionGene(new ConnectionGene(2, 4, 0.1f, true, 2));	// conn id is 2
		genome.addConnectionGene(new ConnectionGene(4, 3, 1f, true, 3));	// conn id is 3
		
		net = new NeuralNetwork(genome);
		input = new float[]{0.5f, 0.75f, 0.90f};
		for (int i = 0; i < 3 ; i++) {
			output = net.calculate(input);
			System.out.println("output is of length="+output.length+" and has output[0]="+output[0]+" expecting 0.9924");
		}
		
		System.out.println();
		System.out.println("======================TEST 5===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 1));					// node id is 1
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 2));					// node id is 2
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 3));					// node id is 3
		genome.addNodeGene(new NodeGene(TYPE.HIDDEN, 4));					// node id is 4
		genome.addNodeGene(new NodeGene(TYPE.HIDDEN, 5));					// node id is 5
		
		genome.addConnectionGene(new ConnectionGene(0, 4, 0.4f, true, 0));	// conn id is 0
		genome.addConnectionGene(new ConnectionGene(1, 4, 0.7f, true, 1));	// conn id is 1
		genome.addConnectionGene(new ConnectionGene(2, 4, 0.1f, true, 2));	// conn id is 2
		genome.addConnectionGene(new ConnectionGene(4, 3, 1f, true, 3));	// conn id is 3
		genome.addConnectionGene(new ConnectionGene(2, 5, 0.2f, true, 4));	// conn id is 4
		genome.addConnectionGene(new ConnectionGene(5, 4, 0.75f, true, 5));	// conn id is 5
		genome.addConnectionGene(new ConnectionGene(5, 3, 0.55f, true, 6));	// conn id is 6
		
		net = new NeuralNetwork(genome);
		input = new float[]{1f, 2f, 3f};
		for (int i = 0; i < 3 ; i++) {
			output = net.calculate(input);
			System.out.println("output is of length="+output.length+" and has output[0]="+output[0]+" expecting 0.99895");
		}
		
		//GenomePrinter printer = new GenomePrinter();
		//printer.showGenome(genome, "");
	}

}
