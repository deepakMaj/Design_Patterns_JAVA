package decorator;

import java.util.function.Supplier;

class StaticColoredShape<T extends Shape> implements Shape {
	private Shape shape;
	private String color;
	
	public StaticColoredShape(Supplier<? extends T> ctor, String color) {
		shape = ctor.get();
		this.color = color;
	}
 	
	@Override
	public String info() {
		return "%s has the color %s".formatted(shape.info(), color);
	}
}
public class StaticDecoratorDemo {
	public static void main(String[] args) {
		StaticColoredShape<Square> blueSquare = new StaticColoredShape<>(() -> new Square((float) 20.0), "blue");
		System.out.println(blueSquare.info());
	}
}
