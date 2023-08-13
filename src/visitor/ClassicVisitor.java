package visitor;

interface ExpressionVisitor {
	void visit(DoubleExpressions e);
	void visit(AdditionExpressions e);
}

abstract class Expressions {
	public abstract  void accept(ExpressionVisitor visitor);
}

class DoubleExpressions extends Expressions {
	public double value;

	public DoubleExpressions(double value) {
		this.value = value;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class AdditionExpressions extends Expressions {
	public Expressions left, right;

	public AdditionExpressions(Expressions left, Expressions right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class ExpressionsPrinter implements ExpressionVisitor {
	public StringBuilder sb = new StringBuilder();

	@Override
	public void visit(DoubleExpressions e) {
		sb.append(e.value);
	}

	@Override
	public void visit(AdditionExpressions e) {
		sb.append("(");
		e.left.accept(this);
		sb.append("+");
		e.right.accept(this);
		sb.append(")");
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

class ExpressionCalculator implements ExpressionVisitor {
	public double result;

	@Override
	public void visit(DoubleExpressions e) {
		result = e.value;
	}

	@Override
	public void visit(AdditionExpressions e) {
		e.left.accept(this);
		double a = result;
		e.right.accept(this);
		double b = result;
		result = a + b;
	}

	@Override
	public String toString() {
		return "ExpressionCalculator [result=" + result + "]";
	}
}
 
public class ClassicVisitor {

	public static void main(String[] args) {
		AdditionExpressions expression =  new AdditionExpressions(
				new DoubleExpressions(1),
				new AdditionExpressions(
						new DoubleExpressions(2),
						new DoubleExpressions(3)
				)
		);
		
		ExpressionsPrinter ep = new ExpressionsPrinter();
		ep.visit(expression);
		System.out.println(ep.sb.toString());
		
		ExpressionCalculator ec = new ExpressionCalculator();
		ec.visit(expression);
		System.out.println(ec);
	}

}
