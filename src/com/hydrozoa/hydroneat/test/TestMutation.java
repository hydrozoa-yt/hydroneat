package com.hydrozoa.hydroneat.test;

import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

public class TestMutation {
	
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
		
		ConnectionGene con1 = new ConnectionGene(0,2,0.5f,true,innovation.getInnovation());
		ConnectionGene con2 = new ConnectionGene(1,2,1f,true,innovation.getInnovation());
		
		genome.addConnectionGene(con1);
		genome.addConnectionGene(con2);
		
		// LegacyGenomePrinter.printGenome(genome, "output/mut_test_before.png");
		
		printer.showGenome(genome, "Before 1st mutation");
		
		genome.mutation(0.7f,r);
		
		printer.showGenome(genome, "After 1st mutation");
		
		int c = 100000;
		for (int i = 0; i < c; i++) {
			genome.mutation(0.7f, r);
		}
		
		printer.showGenome(genome, "After "+c+" mutations");
		
		// LegacyGenomePrinter.printGenome(genome, "output/mut_test_after.png");
	}

}
