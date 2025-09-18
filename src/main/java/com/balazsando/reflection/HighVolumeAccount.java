package com.balazsando.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class HighVolumeAccount extends BankAccount implements Runnable {
    public HighVolumeAccount(String id) {
        super(id);
    }

    public HighVolumeAccount(String id, int balance) {
        super(id, balance);
    }

    private int[] readDailyDeposits() {
        int[] dailyDeposits = new int[5];
        return dailyDeposits;
    }

    private int[] readDailyWithdrawals() {
        int[] dailyWithdrawals = new int[5];
        return dailyWithdrawals;
    }

    @Override
    public void run() {
        for (int deposits : readDailyDeposits()) {
            deposit(deposits);
        }

        for (int withdrawals : readDailyWithdrawals()) {
            withdraw(withdrawals);
        }
    }

    public static void main(String[] args) {
        HighVolumeAccount account = new HighVolumeAccount("1234", 500);
        doClassInfo(account);
        callGetId(account);

    }

    private static void doClassInfo(Object o) {
        Class<?> c = o.getClass();
        System.out.println(c.getSimpleName());
        Class<?> superclass = c.getSuperclass();
        System.out.println(superclass.getSimpleName());
        Class<?>[] interfaces = c.getInterfaces();
        for (Class<?> i : interfaces) {
            System.out.println(i.getSimpleName());
        }
        int modifiers = c.getModifiers();
        System.out.println("-----------------CLASS INFO----------------------");
        System.out.println("public: " + Modifier.isPublic(modifiers));
        System.out.println("rivate: " + Modifier.isPrivate(modifiers));
        System.out.println("final: " + Modifier.isFinal(modifiers));
        doFieldInfo(c);
//        doMethodInfo(c);
        doMethod2Info(c);
    }

    private static void doFieldInfo(Class<?> c) {
        System.out.println("------------------FIELD INFO--------------------");
        Field[] fields = c.getFields();
        Arrays.stream(fields).forEach(System.out::println);
        Field[] declaredFields = c.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(System.out::println);
    }

    private static void doMethodInfo(Class<?> c) {
        System.out.println("------------------METHOD INFO-------------------");
        Method[] methods = c.getMethods();
        Arrays.stream(methods).forEach(System.out::println);
        Method[] declaredMethods = c.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(System.out::println);
    }

    private static void doMethod2Info(Class<?> c) {
        System.out.println("------------------METHOD INFO-------------------");
        Method[] methods = c.getMethods();
        Arrays.stream(methods).filter(method -> method.getDeclaringClass() != Object.class).forEach(System.out::println);
        Method[] declaredMethods = c.getDeclaredMethods();
        Arrays.stream(declaredMethods).filter(method -> method.getDeclaringClass() != Object.class).forEach(System.out::println);
    }

    private static void callGetId(Object o) {
        Class<?> c = o.getClass();
        try {
            Method m = c.getMethod("getId");
            Object result = m.invoke(o);
            System.out.println(result);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void callDeposit(Object o, int amount) {
        Class<?> c = o.getClass();
        try {
            Method m = c.getMethod("deposit", int.class);
            m.invoke(o, amount);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
