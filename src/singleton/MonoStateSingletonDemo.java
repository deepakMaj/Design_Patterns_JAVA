package singleton;

class ChiefExecutiveOfficer {
	private static String name;
	private static int age;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		ChiefExecutiveOfficer.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		ChiefExecutiveOfficer.age = age;
	}
	@Override
	public String toString() {
		return "CheifExecutiveOfficer [name=" + name + ", age=" + age + "]";
	}
}

public class MonoStateSingletonDemo {
	public static void main(String[] args) {
		ChiefExecutiveOfficer ceo = new ChiefExecutiveOfficer();
		ceo.setName("John Doe");
		ceo.setAge(55);
		
		ChiefExecutiveOfficer ceo2 = new ChiefExecutiveOfficer();
		System.out.println(ceo2.toString());
	}
}
