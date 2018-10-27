package com.hydrozoa.hydroneat.test;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.Counter;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

/**
 * @author hydrozoa
 */
public class TestGeneCounting {
	
	public static void main(String[] args) {
		Counter nodeInnovator = new Counter();
		Counter connInnovator = new Counter();
		
		NodeGene[] sharedNodes = new NodeGene[10];
		for (int i = 0; i < sharedNodes.length; i++) {
			sharedNodes[i] = new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation());
		}
		
		ConnectionGene[] sharedConnections = new ConnectionGene[5];
		for (int i = 0; i < sharedConnections.length; i++) {
			sharedConnections[i] = new ConnectionGene(0, 0, 1f, true, connInnovator.getInnovation());
		}
		
		Genome genome1 = new Genome();
		Genome genome2 = new Genome();
		
		for (NodeGene g : sharedNodes) {
			genome1.addNodeGene(g);
			genome2.addNodeGene(g);
		}
		
		for (ConnectionGene g : sharedConnections) {
			genome1.addConnectionGene(g);
			genome2.addConnectionGene(g);
		}
		
		System.out.println("Number of matching genes = "+Genome.countMatchingGenes(genome1, genome2)+"\t Correct answer = "+(sharedNodes.length+sharedConnections.length));
		System.out.println("Number of disjoint genes = "+Genome.countDisjointGenes(genome1, genome2)+"\t Correct answer = 0");
		System.out.println("Number of excess genes = "+Genome.countExcessGenes(genome1, genome2)+"\t Correct answer = 0");
		System.out.println("\n");
		
		genome1.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome1.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome1.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome1.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		genome1.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		genome1.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		
		System.out.println("Number of matching genes = "+Genome.countMatchingGenes(genome1, genome2)+"\t Correct answer = "+(sharedNodes.length+sharedConnections.length));
		System.out.println("Number of disjoint genes = "+Genome.countDisjointGenes(genome1, genome2)+"\t Correct answer = 0");
		System.out.println("Number of excess genes = "+Genome.countExcessGenes(genome1, genome2)+"\t Correct answer = 6");
		System.out.println("\n");
		
		genome2.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome2.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome2.addNodeGene(new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation()));
		genome2.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		genome2.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		genome2.addConnectionGene(new ConnectionGene(0,0,1f,true,connInnovator.getInnovation()));
		
		System.out.println("Number of matching genes = "+Genome.countMatchingGenes(genome1, genome2)+"\t Correct answer = "+(sharedNodes.length+sharedConnections.length));
		System.out.println("Number of disjoint genes = "+Genome.countDisjointGenes(genome1, genome2)+"\t Correct answer = 6");
		System.out.println("Number of excess genes = "+Genome.countExcessGenes(genome1, genome2)+"\t Correct answer = 6");
		System.out.println("\n");
		
		System.out.println("Counting genes between same genomes, but with opposite parameters:");
		System.out.println("Number of matching genes = "+Genome.countMatchingGenes(genome2, genome1)+"\t Correct answer = "+(sharedNodes.length+sharedConnections.length));
		System.out.println("Number of disjoint genes = "+Genome.countDisjointGenes(genome2, genome1)+"\t Correct answer = 6");
		System.out.println("Number of excess genes = "+Genome.countExcessGenes(genome2, genome1)+"\t Correct answer = 6");
		System.out.println("\n");
		
		NodeGene finalMatchingNode = new NodeGene(TYPE.HIDDEN, nodeInnovator.getInnovation());
		ConnectionGene finalMatchingConn = new ConnectionGene(0,0,1f,true,connInnovator.getInnovation());
		
		genome1.addNodeGene(finalMatchingNode);
		genome2.addNodeGene(finalMatchingNode);
		
		genome1.addConnectionGene(finalMatchingConn);
		genome2.addConnectionGene(finalMatchingConn);
		
		System.out.println("Number of matching genes = "+Genome.countMatchingGenes(genome1, genome2)+"\t Correct answer = "+(sharedNodes.length+sharedConnections.length+2));
		System.out.println("Number of disjoint genes = "+Genome.countDisjointGenes(genome1, genome2)+"\t Correct answer = 12");
		System.out.println("Number of excess genes = "+Genome.countExcessGenes(genome1, genome2)+"\t Correct answer = 0");
	}

}
