package com.hydrozoa.hydroneat;

/**
 * @author hydrozoa
 */
public class ConnectionGene {
	
	private int inNode;
	private int outNode;
	private float weight;
	private boolean expressed;
	private int innovation;
	
	public ConnectionGene(int inNode, int outNode, float weight, boolean expressed, int innovation) {
		this.inNode = inNode;
		this.outNode = outNode;
		this.weight = weight;
		this.expressed = expressed;
		this.innovation = innovation;
	}
	
	public ConnectionGene(ConnectionGene toBeCopied) {
		this.inNode = toBeCopied.inNode;
		this.outNode = toBeCopied.outNode;
		this.weight = toBeCopied.weight;
		this.expressed = toBeCopied.expressed;
		this.innovation = toBeCopied.innovation;
	}

	public int getInNode() {
		return inNode;
	}

	public int getOutNode() {
		return outNode;
	}

	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float newWeight) {
		this.weight = newWeight;
	}

	public boolean isExpressed() {
		return expressed;
	}
	
	public void disable() {
		expressed = false;
	}

	public int getInnovation() {
		return innovation;
	}
	
	public ConnectionGene copy() {
		return new ConnectionGene(inNode, outNode, weight, expressed, innovation);
	}
	
}
