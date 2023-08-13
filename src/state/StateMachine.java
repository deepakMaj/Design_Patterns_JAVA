package state;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

enum PhoneState {
	OFF_HOOK, // starting
	ON_HOOK, // terminal
	CONNECTING,
	CONNECTED,
	ON_HOLD
};

enum Trigger {
	CALL_DAILED,
	HUNG_UP,
	CALL_CONNECT,
	PLACED_ON_HOLD,
	TAKEN_OFF_HOLD,
	LEFT_MESSAGE,
	STOP_USING_PHONE
};

public class StateMachine {
	private static Map<PhoneState, List<ImmutablePair<Trigger, PhoneState>>> rules = new HashMap<>() ;
	
	static {
		rules.put(PhoneState.OFF_HOOK, List.of(
				new ImmutablePair<>(Trigger.CALL_DAILED, PhoneState.CONNECTING),
				new ImmutablePair<>(Trigger.STOP_USING_PHONE, PhoneState.ON_HOOK)
		));
		rules.put(PhoneState.CONNECTING, List.of(
				new ImmutablePair<>(Trigger.CALL_CONNECT, PhoneState.CONNECTED),
				new ImmutablePair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK)
		));
		rules.put(PhoneState.CONNECTED, List.of(
				new ImmutablePair<>(Trigger.LEFT_MESSAGE, PhoneState.OFF_HOOK),
				new ImmutablePair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK),
				new ImmutablePair<>(Trigger.PLACED_ON_HOLD, PhoneState.ON_HOLD)
		));
		rules.put(PhoneState.ON_HOLD, List.of(
				new ImmutablePair<>(Trigger.TAKEN_OFF_HOLD, PhoneState.CONNECTED),
				new ImmutablePair<>(Trigger.HUNG_UP, PhoneState.OFF_HOOK)
		));
	}

	private static PhoneState currentState = PhoneState.OFF_HOOK;
	private static PhoneState exitState = PhoneState.ON_HOOK;
	
	public static void main(String[] args) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("Phone is currently in %s".formatted(currentState));
			System.out.println("Select a trigger:");
			
			for(int i = 0; i < rules.get(currentState).size(); ++i) {
				Trigger trigger = rules.get(currentState).get(i).left;
				System.out.println("%d. %s".formatted(i, trigger));
			}
			
			boolean parseOk = false;
			int choice = 0;
			
			do {
				try {
					System.out.println("Please enter your choice: ");
					choice = Integer.parseInt(console.readLine());
					parseOk = choice >= 0 && choice < rules.get(currentState).size();
				} catch (Exception e) {
					parseOk = false;
				}
			} while(!parseOk);
			
			currentState = rules.get(currentState).get(choice).right;
			
			if(currentState == exitState) {
				break;
			}
		}
		
		System.out.println("And we are done!");
	}
}
