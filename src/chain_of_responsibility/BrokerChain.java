package chain_of_responsibility;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// Command-Query Separation

class Event<Args> {
	private int index;
	private Map<Integer, Consumer<Args>> handlers = new HashMap<>();
	
	public int subscribe(Consumer<Args> handler) {
		int i = index;
		handlers.put(index++, handler);
		return i;
	}
	
	public void unsubscribe(int key) {
		handlers.remove(key);
	}
	
	public void fire(Args args) {
		for(Consumer<Args> handler : handlers.values()) {
			handler.accept(args);
		}
	}
}

class Query {
	public String creatureName;
	enum Argument {
		ATTACK,
		DEFENSE
	}
	public Argument argument;
	public int result;
	
	public Query(String creatureName, Argument argument, int result) {
		this.creatureName = creatureName;
		this.argument = argument;
		this.result = result;
	}
}

// Mediator
class Game {
	public Event<Query> queries = new Event<>();
}

class CreatureNew {
	private Game game;
	public String name;
	public int baseAttack, baseDefense;
	
	public CreatureNew(Game game, String name, int baseAttack, int baseDefense) {
		this.game = game;
		this.name = name;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}

	int getAttack() {
		Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
		game.queries.fire(query);
		return query.result;
	}
	
	int getDefense() {
		Query query = new Query(name, Query.Argument.DEFENSE, baseDefense);
		game.queries.fire(query);
		return query.result;
	}

	@Override
	public String toString() {
		return "CreatureNew [name=" + name + ", baseAttack=" + getAttack()  + ", baseDefense="
				+ getDefense() + "]";
	}
}

class CreatureNewModifierNew {
	protected Game game;
	protected CreatureNew creature;
	
	public CreatureNewModifierNew(Game game, CreatureNew creature) {
		super();
		this.game = game;
		this.creature = creature;
	}
}

class DoubleAttackModifierNew extends CreatureNewModifierNew implements AutoCloseable {
	private final int token;
	
	public DoubleAttackModifierNew(Game game, CreatureNew creature) {
		super(game, creature);
		token = game.queries.subscribe(q -> {
			if(q.creatureName.equals(creature.name) && q.argument == Query.Argument.ATTACK) {
				q.result *= 2;
			}
		});
	}

	@Override
	public void close() {
		game.queries.unsubscribe(token);
	}
}

class IncreaseDefenseModifierNew extends CreatureNewModifierNew {
	public IncreaseDefenseModifierNew(Game game, CreatureNew creature) {
		super(game, creature);
		game.queries.subscribe(q -> {
			if(q.creatureName.equals(creature.name) && q.argument == Query.Argument.DEFENSE) {
				q.result *= 2;
			}
		});
	}
}

public class BrokerChain {
	public static void main(String args[]) {
		Game game = new Game();
		CreatureNew goblin = new CreatureNew(game, "Goblin", 2, 2);
		
		System.out.println(goblin);
		
		new IncreaseDefenseModifierNew(game, goblin);
		DoubleAttackModifierNew dam = new DoubleAttackModifierNew(game, goblin);
		try(dam) {
			System.out.println(goblin);
		}
		System.out.println(goblin);
	}
}
