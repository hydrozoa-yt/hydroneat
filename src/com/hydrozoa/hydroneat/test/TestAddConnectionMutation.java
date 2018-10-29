package com.hydrozoa.hydroneat.test;

import com.hydrozoa.hydroneat.Genome;

import java.util.Random;

import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;
import com.hydrozoa.hydroneat.visual.GenomePrinter;

/**
 * @author hydrozoa
 */
public class TestAddConnectionMutation {
	
	public static void main(String[] args) {
		GenomePrinter printer = new GenomePrinter();
		Counter innovation = new Counter();
		Random r = new Random(1337L);
		
		Genome genome = new Genome();
		
		NodeGene input1 = new NodeGene(TYPE.INPUT, 0);
		NodeGene input2 = new NodeGene(TYPE.INPUT, 1);
		NodeGene output = new NodeGene(TYPE.OUTPUT, 2);
		
		genome.addNodeGene(input1);
		genome.addNodeGene(input2);
		genome.addNodeGene(output);
		
		//LegacyGenomePrinter.printGenome(genome, "output/add_con_mut_test_before.png");
		printer.showGenome(genome, "before");
		
		genome.addConnectionMutation(r, innovation, 10);
		//genome.addConnectionMutation(r, innovation, 10);
		
		//LegacyGenomePrinter.printGenome(genome, "output/add_con_mut_test_after.png");
		printer.showGenome(genome, "after");
	}

}
