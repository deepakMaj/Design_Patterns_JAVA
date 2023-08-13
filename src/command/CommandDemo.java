package command;

import java.util.List;

import com.google.common.collect.Lists;

class BankAccount {
	private int balance;
	private int overDraftLimit = -500;
	
	public void deposit(int amount) {
		this.balance += amount;
		System.out.println("Deposited %d, balance is now %d".formatted(amount, balance));
	}
	
	public boolean withdraw(int amount) {
		if(balance - amount >= overDraftLimit) {
			this.balance -= amount;
			System.out.println("Withdrawn %d, balance is now %d".formatted(amount, balance));
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
}

interface Command {
	void call();
	void undo();
}

class BankAccountCommand implements Command {
	private BankAccount account;
	public enum Action {
		DEPOSIT,
		WITHDRAW
	}
	private Action action;
	private int amount;
	private boolean succeeded;
	
	public BankAccountCommand(BankAccount account, Action action, int amount) {
		super();
		this.account = account;
		this.action = action;
		this.amount = amount;
	}

	@Override
	public void call() {
		switch(action) {
			case DEPOSIT:
				succeeded = true;
				account.deposit(amount);
				break;
			case WITHDRAW:
				succeeded = account.withdraw(amount);
				break;
		}
	}
	
	@Override
	public void undo() {
		switch(action) {
		case DEPOSIT:
			account.withdraw(amount);
			break;
		case WITHDRAW:
			if(!succeeded) return;
			account.deposit(amount);
			break;
		}
	}
}
public class CommandDemo {
	public static void main(String args[]) {
		BankAccount account = new BankAccount();
		
		List<BankAccountCommand> commands = List.of(
				new BankAccountCommand(account, BankAccountCommand.Action.DEPOSIT, 550),
				new BankAccountCommand(account, BankAccountCommand.Action.WITHDRAW, 200)
		);
		
		for(Command command : commands) {
			command.call();
		}
		
		for(Command command : Lists.reverse(commands)) {
			command.undo();
		}
	}
}
