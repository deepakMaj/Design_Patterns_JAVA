package mediator;

import java.util.ArrayList;
import java.util.List;

class Person {
	public String name;
	private List<String> chatLog = new ArrayList<>();
	public ChatRoom room;
	public Person(String name) {
		super();
		this.name = name;
	}
	
	public void receive(String sender, String message) {
		String str = "%s: %s".formatted(sender, message);
		System.out.println("[%s's chat session] %s".formatted(name, str));
		chatLog.add(str);
	}
	
	public void say(String message) {
		room.broadcast(name, message);
	}
	
	public void privateMessage(String who, String message) {
		room.message(name, who, message);
	}
}

public class ChatRoom {
	private List<Person> people = new ArrayList<>();
	
	public void join(Person person) {
		String joinMessage = "%s joins the room".formatted(person.name);
		broadcast("room", joinMessage);
		person.room = this;
		people.add(person);
	}
	
	public void broadcast(String source, String message) {
		for(Person person : people) {
			if(person.name != source) {
				person.receive(source, message);
			}
		}
	}
	
	public void message(String source, String destination, String message) {
		people
			.stream()
			.filter(p -> p.name.equals(destination))
			.findFirst()
			.ifPresent(p -> p.receive(source, message));
	}
	
	public static void main(String[] args) {
		ChatRoom room = new ChatRoom();
		Person john = new Person("John");
		Person jane = new Person("Jane");
		room.join(john);
		room.join(jane);
		
		john.say("Hi Jane");
		jane.say("Hi John");
		
		Person simon = new Person("Simon");
		room.join(simon);
		simon.say("Hi everyone");
		
		jane.privateMessage("Simon", "Hello Simon");
	}
}
