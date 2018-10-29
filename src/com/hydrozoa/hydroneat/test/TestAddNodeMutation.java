package com.hydrozoa.hydroneat.test;

import java.util.Random;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;
import com.hydrozoa.hydroneat.visual.GenomePrinter;

/**
 * @author hydrozoa
 */
public class TestAddNodeMutation {
	
	public static void main(String[] args) {
		GenomePrinter printer = new GenomePrinter();
		
		Counter nodeInnovator = new Counter();
		Counter connInnovator = new Counter();
		Random r = new Random();
		
		Genome genome = new Genome();
		
		NodeGene input1 = new NodeGene(TYPE.INPUT, nodeInnovator.getInnovation());
		NodeGene input2 = new NodeGene(TYPE.INPUT, nodeInnovator.getInnovation());
		NodeGene output = new NodeGene(TYPE.OUTPUT, nodeInnovator.getInnovation());
		
		genome.addNodeGene(input1);
		genome.addNodeGene(input2);
		genome.addNodeGene(output);
		
		ConnectionGene con1 = new ConnectionGene(0, 2, 0.5f, true, connInnovator.getInnovation());
		ConnectionGene con2 = new ConnectionGene(1, 2, 0.75f, true, connInnovator.getInnovation());
		
		genome.addConnectionGene(con1);
		genome.addConnectionGene(con2);
		
		//LegacyGenomePrinter.printGenome(genome, "output/add_nod_mut_test_before.png");
		printer.showGenome(genome, "before");
		
		genome.addNodeMutation(r, connInnovator, nodeInnovator);
		
		//LegacyGenomePrinter.printGenome(genome, "output/add_nod_mut_test_after.png");
		printer.showGenome(genome, "after");
	}

}
