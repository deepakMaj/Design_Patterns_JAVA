package proxy;

import java.util.Objects;

class Property<T> {
	private T value;
	
	public Property(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		// Logging
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property<?> other = (Property<?>) obj;
		return Objects.equals(value, other.value);
	}
}

class Creature {
	private Property<Integer> agility = new Property<>(10);

	public void setAgility(int value) {
		agility.setValue(value);
	}
	
	public int getAgility() {
		return agility.getValue();
	}
}

public class PropertyProxy {
	public static void main(String args[]) {
		Creature creature = new Creature();
		creature.setAgility(40);
	}
}
