package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

enum EnumBasedSingleton {
	INSTANCE;
	
	EnumBasedSingleton() {
		value = 42;
	}
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

public class EnumSingletonDemo {
	// Serialization
	static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception {
		try(FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(singleton);
		}
	}
	
	// Deserialization
	static EnumBasedSingleton readFromFile(String filename) throws Exception {
		try(FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (EnumBasedSingleton) in.readObject();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String filename = "singleton.bin";
		EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
		singleton.setValue(111);
		saveToFile(singleton, filename);
		
		System.out.println(readFromFile(filename).getValue());
	}
}
