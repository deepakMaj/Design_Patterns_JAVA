package builder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBuilder {

    private final int INDENT = 2;

    private final String className;
    private final Map<String, String> fields = new LinkedHashMap<>();

    public CodeBuilder(String className) {
        this.className = className;
    }

    public CodeBuilder addField(String name, String type) {
        fields.put(name, type);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("public class %s\n", className))
        ;
        sb.append("{\n");
        fields.forEach((name, type) -> {
            sb.append(String.join("", Collections.nCopies(INDENT, " ")));
            sb.append(String.format("public %s %s;\n", type, name));
        });
        sb.append("}");
        return sb.toString();
    }
}