package observer;

import java.util.ArrayList;
import java.util.List;

class PropertyChangeEventArgs<T> {
	@SuppressWarnings("unused")
	private T source;
	public String propertyName;
	public Object newValue;
	
	public PropertyChangeEventArgs(T source, String propertyName, Object newValue) {
		super();
		this.source = source;
		this.propertyName = propertyName;
		this.newValue = newValue;
	}
}

interface Observer<T> {
	void handle(PropertyChangeEventArgs<T> args);
}

class Observable<T> {
	private List<Observer<T>> observers = new ArrayList<>();
	
	public void subscribe(Observer<T> observer) {
		observers.add(observer);
	}
	
	protected void propertyChanged(T source, String propertyName, Object value) {
		for(Observer<T> observer : observers) {
			observer.handle(new PropertyChangeEventArgs<T>(source, propertyName, value));
		}
	}
}

class Person extends Observable<Person> {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if(this.age == age) return;
		this.age = age;
		propertyChanged(this, "age", age);
	}
}

public class Observer_Observable implements Observer<Person> {
	public static void main(String[] args) {
		new Observer_Observable();
	}
	
	public Observer_Observable() {
		Person person = new Person();
		person.subscribe(this);
		for(int  i = 20; i <= 24; ++i) {
			person.setAge(i);
		}
	}

	@Override
	public void handle(PropertyChangeEventArgs<Person> args) {
		System.out.println("Persons's %s has changed to %d".formatted(args.propertyName, args.newValue));
	}
}
