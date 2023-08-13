package adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Point {
	public int x, y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
	
}

class Line {
	public Point start, end;

	public Line(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		return Objects.equals(end, other.end) && Objects.equals(start, other.start);
	}
	
}

class VectorObject extends ArrayList<Line> {
	private static final long serialVersionUID = 1L;
	
}

class VectorRectangle extends VectorObject {
	private static final long serialVersionUID = 1L;

	public VectorRectangle(int x, int y, int width, int height) {
		add(new Line(new Point(x, y), new Point(x + width, y)));
		add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
		add(new Line(new Point(x, y), new Point(x, y + height)));
		add(new Line(new Point(x, y + height), new Point(x + width, y + height)));
	}
}

class LineToPointAdapter implements Iterable<Point> {
	private static int count = 0;
	private static Map<Integer, List<Point>> cache  = new HashMap<>();
	private int hash;

	public LineToPointAdapter(Line line) {
		System.out.println("%d: Generating points for line [%d, %d] - [%d, %d] (with caching)"
				.formatted(++count, line.start.x, line.start.y, line.end.x, line.end.y));

		hash = line.hashCode();
		if(cache.get(hash) != null) return;
		
		ArrayList<Point> points = new ArrayList<>();
		int left = Math.min(line.start.x, line.end.x);
		int right = Math.max(line.start.x, line.end.x);
		int top = Math.min(line.start.y, line.end.y);
		int bottom = Math.max(line.start.y, line.end.y);
		int dx = right - left;
		int dy = line.end.y - line.start.y;
		
		if(dx == 0) {
			for(int y = top; y <= bottom; ++y) {
				points.add(new Point(left, y));
			}
		}
		else if(dy == 0) {
			for(int x = left; x <= right; ++x) {
				points.add(new Point(x, top));
			}
		}
		
		cache.put(hash, points);
	}

	@Override
	public Iterator<Point> iterator() {
		return cache.get(hash).iterator();
	}
}

public class VectorAdapterDemo {
	private static final List<VectorObject> vectorObjects =  new ArrayList<>(
			Arrays.asList(
					new VectorRectangle(1, 1, 10, 10),
					new VectorRectangle(3, 3, 6, 6)
			)
	);

	public static void main(String[] args) {
		draw();
		draw();
	}
	
	public static void drawPoint(Point point) {
		System.out.println(".");
	}
	
	private static void draw() {
		for(VectorObject vectorObject : vectorObjects) {
			for(Line line : vectorObject) {
				LineToPointAdapter adapter  = new LineToPointAdapter(line);
				adapter.forEach(VectorAdapterDemo::drawPoint);
			}
		}
	}
}
