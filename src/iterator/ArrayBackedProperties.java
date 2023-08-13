package iterator;

import java.util.Iterator;
import java.util.stream.IntStream;

class SimpleCreature {
	// Cannot perform some operations such as sum, max, average... if field increases in future.
	private int stength, agility, intelligence;

	public int getStength() {
		return stength;
	}

	public void setStength(int stength) {
		this.stength = stength;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
}

class Creature implements Iterable<Integer> {
	private int[] stats = new int[3];
	
	public int getStrength() {
		return stats[0];
	}
	
	public void setStrength(int value) {
		this.stats[0] = value;
	}
	
	public int getAgility() {
		return stats[1];
	}
	
	public void setAgility(int value) {
		this.stats[1] = value;
	}
	
	public int getIntelligence() {
		return stats[2];
	}
	
	public void setIntelligence(int value) {
		this.stats[2] = value;
	}
	
	public int sum() {
		return IntStream.of(stats).sum();
	}
	
	public int max() {
		return IntStream.of(stats).max().getAsInt();
	}
	
	public double average() {
		return IntStream.of(stats).average().getAsDouble();
	}

	@Override
	public Iterator<Integer> iterator() {
		return IntStream.of(stats).iterator();
	}
}

public class ArrayBackedProperties {

	public static void main(String[] args) {
		Creature creature = new Creature();
		creature.setStrength(12);
		creature.setAgility(20);
		creature.setIntelligence(10);
		System.out.println("Sum %d; Max %d; Average: %f".formatted(creature.sum(), creature.max(), creature.average()));
	}
}
