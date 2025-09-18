package com.balazsando.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLinesDemo {
    public static void main(String[] args) {
        if(args.length < 1) {
            showUsage();
            return;
        }

        String fileName = args[0];
        if (!Files.exists(Paths.get(fileName))) {
            System.out.println("File not found: " + fileName);
            return;
        }

        showFileLines(fileName);
    }

    private static void showFileLines(String fileName) {
        System.out.println("---- File lines demo ----");

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void showUsage() {
        System.out.println();
        System.out.println("Please provide a file name as a command line argument");
    }
}
