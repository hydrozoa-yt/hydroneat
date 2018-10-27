# HydroNEAT
Follow development on [YouTube](https://www.youtube.com/channel/UCQ3I9UG_zvcQOs6tTPyyz_A).

This is an implementation of NEAT. It is experimental for now, but it does solve XOR satisfactorily. I've tried to keep this implementation as true to the [original paper](http://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf) as possible.

## Features
* Genomes
   * Add connection mutation
   * Add node mutation
   * Small perturbing mutations
   * Crossover
* Networks can be calculated, with sigmoid activation function
* Configuration can be easily tweaked
* Evaluator with easily pluggable fitness scoring
* Visualization of genomes as graphs

The project also features tools for speciation (calculation of genomic distance), although speciation is not incorporated into the Evaluator yet. Instead, the Evaluator employs a more conventional evolutionary algorithm where:
1. The most fit genome is carried into next generation unchanged
1. The lowest performing members of the population are killed
1. The remaining genomes are paired randomly as parents to create the new generation
   1. A percentage of the next generation is created using sexual reproduction (crossover)
   1. A percentage of the next generation is created using asexual reproduction (mutation without crossover)
   1. All new genomes are either perturbed in their weights, or assigned new weights - both happen randomly
  
## Genome visualization
HydroNEAT comes with a great visualizer, that shows you what a given genome will look like as a network.
![graph_image](https://i.imgur.com/P8iSfbC.png)

For more info, look at GenomePrinter.java.

## Running it on your own
Be sure to tweak the Evaluator to suit your needs. If you want to run this for yourself, the tests are a good place to start.

## Contact
I can be contacted on hydrozoa.rs [at] gmail [dot] com. 

If you use this code, please credit me.
