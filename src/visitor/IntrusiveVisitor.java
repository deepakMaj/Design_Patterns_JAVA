package visitor;

abstract class Expression {
	public abstract void print(StringBuilder sb);
}

class DoubleExpression extends Expression {
	public double value;

	public DoubleExpression(double value) {
		this.value = value;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append(value);
	}
}

class AdditionExpression extends Expression {
	public Expression left, right;

	public AdditionExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append("+");
		right.print(sb);
		sb.append(")");
	}
}

public class IntrusiveVisitor {

	public static void main(String[] args) {
		Expression expression =  new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)
				)
		);
		StringBuilder sb = new StringBuilder();
		expression.print(sb);
		System.out.println(sb.toString());
	}

}
