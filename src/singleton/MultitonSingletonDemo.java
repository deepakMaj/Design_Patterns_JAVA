package singleton;

import java.util.HashMap;

enum Subsystem {
	PRIMARY,
	AUXILLARY,
	FALLBACK
}

class Printer {
	private static int instanceCount  = 0;
	
	private Printer() {
		instanceCount++;
		System.out.println("A total of %d instances created so far.".formatted(instanceCount));
	}
	
	private static HashMap<Subsystem, Printer> instances = new HashMap<>();
	
	public static Printer get(Subsystem subsystem) {
		if(instances.containsKey(subsystem))
			return instances.get(subsystem);
		
		Printer instance = new Printer();
		instances.put(subsystem, instance);
		return instance;
	}
}

public class MultitonSingletonDemo {
	public static void main(String[] args) {
		Printer.get(Subsystem.PRIMARY);
		Printer.get(Subsystem.AUXILLARY);
		Printer.get(Subsystem.AUXILLARY); // No new auxiliary printer is created 
	}
}
