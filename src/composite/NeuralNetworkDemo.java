package composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

interface SomeNeurons extends Iterable<Neuron> {
	default void connectTo(SomeNeurons other) {
		if(this == other) return;
		
		for(Neuron from : this) {
			for(Neuron to : other) {
				from.out.add(to);
				to.in.add(from);
			}
		}
	}
}

class Neuron implements SomeNeurons {
	public ArrayList<Neuron> in, out;

	@Override
	public Iterator<Neuron> iterator() {
		return Collections.singleton(this).iterator();
	} 
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons {
	private static final long serialVersionUID = 1L;
	
}

public class NeuralNetworkDemo {
	public static void main(String[] args) {
		Neuron neuron = new Neuron();
		Neuron neuron2 = new Neuron();
		NeuronLayer layer = new NeuronLayer();
		NeuronLayer layer2 = new NeuronLayer();
		
		neuron.connectTo(neuron2);
		neuron.connectTo(layer);
		layer.connectTo(neuron2);
		layer.connectTo(layer2);
	}
}
