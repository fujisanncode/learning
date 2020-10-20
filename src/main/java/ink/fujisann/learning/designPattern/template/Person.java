package ink.fujisann.learning.designPattern.template;

public class Person implements Comparable {

    private int age;

    public Person(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        return this.age > ((Person) o).age ? 1 : -1;
    }

    @Override
    public String toString() {
        return "{"
            + "\"age\":"
            + age
            + "}";
    }
}
