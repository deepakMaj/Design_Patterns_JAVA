package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Token {
	public enum Type {
		INTEGER,
		PLUS,
		MINUS,
		LPAREN,
		RPAREN
	}
	public Type type;
	public String  text;
	
	public Token(Type type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	@Override
	public String toString() {
		return "`%s`".formatted(text);
	}
}

interface Element {
	int eval();
}

class Integer implements Element {
	private int value;
	
	public Integer(int value) {
		super();
		this.value = value;
	}

	@Override
	public int eval() {
		return value;
	}
}

class BinaryOperation implements Element {
	public enum Type {
		ADDITION,
		SUBTRACTION
	}
	Type type;
	Element left, right;

	@Override
	public int eval() {
			switch(type) {
				case ADDITION:
					return left.eval() + right.eval();
				case SUBTRACTION:
					return left.eval() - right.eval();
				default:
					return 0;
			}
	}
}

class Lexing {
	static List<Token> lex(String input) {
		List<Token> tokens = new ArrayList<>();
		
		for(int i = 0; i < input.length(); i++) {
			switch(input.charAt(i)) {
				case '+':
					tokens.add(new Token(Token.Type.PLUS, "+"));
					break;
				case '-':
					tokens.add(new Token(Token.Type.MINUS, "-"));
					break;
				case '(':
					tokens.add(new Token(Token.Type.LPAREN, "("));
					break;
				case ')':
					tokens.add(new Token(Token.Type.RPAREN, ")"));
					break;
				default:
					StringBuilder sb = new StringBuilder("" + input.charAt(i));
					for(int j = i + 1;  j < input.length(); j++) {
						if(Character.isDigit(input.charAt(j))) {
							sb.append(input.charAt(j));
							++i;
						} else {
							tokens.add(new Token(Token.Type.INTEGER, sb.toString()));
							break;
						}
					}
					break;
			}
		}
		
		return tokens;
	}
	
	static Element parse(List<Token> tokens) {
		BinaryOperation result =  new BinaryOperation();
		boolean haveLHS = false;
		
		for(int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			
			switch(token.type) {
				case INTEGER:
					Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
					if(!haveLHS) {
						result.left = integer;
						haveLHS = true;
					} else {
						result.right = integer;
					}
					break;
				case PLUS:
					result.type = BinaryOperation.Type.ADDITION;
					break;
				case MINUS:
					result.type = BinaryOperation.Type.SUBTRACTION;
					break;
				case LPAREN:
					int j = i;
					for(; j < tokens.size(); j++) {
						if(tokens.get(j).type.equals(Token.Type.RPAREN)) {
							break;
						}
					}
					List<Token> subexpression = tokens.stream().skip(i + 1).limit(j - i - 1).collect(Collectors.toList());
					Element element = parse(subexpression);
					if(!haveLHS) {
						result.left = element;
						haveLHS = true;
					} else {
						result.right = element;
					}
					i = j++;
					break;
			default:
				break;
			}
		}
		
		return result;
	}
}

public class Interpreter {
	public static void main(String[] args) {
		String input = "(13-4)+(12+1)";
		List<Token> result = Lexing.lex(input);
		Element parsed = Lexing.parse(result);
		System.out.println(parsed.eval());
	}
}
