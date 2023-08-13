package singleton;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Iterables;

interface Database {
	int getPopulation(String name);
}

class SingletonDatabase implements Database {
	private Dictionary<String, Integer> capitals = new Hashtable<>();
	private static int instanceCount = 0;
	
	private SingletonDatabase() {
		instanceCount++;
		System.out.println("Initializing Database");
		
		try {
			File file = new File(SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			Path fullPath = Paths.get(file.getPath(), "captials.txt");
			List<String> lines = Files.readAllLines(fullPath);
			Iterables.partition(lines, 2)
				.forEach(kv -> capitals.put(
						kv.get(0).trim(), Integer.parseInt(kv.get(1)))
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getCount() {
		return instanceCount;
	}
	
	private static final SingletonDatabase instance = new SingletonDatabase();
	
	public static SingletonDatabase getInstance() {
		return instance;
	}
	
	public int getPopulation(String name) {
		return capitals.get(name);
	}
}

class SingletonRecordFinder {
	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for(String name : names) {
			result += SingletonDatabase.getInstance().getPopulation(name);
		}
		return result;
	}
}

class ConfigurableRecordFinder {
	private Database database;
	
	public ConfigurableRecordFinder(Database database) {
		this.database = database;
	}
	
	public int getTotalPopulation(List<String> names) {
		int result = 0;
		for(String name : names) {
			result += database.getPopulation(name);
		}
		return result;
	}
}

class DummyDatabase implements Database {
	
	private Dictionary<String, Integer> dummyData = new Hashtable<>();
	
	public DummyDatabase() {
		dummyData.put("alpha", 1);
		dummyData.put("beta", 2);
		dummyData.put("gamma", 3);
	}

	@Override
	public int getPopulation(String name) {
		return dummyData.get(name);
	}	
}

public class TestSingleton {
//	@Test // Not a unit test!
//	public void singletonTotalPopulationTest() {
//		SingletonRecordFinder recordFinder = new SingletonRecordFinder();
//		List<String> names = List.of("Seoul", "Mexico City");
//		int totalPopulation = recordFinder.getTotalPopulation(names);
//		assertEquals(17500000 + 17400000, totalPopulation);
//	}
	
	@Test // Unit test
	public void dependentPopulationTest() {
		DummyDatabase database = new DummyDatabase();
		ConfigurableRecordFinder recordFinder = new ConfigurableRecordFinder(database);
		assertEquals(4, recordFinder.getTotalPopulation(List.of("alpha", "gamma")));
	}
}
