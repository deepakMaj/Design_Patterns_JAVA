package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class BasicSingleton implements Serializable {
	private static final long serialVersionUID = 1L;
	private int value = 0;
	
	private BasicSingleton() {}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private static final BasicSingleton INSTANCE = new BasicSingleton();
	
	public static BasicSingleton getInstance() {
		return INSTANCE;
	}
	
	protected Object readResolve() {
		return INSTANCE;
	}
}

public class BasicSingletonDemo {
	public static void main(String[] args) throws Exception {
		BasicSingleton singleton = BasicSingleton.getInstance();
		singleton.setValue(10);
		
		String filename = "singleton.bin";
		saveToFile(singleton, filename);
		
		singleton.setValue(22);
		
		BasicSingleton singleton2 = readFromFile(filename);
		
		System.out.println(singleton == singleton2);
		System.out.println(singleton.getValue());
		System.out.println(singleton2.getValue());
	}
	
	static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
		try(FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(singleton);
		}
	}
	
	static BasicSingleton readFromFile(String filename) throws Exception {
		try(FileInputStream  fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (BasicSingleton) in.readObject();
		}
	}
}
