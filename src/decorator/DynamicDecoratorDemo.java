package decorator;

interface Shape {
	String info();
}

class Circle implements Shape {
	
	private float radius;
	
	public Circle() {}

	public Circle(float radius) {
		super();
		this.radius = radius;
	}

	public void resize(int factor) {
		radius *= factor;
	}

	@Override
	public String info() {
		return "A circle of radius %f".formatted(radius);
	}
}

class Square implements Shape {
	private float side;
	
	public Square() {}
	
	public Square(float side) {
		this.side = side;
	}

	@Override
	public String info() {
		return "A square of side %f".formatted(side);
	}
}

class ColoredShape implements Shape {
	private Shape shape;
	private String color;

	public ColoredShape(Shape shape, String color) {
		super();
		this.shape = shape;
		this.color = color;
	}
	
	@Override
	public String info() {
		return "%s has the color %s".formatted(shape.info(), color);
	}
}

class TransparentShape implements Shape {
	private Shape shape;
	private int transparency;

	public TransparentShape(Shape shape, int transparency) {
		super();
		this.shape = shape;
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return "%s has %d transparency".formatted(shape.info(),  transparency);
	}
}

public class DynamicDecoratorDemo {
	public static void main(String[] args) {
		Circle circle = new Circle(10);
		System.out.println(circle.info());
		
		ColoredShape blueSquare = new ColoredShape(new Square(20), "blue");
		System.out.println(blueSquare.info());
		
		TransparentShape transparentGreenCircle = new TransparentShape(new ColoredShape(new Circle(5), "green"), 50);
		System.out.println(transparentGreenCircle.info());
	}
}
