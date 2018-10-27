package com.hydrozoa.hydroneat.test;

import com.hydrozoa.hydroneat.NeuralNetwork.Neuron;

/**
 * Tests that neurons are working correctly and as expected.
 * 
 * @author hydrozoa
 */
public class TestNeuron {
	
	public static void main(String[] args) {
		System.out.println("========TEST 1========");
		Neuron test1 = new Neuron();
		test1.addInputConnection();
		test1.addInputConnection();
		test1.addInputConnection();
		
		test1.feedInput(1f);
		System.out.println("Neuron reports that isReady()="+test1.isReady()+", and is expected to report that isReady()=false");
		test1.feedInput(1f);
		System.out.println("Neuron reports that isReady()="+test1.isReady()+", and is expected to report that isReady()=false");
		test1.feedInput(1f);
		System.out.println("Neuron reports that isReady()="+test1.isReady()+", and is expected to report that isReady()=true");
		System.out.println("Sum=3");
		
		System.out.println("Calculating...");
		float output = test1.calculate();
		System.out.println("Output: "+output);
		
		System.out.println("========TEST 2========");
		Neuron test2 = new Neuron();
		test2.addInputConnection();
		test2.addInputConnection();
		test2.addInputConnection();
		
		test2.feedInput(0f);
		test2.feedInput(0.5f);
		test2.feedInput(-0.5f);
		System.out.println("Sum=0");
		
		System.out.println("Calculating...");
		output = test2.calculate();
		System.out.println("Output: "+output);
		
		System.out.println("========TEST 3========");
		Neuron test3 = new Neuron();
		test3.addInputConnection();
		test3.addInputConnection();
		test3.addInputConnection();
		
		test3.feedInput(-2f);
		test3.feedInput(-2f);
		test3.feedInput(-2f);
		System.out.println("Sum=-6");
		
		System.out.println("Calculating...");
		output = test3.calculate();
		System.out.println("Output: "+output);
		
		System.out.println("========TEST 4========");
		Neuron test4 = new Neuron();
		test4.addInputConnection();
		test4.addInputConnection();
		test4.addInputConnection();
		
		test4.feedInput(-20f);
		test4.feedInput(-20f);
		test4.feedInput(-20f);
		System.out.println("Sum=-60");
		
		System.out.println("Calculating...");
		output = test4.calculate();
		System.out.println("Output: "+output);
	}
}
