package com.balazsando.stringformat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {
    public static void main(String[] args) {
        stringMethodDemo();
    }

    private static void stringMethodDemo() {
        String s1 = "apple, apple and orange please";
        System.out.println(s1.replaceAll("ple", "ricot"));
        System.out.println(s1.replaceAll("ple\\b", "ricot"));
        String[] parts = s1.split("\\b");
        for (String part : parts) {
            if (part.matches("\\w+")) {
                System.out.println(part);
            }
        }
    }

    private static void dedicatedClassDemo() {
        String s1 = "apple, apple and orange please";
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(s1);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
