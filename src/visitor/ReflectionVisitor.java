package visitor;

class ExpressionPrinter {
	public static void print(Expression e, StringBuilder sb) {
		if(e.getClass() == DoubleExpression.class) {
			sb.append(((DoubleExpression)e).value);
		} else if(e.getClass() == AdditionExpression.class) {
			AdditionExpression ae = (AdditionExpression)e;
			sb.append("(");
			ae.left.print(sb);
			sb.append("+");
			ae.right.print(sb);
			sb.append(")");
		}
	}
}

public class ReflectionVisitor {

	public static void main(String[] args) {
		Expression expression =  new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)
				)
		);
		StringBuilder sb = new StringBuilder();
		ExpressionPrinter.print(expression, sb);
		System.out.println(sb.toString());
	}

}
