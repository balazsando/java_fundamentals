package com.balazsando.execution;

public class CmdLineDemo {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("No arguments provided");
        } else {
            for(String word: args) {
                System.out.println(word);
            }
        }
    }
}
