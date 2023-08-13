package chain_of_responsibility;

class Creature {
	public String name;
	public  int attack, defense;
	
	public Creature(String name, int attack, int defense) {
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public String toString() {
		return "Creature [name=" + name + ", attack=" + attack + ", defense=" + defense + "]";
	}
}

class CreatureModifier {
	protected Creature creature;
	protected CreatureModifier next;
	
	public CreatureModifier(Creature creature) {
		this.creature = creature;
	}
	
	public void add(CreatureModifier creatureModifier) {
		if(next != null) {
			next.add(creatureModifier);
		} else {
			next = creatureModifier;
		}
	}
	
	public void handle() {
		if(next != null) {
			next.handle();
		}
	}	
}

class DoubleAttackModifier extends CreatureModifier {
	public DoubleAttackModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("Doubling %s's attack".formatted(creature.name));
		creature.attack *= 2;
		super.handle();
	}
}

class IncreaseDefenseModifier extends CreatureModifier {
	public IncreaseDefenseModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("Increasing %s's defense".formatted(creature.name));
		creature.defense *= 2;
		super.handle();
	}
}

class NoBonusModifier extends CreatureModifier {
	public NoBonusModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("No bonuses for you!!");
	}
}

public class MethodChain {
	public static void main(String args[]) {
		Creature goblin = new Creature("Goblin", 2, 3);
		CreatureModifier root = new CreatureModifier(goblin);
		root.add(new NoBonusModifier(goblin));
		root.add(new DoubleAttackModifier(goblin));
		root.add(new IncreaseDefenseModifier(goblin));
		
		root.handle();
		
		System.out.println(goblin);
	}
}
