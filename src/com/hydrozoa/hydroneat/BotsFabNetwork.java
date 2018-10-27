package com.hydrozoa.hydroneat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * iterates through network performing forward propogation.
 * 
 * @author Bada
 */

//TODO: make setup method a class constructor.
public class BotsFabNetwork {
	// PERSISTENT DATA STRUCTURES
	private Map<Integer, Float> srecurrentPositions = new HashMap<Integer, Float>(); // a flag for weight and node
																						// position of recurrent
																						// connections
	private List<Integer> singene = new ArrayList<Integer>(); // in connections
	private List<Integer> soutgene = new ArrayList<Integer>(); // out connections
	private List<Float> scheckWeight = new ArrayList<Float>(); // connection weights
	private List<Integer> soutputs = new ArrayList<Integer>(); // output nodes
	private List<Integer> sinputs = new ArrayList<Integer>(); // input nodes

	// PRIORITY: OPTIMIZE SETUP, INTEGRATE RUN METHOD INTO SETUP TO
	// SIMPLIFY/REFACTOR RUN. STORE RUN ALGORITHM AS SORTED CONNECTIONWEIGHT, INGENE
	// AND OUTGENE IN QUEUE STRUCTURE FOR QUICK POP BASED ALGORITHM (LOOK AHEAD ONE
	// INDEX TO SEE WHEN WE SHOULD ACCUMULATE AND ACTIVATE)
	public void setup(Genome genome) {// consider sorting connections into rows for fast propogation or into sequence
										// of connections for faster propagation (I want to pop one data structure and
										// pop/push signals on one other structure to propagate).

		genome.getConnectionGenes().forEach((x, l) -> {
			if (l.isExpressed()) {
				singene.add(l.getInNode());
				soutgene.add(l.getOutNode());
				scheckWeight.add(l.getWeight());
			}
		});
		
		for (int i = 0; i < singene.size(); i++) { // place recurrent positions.
			if (singene.get(i).equals(soutgene.get(i)) && !srecurrentPositions.containsKey(singene.get(i))) {
//				System.out.println("recurrent exception! @ " + soutgene.get(i) + " : " + singene.get(i));
				srecurrentPositions.put(soutgene.remove(i), scheckWeight.remove(i));
				singene.remove(i);
			}
		}
		genome.getNodeGenes().forEach((k, v) -> { // mark input and output nodes for start/finish of current run()
													// method
			if (v.getType() == NodeGene.TYPE.INPUT) {
				sinputs.add(v.getId());
			} else if (v.getType() == NodeGene.TYPE.OUTPUT) {
				soutputs.add(v.getId());
			}
		});
	}

	// use a separate structure like last time and implement an input array. run
	// should take input signals parameter as an index matching array to input array
	public List<Float> run(List<Float> sensors) { // REFACTOR AS SOON AS OPERATIONAL
		// TEMPORARY DATA STRUCTURES

		Map<Integer, Float> recurrentPositions = new HashMap<Integer, Float>(srecurrentPositions);
		List<Integer> ingene = new ArrayList<Integer>(singene);
		List<Integer> outgene = new ArrayList<Integer>(soutgene);
		List<Float> checkWeight = new ArrayList<Float>(scheckWeight);

		List<Integer> outputs = new ArrayList<Integer>(soutputs);
		List<Integer> inputs = new ArrayList<Integer>(sinputs); // not clearing old lists...

		List<Float> connectionSignals = new ArrayList<Float>(); // runtime data structures
		List<Integer> nodeSignals = new ArrayList<Integer>();
		Map<Integer, Float> recurrentSignals = new HashMap<Integer, Float>();

		// check if inputs match sensor length if not throw error.
		if (sensors.size() != inputs.size()) {
			throw new IllegalArgumentException("FATAL ERROR: INPUTS AND SENSORS DO NOT ALIGN" + sensors + inputs);	// error will now have stack info (compared to printing to std)
		}

		// INITIALIZE
		// too many loops for this procedure. while(!inputs.isEmpty) is only needed due
		// to for() index not being adjusted after remove operation
		while (!inputs.isEmpty()) { // prepare the network for forward propagation (inputs I.E.: row 0).
			for (int i = 0; i < inputs.size(); i++) {
				int inputVal = inputs.remove(i);
				float sensorVal = sensors.remove(i);
				while (ingene.contains(inputVal)) {
					for (int g = 0; g < ingene.size(); g++) {
						if (ingene.get(g).equals(inputVal)) {
							nodeSignals.add(outgene.remove(g));
							connectionSignals.add(checkWeight.remove(g) * sensorVal); // multiply weight by input value
							ingene.remove(g);
						}
					}
				}
			}
		}

		final long startTime = System.currentTimeMillis();

		// GR8 Squisher 2.0
		for (int n = 0; n < nodeSignals.size(); n++) { // sum initial nodeSignals then propagating through hidden nodes
			Integer val = nodeSignals.get(n);
			for (int m = n + 1; m < nodeSignals.size(); m++) {
				if (val.equals(nodeSignals.get(m))) {
					connectionSignals.set(n, connectionSignals.get(n) + connectionSignals.remove(m));
					nodeSignals.remove(m);
					n--;// was n=0;
				}
			}
		}

		// EXECUTE
		while (!ingene.isEmpty() && !outgene.isEmpty()) { // too many loops for this procedure
			for (int i = 0; i < nodeSignals.size(); i++) {
				if (outgene.contains(nodeSignals.get(i)) || outputs.contains(nodeSignals.get(i))) {
					continue;
				}

				// check for recurrent positions and initialize signals or process signals.
				if (recurrentPositions.containsKey(nodeSignals.get(i))) { // this needs to see squisher in time...
					int val = nodeSignals.get(i); // this condition must be before default condition so recurrent
													// connections are handled marginally prior to forward propagation
					if (recurrentSignals.containsKey(val)) {
						connectionSignals.add(recurrentSignals.remove(val)); // send activated connectionSignals
						nodeSignals.add(val);

						recurrentSignals.put(val, recurrentPositions.remove(val) * connectionSignals.get(i));
						recurrentPositions.remove(val);
					} else { // initialize recurrent signals hashmap for that value.
						recurrentSignals.put(val, recurrentPositions.remove(val) * connectionSignals.get(i));

						recurrentPositions.remove(val); // ERROR: removed but not resolved below
					}
				} else {

					int val = nodeSignals.get(i);

					if (outputs.contains(val)) {
						System.out.println("FATAL ERROR IN THE LOOP: DESTROYING OUTNODE IN NODESIGNALS: " + val);
						System.out.println(ingene);
						System.out.println(outgene);
						System.out.println(nodeSignals);
					}

					//float outSignal = (float) (1f / (1f + Math.pow(2.7182818, (-4.8 * connectionSignals.remove(i)))));
					float outSignal = (float)(1f/( 1f + Math.exp(-4.9d * connectionSignals.remove(i))));
					
//						float outSignal = connectionSignals.remove(i); // for testing/tracing purposes
					nodeSignals.remove(i);

					while (ingene.contains(val)) {
						int j = ingene.indexOf(val);

						// process non-recurrent signals which have summed all incoming
						// connectionSignals
						connectionSignals.add((checkWeight.remove(j)) * outSignal);
						nodeSignals.add(outgene.remove(j));
						ingene.remove(j);
					}
				}
				// GR8 squisher 2.0
				for (int k = 0; k < nodeSignals.size(); k++) {
					Integer vals = nodeSignals.get(k);
					for (int m = k + 1; m < nodeSignals.size(); m++) {
						if (vals.equals(nodeSignals.get(m))) {
							connectionSignals.set(k, connectionSignals.get(k) + connectionSignals.remove(m));
							nodeSignals.remove(m);
							k--;
						}
					}
				}

			} // EOF

		} // EOW

		final long endTime = System.currentTimeMillis();
//		System.out.println("Total execution time: " + (endTime - startTime));
//		System.out.println("END OF FORWARD PROPAGATION");
//		System.out.println("connectionSignals: " + connectionSignals);
//		System.out.println("Signals to be pushed out: ");
//		for (int i = 0; i < connectionSignals.size(); i++) {
//			System.out.println((float) (1 / (1 + Math.pow(2.7182818, (-4.8 * connectionSignals.get(i))))));
//		}
//		System.out.println("nodeSignals: " + nodeSignals);
//		System.out.println("ingenes: " + ingene);
//		System.out.println("outgenes: " + outgene);
//		System.out.println("recurrentSignals: " + recurrentSignals);
//		System.out.println(outputs);
		
//		System.out.println("SORTING NODESIGNALS" + nodeSignals);
//		System.out.println("pre NodeSignals: " + nodeSignals);
//		System.out.println("pre ConnectionSignals: " + connectionSignals);

		for (int i = 0; i < nodeSignals.size(); i++) {
			for (int j = i + 1; j < nodeSignals.size(); j++) {
				if (nodeSignals.get(i).compareTo(nodeSignals.get(j)) >= 0) { // should never have equal to zero. can
																				// we
																				// leave this to infinit loop in
																				// error
																				// case?
					nodeSignals.add(i, nodeSignals.remove(j));
					connectionSignals.add(i, connectionSignals.remove(j));
					i = 0; // do it work tho?
				}
			}
		}

		for (int i = 0; i < connectionSignals.size(); i++) { // final activation
			//connectionSignals.set(i, (float) (1f / (1f + Math.pow(2.7182818, (-4.8 * connectionSignals.get(i))))));
			connectionSignals.set(i, (float)(1f/( 1f + Math.exp(-4.9d * connectionSignals.get(i)))));

		}
//		System.out.println("NodeSignals: " + nodeSignals);
//		System.out.println("ConnectionSignals: " + connectionSignals);

		return (connectionSignals); // ensure that connectionSignals keep index values of nodes consistent.
									// since connections can arrive at different times based on topologies, sort the
									// outputs by incrementing nodeSignal values (output 1 comes before 2 etc.)

	}
}

