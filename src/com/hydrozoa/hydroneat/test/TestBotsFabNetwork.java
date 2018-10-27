package com.hydrozoa.hydroneat.test;

import java.util.ArrayList;
import java.util.List;

import com.hydrozoa.hydroneat.BotsFabNetwork;
import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

public class TestBotsFabNetwork {
	
	public static void main(String[] args) {
		Genome genome;
		BotsFabNetwork net;
		
		List<Float> input;
		List<Float> output;
		
		System.out.println("======================TEST 1===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		
		genome.addConnectionGene(new ConnectionGene(0, 1, 0.5f, true, 0));	// conn id is 0
		
		net = new BotsFabNetwork();
		net.setup(genome);
		input = new ArrayList<Float>();
		input.add(1f);
		output = net.run(input);
		System.out.println("output is of length="+output.size()+" and has output[0]="+output.get(0)+" expecting 0.9192");
		
		System.out.println();
		System.out.println("======================TEST 2===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		
		genome.addConnectionGene(new ConnectionGene(0, 1, 0.1f, true,0));	// conn id is 0
		
		net = new BotsFabNetwork();
		net.setup(genome);
		input = new ArrayList<Float>();
		input.add(-0.5f);
		output = net.run(input);
		System.out.println("output is of length="+output.size()+" and has output[0]="+output.get(0)+" expecting 0.50973");

		
		System.out.println();
		System.out.println("======================TEST 3===================================");
		System.out.println();
		
		genome = new Genome();
		genome.addNodeGene(new NodeGene(TYPE.INPUT, 0));					// node id is 0
		genome.addNodeGene(new NodeGene(TYPE.OUTPUT, 1));					// node id is 1
		genome.addNodeGene(new NodeGene(TYPE.HIDDEN, 2));					// node id is 2
		
		genome.addConnectionGene(new ConnectionGene(0, 2, 0.4f, true, 0));	// conn id is 0
		genome.addConnectionGene(new ConnectionGene(2, 1, 0.7f, true, 1));	// conn id is 1
		
		net = new BotsFabNetwork();
		net.setup(genome);
		input = new ArrayList<Float>();
		input.add(0.9f);
		output = net.run(input);
		System.out.println("output is of length="+output.size()+" and has output[0]="+output.get(0)+" expecting 0.9524");
		
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
		
		net = new BotsFabNetwork();
		net.setup(genome);
		input = new ArrayList<Float>();
		input.add(0.5f);
		input.add(0.75f);
		input.add(0.90f);
		output = net.run(input);
		System.out.println("output is of length="+output.size()+" and has output[0]="+output.get(0)+" expecting 0.9924");
		
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
		
		net = new BotsFabNetwork();
		net.setup(genome);
		input = new ArrayList<Float>();
		input.add(1f);
		input.add(2f);
		input.add(3f);
		output = net.run(input);
		System.out.println("output is of length="+output.size()+" and has output[0]="+output.get(0)+" expecting 0.99895");
		
		//GenomePrinter printer = new GenomePrinter();
		//printer.showGenome(genome, "");
	}
}