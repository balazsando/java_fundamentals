package com.balazsando.collections;

import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        List<String> list2 = new LinkedList<>();
        list2.add("one");
        list2.add("two");

        list.addAll(list2);
        System.out.println(list.toString());

        List<MyClass> myClassList = new ArrayList<>();
        MyClass v1 = new MyClass("v1", "abc");
        MyClass v2 = new MyClass("v2", "xyz");
        MyClass v3 = new MyClass("v3", "abc");
        myClassList.add(v1);
        myClassList.add(v2);
        myClassList.add(v3);
//        myClassList.forEach(m -> System.out.println(m.label));

        myClassList.removeIf(m -> m.value.equals("abc"));
        myClassList.forEach(m -> System.out.println(m.label));

        MyClass[] arr = myClassList.toArray(new MyClass[0]);
        MyClass[] arr2 = new MyClass[3];
        MyClass[] arr3 = myClassList.toArray(arr2);

    }
}

class MyClass {
    String label, value;

    MyClass(String label, String value) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return value.equals(((MyClass) obj).value);
    }
}
