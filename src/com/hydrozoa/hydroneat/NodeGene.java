package com.hydrozoa.hydroneat;

/**
 * @author hydrozoa
 */
public class NodeGene {
	
	public enum TYPE {
		INPUT,
		HIDDEN,
		OUTPUT,
		;
	}
	
	private TYPE type;
	private int id;
	
	public NodeGene(TYPE type, int id) {
		this.type = type;
		this.id = id;
	}
	
	public NodeGene(NodeGene toBeCopied) {
		this.type = toBeCopied.type;
		this.id = toBeCopied.id;
	}

	public TYPE getType() {
		return type;
	}

	public int getId() {
		return id;
	}
}
