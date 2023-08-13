package proxy;

interface Driveable {
	void drive();
}

 class Car implements Driveable {
	 protected Driver driver;
	 
	 public Car(Driver driver) {
		 this.driver = driver;
	 }

	@Override
	public void drive() {
		System.out.println("Car is being driven");
	}
 }
 
 class Driver {
	 public int age;
	 
	 public Driver(int age) {
		 this.age = age;
	 }
 }
 
 class CarProxy extends Car {
	 public CarProxy(Driver driver) {
		 super(driver);
	 }
	 
	 @Override
		public void drive() {
			if(driver.age >= 16) {
				super.drive();
			} else {
				System.out.println("Driver too young!!");
			}
		}
 }

public class ProtectionProxy {
	public static void main(String args[]) {
		Car car = new CarProxy(new Driver(12));
		car.drive();
	}
}
