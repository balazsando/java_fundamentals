package com.balazsando.collections;
import java.util.*;

public class ArrayVsListBenchmark {
    public static void main(String[] args) {
        final int N = 10_000_000;

        // --- Array ---
        int[] arr = new int[N];
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) arr[i] = i;
        long end = System.currentTimeMillis();
        System.out.println("Array write: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < N; i++) sum += arr[i];
        end = System.currentTimeMillis();
        System.out.println("Array read: " + (end - start) + " ms");

        // --- ArrayList ---
        List<Integer> arrayList = new ArrayList<>(N);  // pre-size to avoid resizing
        start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) arrayList.add(i);
        end = System.currentTimeMillis();
        System.out.println("ArrayList write: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        sum = 0;
        for (int i = 0; i < N; i++) sum += arrayList.get(i);  // autoboxing overhead
        end = System.currentTimeMillis();
        System.out.println("ArrayList read: " + (end - start) + " ms");

        // --- LinkedList ---
        List<Integer> linkedList = new LinkedList<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) linkedList.add(i);
        end = System.currentTimeMillis();
        System.out.println("LinkedList write: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        sum = 0;
        for (int i = 0; i < N; i++) sum += linkedList.get(i); // very slow! O(n^2)
        end = System.currentTimeMillis();
        System.out.println("LinkedList read: " + (end - start) + " ms");
    }
}

