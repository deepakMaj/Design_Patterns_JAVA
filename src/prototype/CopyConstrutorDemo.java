package prototype;

class HomeAddress {
	public String steetAddress, city, country;

	public HomeAddress(String steetAddress, String city, String country) {
		super();
		this.steetAddress = steetAddress;
		this.city = city;
		this.country = country;
	}
	
	public HomeAddress(HomeAddress address) {
		this(address.steetAddress, address.city, address.country);
	}

	@Override
	public String toString() {
		return "HomeAddress [steetAddress=" + steetAddress + ", city=" + city + ", country=" + country + "]";
	}
}

class Employee {
	public String name;
	public HomeAddress address;

	public Employee(String name, HomeAddress address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public Employee(Employee employee) {
		name = employee.name;
		address = new HomeAddress(employee.address);
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", address=" + address + "]";
	}
}

public class CopyConstrutorDemo {
	public static void main(String[] args) {
		Employee john = new Employee("John Smith", new HomeAddress("123 London Road", "London", "UK"));
		Employee chris = new Employee(john);
		chris.name = "Chris Watt";
		
		System.out.println(john.toString());
		System.out.println(chris.toString());
	}
}
