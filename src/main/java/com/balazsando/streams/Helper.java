package com.balazsando.streams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Helper {
    public static Reader openReader(String file) {
        return null;
    }

    public static Writer openWriter(String file) {
        return null;
    }

    void doChain(InputStream in) throws IOException {
        int length;
        char[] charBuff = new char[128];
        try (InputStreamReader rdr = new InputStreamReader(in)) {
            while((length = rdr.read(charBuff)) >= 0) {
                // do something with charBuff
            }
        }
    }

    void doBuffered() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("file1.txt"))) {
            int intVal;
            while ((intVal = br.read()) >= 0) {
                char c = (char) intVal;
                // do something with c
            }
        }
    }

    void writeData(String[] data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("file2.txt"))) {
            for (String s : data) {
                bw.write(s);
                bw.newLine();
            }
        }
    }

    void readData() throws IOException {
        try (BufferedReader bw = new BufferedReader(new FileReader("file1.txt"))) {
            String line;
            while ((line = bw.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    void readDataWithFiles() throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get("file1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    void readThemALl() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("file1.txt"));

        for (String line : lines) {
            System.out.println(line);
        }
    }

}
