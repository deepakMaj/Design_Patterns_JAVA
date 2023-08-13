package singleton;

// Helps achieve thread safety
class InnerStaticSingleton {
	private InnerStaticSingleton() {}
	
	private static class Impl {
		private static final InnerStaticSingleton  INSTANCE = new InnerStaticSingleton();
	}
	
	public static InnerStaticSingleton getInstance() {
		return Impl.INSTANCE;
	}
}

public class InnerStaticSingletonDemo {
	public static void main(String[] args) {
		InnerStaticSingleton.getInstance();
	}
}
