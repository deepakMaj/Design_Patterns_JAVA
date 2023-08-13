package factory;

public class PersonFactory {

    private static int sequence = 0;

    private PersonFactory() {
    }

    public static Person createPerson(String name) {
        return new Person(sequence++, name);
    }
}