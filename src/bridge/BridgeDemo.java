package bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

// Shape -> Circle, Rectangle, ...
// Renderer -> Vector, Raster, ...
// Cartesian Product -> VectorCircle, VectorRectangle, RasterCircle, RasterRectangle, ...
// The Bridge pattern helps resolve the complexity problem

interface Renderer {
	void renderCircle(float radius);
}

class VectorRenderer implements Renderer {

	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle of radius %f".formatted(radius));
	}
}

class RasterRenderer implements Renderer {

	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing pixels for a circle of radius %f".formatted(radius));
	}
}

abstract class Shape {
	protected Renderer renderer;
	
	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract void draw();
	public abstract void resizeShape(float factor);
}

class Circle extends Shape {
	public float radius;
	
	@Inject
	public Circle(Renderer renderer) {
		super(renderer);
	}
	
	public Circle(Renderer renderer, float radius) {
		super(renderer);
		this.radius = radius;
	}

	@Override
	public void draw() {
		renderer.renderCircle(radius);
	}

	@Override
	public void resizeShape(float factor) {
		radius *= factor;
	}
}

class ShapeModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Renderer.class).to(VectorRenderer.class);
	}
}

public class BridgeDemo {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new ShapeModule());
		Circle instance = injector.getInstance(Circle.class);
		instance.radius = 5;
		instance.draw();
		instance.resizeShape(2);
		instance.draw();
	}
}
