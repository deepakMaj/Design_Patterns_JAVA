package visitor;

interface Visitor {};

interface AExpressionVisitor extends Visitor {
	void visit(AExpression e);
}

interface DoubleExpressionVisitor extends Visitor {
	void visit(ADoubleExpression e);
}

interface AdditionExpressionVisitor extends Visitor {
	void visit(AAdditionExpression e);
}

abstract class AExpression {
	public  void accept(Visitor visitor) {
		if(visitor instanceof AExpressionVisitor) {
			((AExpressionVisitor)visitor).visit(this);
		}
	}
}

class ADoubleExpression extends AExpression {
	public double value;

	public ADoubleExpression(double value) {
		this.value = value;
	}
	
	@Override
	public  void accept(Visitor visitor) {
		if(visitor instanceof DoubleExpressionVisitor) {
			((DoubleExpressionVisitor)visitor).visit(this);
		}
	}
}

class AAdditionExpression extends AExpression {
	public AExpression left, right;

	public AAdditionExpression(AExpression left, AExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public  void accept(Visitor visitor) {
		if(visitor instanceof AdditionExpressionVisitor) {
			((AdditionExpressionVisitor)visitor).visit(this);
		}
	}
}

class AExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor {
	public StringBuilder sb = new StringBuilder();
	
	@Override
	public void visit(AAdditionExpression e) {
		sb.append("(");
		e.left.accept(this);
		sb.append("+");
		e.right.accept(this);
		sb.append(")");
	}

	@Override
	public void visit(ADoubleExpression e) {
		sb.append(e.value);
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

public class AcyclicVisitor {

	public static void main(String[] args) {
		AAdditionExpression expression =  new AAdditionExpression(
				new ADoubleExpression(1),
				new AAdditionExpression(
						new ADoubleExpression(2),
						new ADoubleExpression(3)
				)
		);
		
		AExpressionPrinter ep = new AExpressionPrinter();
		ep.visit(expression);
		System.out.println(ep.sb.toString());
	}

}
