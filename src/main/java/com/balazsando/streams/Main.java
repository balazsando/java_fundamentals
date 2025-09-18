package com.balazsando.streams;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        doTryCatchFinally();
//        doTrywithResources();
//        doTrywithResourcesMulti();
        doCloseThing();
    }

    public static void doTryCatchFinally() {
        char[] buff = new char[8];
        int length;
        Reader reader = null;
        try {
            reader = Helper.openReader("file.txt");
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                for (int i = 0; i < length; i++) {
                    System.out.print(buff[i]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        }
    }

    public static void doTrywithResources() {
        char[] buff = new char[8];
        int length;
        try (
                Reader reader = Helper.openReader("file1.txt");
                Writer writer = Helper.openWriter("file2.txt")
        ) {
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                writer.write(buff, 0, length);
            }
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    public static void doTrywithResourcesMulti() {
        char[] buff = new char[8];
        int length;
        try (Reader reader = Helper.openReader("file.txt")) {
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                for (int i = 0; i < length; i++) {
                    System.out.print(buff[i]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static void doCloseThing() {
        try (MyAutoCloseable ac = new MyAutoCloseable()) {
            ac.saySomething();
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());

            for(Throwable th : ex.getSuppressed()) {
                System.out.println("Suppressed: " + th.getMessage());
            }
        }
    }
}