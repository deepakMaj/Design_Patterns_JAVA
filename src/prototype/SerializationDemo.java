package prototype;

import java.io.Serializable;
import org.apache.commons.lang3.*;

class Foo implements Serializable {
	private static final long serialVersionUID = 1L;
	public int stuff;
	public String whatever;

	public Foo(int stuff, String whatever) {
		super();
		this.stuff = stuff;
		this.whatever = whatever;
	}
	@Override
	public String toString() {
		return "Foo [stuff=" + stuff + ", whatever=" + whatever + "]";
	}
}

public class SerializationDemo {
	public static void main(String[] args) {
		Foo foo = new Foo(42, "life");
		Foo fooCopy = SerializationUtils.roundtrip(foo);
		fooCopy.stuff = 10;
		fooCopy.whatever = "Alive";
		
		System.out.println(foo.toString());
		System.out.println(fooCopy.toString());
	}
}
