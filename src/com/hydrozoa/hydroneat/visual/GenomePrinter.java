package com.hydrozoa.hydroneat.visual;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import com.hydrozoa.hydroneat.ConnectionGene;
import com.hydrozoa.hydroneat.Genome;
import com.hydrozoa.hydroneat.NodeGene;
import com.hydrozoa.hydroneat.NodeGene.TYPE;

/**
 * @author hydrozoa
 */
public class GenomePrinter {
	
	protected String STYLESHEET =
			"edge {" +
	        		"text-background-mode: rounded-box;" +
	        		"text-background-color: black;" +
	        		"text-alignment: center;" +
	        		"text-color: white;" +
	        		"arrow-size:5;" +
	        		"text-size: 10;" +
	        "}" +
	        		
			"edge.inactive {" +
				"fill-color:gray;" +
			"}" +
	        		
	        "node {" +
	        		"fill-color: black;" +
	        		"text-background-mode: rounded-box;" +
	        		"text-background-color: black;" +
	        		"text-alignment: center;" +
	        		"text-color: white;" +
	        		"size: 30;" +
	        		"text-size: 10;" +
	        "}" +
	        
	        "node.i {" +
	        	"fill-color: red;" +
	        "}" +
	        
	        "node.h {" +
	        	"fill-color: green;" +
	        "}" +
	        
			"node.o {" +
				"fill-color: blue;" +
			"}";
	
	public void showGenome(Genome genome, String title) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer"); // use advanced viewer
		
		Random random = new Random(1337L);
		
		Graph graph = new MultiGraph("Genome 1");
		graph.setAttribute("ui.title", title);
		graph.addAttribute("ui.stylesheet", STYLESHEET);
		
		List<NodeGene> inputs = new LinkedList<NodeGene>();
		List<NodeGene> outputs = new LinkedList<NodeGene>();
		List<NodeGene> hidden = new LinkedList<NodeGene>();
		
		for (NodeGene node : genome.getNodeGenes().values()) {
			if (node.getType() == TYPE.INPUT) {
				inputs.add(node);
			} else if (node.getType() == TYPE.OUTPUT) {
				outputs.add(node);
			} else { // hidden
				hidden.add(node);
			}
		}
		
		for (int i = 0; i < inputs.size(); i++) {
			NodeGene nodeGene = inputs.get(i);
			
			Node n = graph.addNode("N"+nodeGene.getId());
			n.addAttribute("ui.label", "id="+nodeGene.getId());
			
			n.addAttribute("layout.frozen");
			n.addAttribute("y", 0);
			n.addAttribute("x", 1f/(inputs.size()+1) * (i+1));
			n.addAttribute("ui.class", "i");
		}
		
		for (int i = 0; i < outputs.size(); i++) {
			NodeGene nodeGene = outputs.get(i);
			Node n = graph.addNode("N"+nodeGene.getId());
			n.addAttribute("ui.label", "id="+nodeGene.getId());
			
			n.addAttribute("layout.frozen");
			n.addAttribute("y", 1);
			n.addAttribute("x", 1f/(inputs.size()+1) * (i+1));
			n.addAttribute("ui.class", "o");
		}
		
		for (int i = 0; i < hidden.size(); i++) {
			NodeGene nodeGene = hidden.get(i);
			Node n = graph.addNode("N"+nodeGene.getId());
			n.addAttribute("ui.label", "id="+nodeGene.getId());
			
			n.addAttribute("layout.frozen");
			n.addAttribute("y", random.nextFloat()*0.5f+0.25f);
			n.addAttribute("x", random.nextFloat());
			n.addAttribute("ui.class", "h");
		}
		
		
		for (ConnectionGene connection : genome.getConnectionGenes().values()) {
			Edge e = graph.addEdge("C"+connection.getInnovation(), "N"+connection.getInNode(), "N"+connection.getOutNode(), true);
			e.addAttribute("ui.label", "w="+connection.getWeight()+"\n"+" in="+connection.getInnovation());
			
			if (!connection.isExpressed()) {
				e.addAttribute("ui.class", "inactive");
			}
		}
		
		graph.display();
	}
}
