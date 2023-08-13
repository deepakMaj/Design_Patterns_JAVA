package flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class Username {
	@SuppressWarnings("unused")
	private String fullname;
	
	public Username(String fullname) {
		this.fullname = fullname;
	}
}

class User2 {
	static List<String> strings = new ArrayList<>();
	@SuppressWarnings("unused")
	private int[] names;
	
	public User2(String fullname) {
		Function<String, Integer> getOrAdd = (String s) -> {
			int index = strings.indexOf(s);
			if(index != -1) {
				return index;
			} else {
				strings.add(s);
				return strings.size() - 1;
			}
		};
		
		names = Arrays.stream(fullname.split(" ")).mapToInt(s -> getOrAdd.apply(s)).toArray();
	}
}

public class RPGNames {
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		User2 user = new User2("John Smith");
		User2 user2 = new User2("Jade Smith");
	}
}
