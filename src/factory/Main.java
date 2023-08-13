package factory;

public class Main {
    public static void main(String[] args) {
        System.out.println(PersonFactory.createPerson("Mario"));
        System.out.println(PersonFactory.createPerson("Luigi"));
    }
}