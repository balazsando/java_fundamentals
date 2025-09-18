package com.balazsando.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapDemo {
    public static void main(String[] args) {
        hashMapDemo();
        sortedMapDemo();
    }

    private static void sortedMapDemo() {
        System.out.println("---- SortedMap demo ----");
        SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        sortedMap.put("2222", "ghi");
        sortedMap.put("3333", "abc");
        sortedMap.put("1111", "def");
        sortedMap.put("4444", "lmn");
        sortedMap.put("5555", "xyz");
        sortedMap.put("6666", "jkl");

        sortedMap.forEach((k, v) -> System.out.println(k + " | " + v));

        SortedMap<String, String> headMap = sortedMap.headMap("4444");
        headMap.forEach((k, v) -> System.out.println(k + " | " + v));

        SortedMap<String, String> tailMap = sortedMap.tailMap("4444");
        tailMap.forEach((k, v) -> System.out.println(k + " | " + v));
    }

    private static void hashMapDemo() {
        System.out.println("---- HashMap demo ----");
        Map<String, String> map = new HashMap<>();
        map.put("2222", "ghi");
        map.put("3333", "abc");
        map.put("1111", "def");
        System.out.println(map.get("2222"));
        System.out.println(map.get("9999"));
        System.out.println(map.getOrDefault("9999", "xyz"));

        map.forEach((k, v) -> System.out.println(k + " | " + v));
        map.replaceAll((k, v) -> v.toUpperCase());
        map.forEach((k, v) -> System.out.println(k + " | " + v));
    }
}
