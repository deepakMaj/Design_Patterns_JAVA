package strategy;

import java.util.List;
import java.util.function.Supplier;

enum OutputFormat {
	MARKDOWN,
	HTML
};

interface ListStrategy {
	default void start(StringBuilder sb) {};
	void addListItem(StringBuilder sb, String item);
	default void end(StringBuilder sb) {};
}

class MarkdownListStrategy implements ListStrategy {
	@Override
	public void addListItem(StringBuilder sb, String item) {
		sb.append("*").append(item).append(System.lineSeparator());
	}
}

class HtmlListStrategy implements ListStrategy {
	@Override
	public void start(StringBuilder sb) {
		sb.append("<ul>").append(System.lineSeparator());
	}

	@Override
	public void addListItem(StringBuilder sb, String item) {
		sb.append("	<li>").append(item).append("</li>").append(System.lineSeparator());
	}
	
	@Override
	public void end(StringBuilder sb) {
		sb.append("</ul>").append(System.lineSeparator());
	}
}

// DYNAMIC STRATEGY

//class TextProcessor {
//	private StringBuilder sb = new StringBuilder();
//	private ListStrategy listStrategy;
//	
//	public TextProcessor(OutputFormat format) {
//		setOutputFormat(format);
//	}
//	
//	public void setOutputFormat(OutputFormat format) {
//		switch(format) {
//			case MARKDOWN:
//				this.listStrategy = new MarkdownListStrategy();
//				break;
//			case HTML:
//				this.listStrategy = new HtmlListStrategy();
//				break;
//			default:
//				break;
//		}
//	}
//	
//	public void appendListItems(List<String> items) {
//		listStrategy.start(sb);
//		for(String item : items) {
//			listStrategy.addListItem(sb, item);
//		}
//		listStrategy.end(sb);
//	}
//	
//	public void clear() {
//		sb.setLength(0);
//	}
//
//	@Override
//	public String toString() {
//		return sb.toString();
//	}
//}

// STATIC STRATEGY

class TextProcessor<LS extends ListStrategy> {
	private StringBuilder sb = new StringBuilder();
	private LS listStrategy;
	
	public TextProcessor(Supplier<? extends LS> ctor) {
		listStrategy = ctor.get();
	}
	
	public void appendListItems(List<String> items) {
		listStrategy.start(sb);
		for(String item : items) {
			listStrategy.addListItem(sb, item);
		}
		listStrategy.end(sb);
	}
	
	public void clear() {
		sb.setLength(0);
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

public class Strategy {
	public static void main(String args[]) {
//		DYNAMIC STRATEGY EXAMPLE
		
//		TextProcessor processor = new TextProcessor(OutputFormat.MARKDOWN);
//		processor.appendListItems(List.of("librete", "egalite", "fraternite"));
//		System.out.println(processor.toString());
//		
//		processor.clear();
//		
//		processor.setOutputFormat(OutputFormat.HTML);
//		processor.appendListItems(List.of("Inheritance", "Encapsulation", "Polymorphism", "Abstraction"));
//		System.out.println(processor.toString());
		
//		STATIC STATEGY EXAMPLE
		
		TextProcessor<MarkdownListStrategy> mp = new TextProcessor<>(MarkdownListStrategy::new);
		mp.appendListItems(List.of("aplha", "beta", "gamma"));
		System.out.println(mp.toString());
		
		TextProcessor<HtmlListStrategy> hp = new TextProcessor<>(HtmlListStrategy::new);
		hp.appendListItems(List.of("aplha", "beta", "gamma"));
		System.out.println(hp.toString());
	}
}
