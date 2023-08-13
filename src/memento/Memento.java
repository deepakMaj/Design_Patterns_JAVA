package memento;

class BankAccount {
	private int balance;
	
	public BankAccount(int balance) {
		this.balance = balance;
	}
	
	public Memento deposit(int amount) {
		this.balance += amount;
		return new Memento(balance);
	}
	
	public void restore(Memento memento) {
		this.balance = memento.balance;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
}

public class Memento {
	public int balance;
	
	public Memento(int balance) {
		this.balance = balance;
	}
	
	public static void main(String args[]) {
		BankAccount account = new BankAccount(100);
		Memento m1 = account.deposit(50);
		Memento m2 = account.deposit(500);
		
		System.out.println(account);
		
		// restore to m1
		account.restore(m1);
		System.out.println(account);
		
		// restore to m2
		account.restore(m2);
		System.out.println(account);
	}
}
