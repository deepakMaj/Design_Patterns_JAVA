package mediator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

class FootballPlayer {
	private int goalScored = 0;
	private EventBroker broker;
	public String name;
	
	public FootballPlayer(int goalScored, EventBroker broker, String name) {
		super();
		this.goalScored = goalScored;
		this.broker = broker;
		this.name = name;
	}
	
	public void scored() {
		this.broker.publish(++goalScored);
	}
}

class FootballCoach {
	public FootballCoach(EventBroker broker) {
		broker.subscribe(i -> System.out.println("Hey you scored %d number of goals.".formatted(i)));
	}
}

public class EventBroker extends Observable<Integer> {
	private List<Observer<Integer>> observers = new ArrayList<>();

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		EventBroker broker = new EventBroker();
		FootballPlayer player = new FootballPlayer(0, broker, "Messi");
		FootballCoach coach = new FootballCoach(broker);
		
		player.scored();
		player.scored();
		player.scored();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void subscribeActual(Observer<? super Integer> observer) {
		observers.add((Observer<Integer>) observer);
	}
	
	public void publish(int value) {
		for(Observer<? super Integer> o : observers) {
			o.onNext(value);
		}
	}
}
