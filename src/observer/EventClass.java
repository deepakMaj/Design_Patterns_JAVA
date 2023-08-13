package observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Event<TArgs> {
	private int count = 0;
	Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();
	
	public Subscription addHandler(Consumer<TArgs> handler) {
		int i = count;
		handlers.put(count++, handler);
		return new Subscription(this, i);
	}
	
	public void fire(TArgs targs) {
		for(Consumer<TArgs>  handler : handlers.values()) {
			handler.accept(targs);
		}
	}
	
	public class Subscription implements AutoCloseable {
		private Event<TArgs> event;
		private int id;
		
		public Subscription(Event<TArgs> event, int id) {
			this.event = event;
			this.id = id;
		}
		
		@Override
		public void close() {
			event.handlers.remove(id);
		}
	}
}

class PropertyChangeEventArgss {
	public Object source;
	public String propertyName;

	public PropertyChangeEventArgss(Object source, String propertyName) {
		super();
		this.source = source;
		this.propertyName = propertyName;
	}
}

class PersonNew {
	public Event<PropertyChangeEventArgss> propertyChanged = new Event<>();
	private int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		if(this.age == age) return;
		this.age = age;
		propertyChanged.fire(new PropertyChangeEventArgss(this, "age"));
	}
}

public class EventClass  {
	public static void main(String[] args) {
		PersonNew person = new PersonNew();
		Event<PropertyChangeEventArgss>.Subscription sub = person.propertyChanged.addHandler(x -> {
			System.out.println("Person's %s has changed".formatted(x.propertyName));
		});
		person.setAge(17);
		person.setAge(18);
		sub.close();
		person.setAge(19);
	}
}
