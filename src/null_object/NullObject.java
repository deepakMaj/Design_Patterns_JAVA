package null_object;

import java.lang.reflect.Proxy;

interface Log {
	void info(String msg);
	void warning(String msg);
}

class BankAccount implements Log {
	private Log log;
	private int balance;
	
	public BankAccount(Log log) {
		this.log = log;
	}
	
	public void deposit(int amount) {
		this.balance += amount;
		log.info("Deposited %s".formatted(balance));
	}

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warning(String msg) {
		// TODO Auto-generated method stub
		
	}
}

final class NullLog implements Log {

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warning(String msg) {
		// TODO Auto-generated method stub
		
	}
	
}

class ConsoleLog implements Log{

	@Override
	public void info(String msg) {
		System.out.println(msg);
	}

	@Override
	public void warning(String msg) {
		System.out.println("%s WARNING".formatted(msg));
	}
	
}

public class NullObject {
	@SuppressWarnings("unchecked")
	public static <T> T noOp(Class<T> itf) {
		return (T) Proxy.newProxyInstance(
				itf.getClassLoader(),
				new Class<?>[] { itf },
				((proxy, method, args) -> {
					if(method.getReturnType().equals(Void.TYPE)) {
						return null;
					} else {
						return method.getReturnType().getConstructor().newInstance(args);
					}
				})
		);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Log log = noOp(Log.class);
		BankAccount account = new BankAccount(log);
	}

}
