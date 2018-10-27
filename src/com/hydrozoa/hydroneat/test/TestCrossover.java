package com.hydrozoa.hydroneat.test;

import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

/**
 * @author hydrozoa
 */
public class TestCrossover {
	
	public static void main(String[] args) {
		GenomePrinter printer = new GenomePrinter();
		
		Genome parent1 = new Genome();
		for (int i = 0; i < 3; i++) {
			NodeGene node = new NodeGene(TYPE.INPUT, i);
			parent1.addNodeGene(node);
		}
		parent1.addNodeGene(new NodeGene(TYPE.OUTPUT, 3));
		parent1.addNodeGene(new NodeGene(TYPE.HIDDEN, 4));
		
		parent1.addConnectionGene(new ConnectionGene(0, 3, 1f, true, 0));
		parent1.addConnectionGene(new ConnectionGene(1, 3, 1f, false, 1));
		parent1.addConnectionGene(new ConnectionGene(2, 3, 1f, true, 2));
		parent1.addConnectionGene(new ConnectionGene(1, 4, 1f, true, 3));
		parent1.addConnectionGene(new ConnectionGene(4, 3, 1f, true, 4));
		parent1.addConnectionGene(new ConnectionGene(0, 4, 1f, true, 8));
		
		printer.showGenome(parent1, "parent 1");
		
		
		Genome parent2 = new Genome();
		for (int i = 0; i < 3; i++) {
			NodeGene node = new NodeGene(TYPE.INPUT, i);
			parent2.addNodeGene(node);
		}
		parent2.addNodeGene(new NodeGene(TYPE.OUTPUT, 3));
		parent2.addNodeGene(new NodeGene(TYPE.HIDDEN, 4));
		parent2.addNodeGene(new NodeGene(TYPE.HIDDEN, 5));
		
		parent2.addConnectionGene(new ConnectionGene(0, 3, 0.5f, true, 0));
		parent2.addConnectionGene(new ConnectionGene(1, 3, 0.5f, false, 1));
		parent2.addConnectionGene(new ConnectionGene(2, 3, 0.5f, true, 2));
		parent2.addConnectionGene(new ConnectionGene(1, 4, 0.5f, true, 3));
		parent2.addConnectionGene(new ConnectionGene(4, 3, 0.5f, false, 4));
		parent2.addConnectionGene(new ConnectionGene(4, 5, 0.5f, true, 5));
		parent2.addConnectionGene(new ConnectionGene(5, 3, 0.5f, true, 6));
		parent2.addConnectionGene(new ConnectionGene(2, 4, 0.5f, true, 8));
		parent2.addConnectionGene(new ConnectionGene(0, 5, 0.5f, true, 10));
		
		printer.showGenome(parent2, "parent 2");
		
		Genome child = Genome.crossover(parent1, parent2, new Random(), 0.75f);
		
		printer.showGenome(child, "child");
		
		// Printing to PNG using legacy printer
		//LegacyGenomePrinter.printGenome(parent1, "output/parent1.png");
		//LegacyGenomePrinter.printGenome(parent2, "output/parent2.png");
		//LegacyGenomePrinter.printGenome(child, "output/child.png");
	}
}
