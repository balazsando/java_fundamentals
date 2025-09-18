package com.balazsando.collections;

import java.util.Comparator;
import java.util.TreeSet;

public class SortingDemo {
    public static void main(String[] args) {
        comparableDemo();
        comparatorDemo();
    }

    private static void comparatorDemo() {
        System.out.println("--- Comparator demo ---");
        TreeSet<MyClass2> treeSet = new TreeSet<>(new MyComparator());
        treeSet.add(new MyClass2("2222", "ghi"));
        treeSet.add(new MyClass2("3333", "abc"));
        treeSet.add(new MyClass2("1111", "def"));
        treeSet.forEach(System.out::println);
    }

    private static void comparableDemo() {
        System.out.println("--- Comparable demo ---");
        TreeSet<MyClass2> treeSet = new TreeSet<>();
        treeSet.add(new MyClass2("2222", "ghi"));
        treeSet.add(new MyClass2("3333", "abc"));
        treeSet.add(new MyClass2("1111", "def"));
        treeSet.forEach(System.out::println);
    }
}

class MyClass2 implements Comparable<MyClass2> {
    String label, value;
    MyClass2(String label, String value) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value.equals(((MyClass2) o).value);
    }

    @Override
    public int compareTo(MyClass2 o) {
        return value.compareToIgnoreCase(o.value);
    }
}

class MyComparator implements Comparator<MyClass2> {
    @Override
    public int compare(MyClass2 o1, MyClass2 o2) {
        return o1.label.compareToIgnoreCase(o2.label);
    }
}
