package singleton;

class LazySingleton {
	private static LazySingleton instance;
	
	private LazySingleton() {
		System.out.println("Initializing lazy singleton");
	}
	
	// Below are the ways to tackle thread safety for lazy initialization of singleton
	
	// 1. Using synchronized keyword
	public static synchronized LazySingleton getInstance() {
		if(instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
	
	// 2. double-checked locking(Not much in use)
//	public static LazySingleton getInstance() {
//		if(instance == null) {
//			synchronized (LazySingleton.class) {
//				if(instance == null) instance = new LazySingleton();
//			}
//		}
//		return instance;
//	}
}

public class LazySingletonDemo {
	public static void main(String[] args) {
		LazySingleton.getInstance();
	}
}
