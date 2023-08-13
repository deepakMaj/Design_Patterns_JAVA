package singleton;

import java.io.File;
import java.io.IOException;

// If singleton throws an exception then initialize the singleton in static with try/catch block
class StaticBlockSingleton {
	private StaticBlockSingleton() throws IOException {
		System.out.println("Singleton is initializing");
		File.createTempFile(".", ".");
	}
	
	private static StaticBlockSingleton instance;
	static {
		try {
			instance = new StaticBlockSingleton();
		} catch (Exception e) {
			System.out.println("Failed to create singleton");
		}
	}
	
	public static StaticBlockSingleton getInstance() {
		return instance;
	}
}

public class StaticBlockSingletonDemo {
	public static void main(String[] args) {
		StaticBlockSingleton.getInstance();
	}
}
