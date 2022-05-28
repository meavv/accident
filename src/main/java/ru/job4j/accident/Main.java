package ru.job4j.accident;

class Cat {
    int age;
    String name;

    public void meow() {
        System.out.println("meow");
    }

    public Cat(int age, String name) {
        this.age = age + 1;
        this.name = name;
    }

    public Cat(){

    }

    @Override
    public String toString() {
        return "Cat{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

class Cat1 extends Cat {
    int paw;

    @Override
    public void meow() {
        super.meow();
        System.out.println("meowwwwwww");
    }

    @Override
    public String toString() {
        return "Cat1{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", paw=" + paw +
                '}';
    }
}


public class Main {

    public static void main(String[] args) {
        Cat cat = new Cat1();
        cat.meow();
    }
}
