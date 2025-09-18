package com.balazsando.multithreading;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Adder implements Runnable {
    private String inFile, outFile;

    public Adder(String inFile, String outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public void add() throws IOException {
        int result = 0;
        String line = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inFile))) {
            while ((line = br.readLine()) != null) {
                result += Integer.parseInt(line);
            }
        }
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outFile))) {
            bw.write(String.valueOf(result));
        }
    }

    @Override
    public void run() {
        try {
            add();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] inFiles = {"./file1.txt", "./file2.txt", "./file3.txt", "./file4.txt", "./file5.txt", "./file6.txt"};
        String[] outFiles = {"./file1.out.txt", "./file2.out.txt", "./file3.out.txt", "./file4.out.txt", "./file5.out.txt", "./file6.out.txt"};
        doManualThreads(inFiles, outFiles);
        doExecutorService(inFiles, outFiles);
    }

    private static void doExecutorService(String[] inFiles, String[] outFiles) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < inFiles.length; i++) {
            Adder adder = new Adder(inFiles[i], outFiles[i]);
            executorService.submit(adder);
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void doManualThreads(String[] inFiles, String[] outFiles) throws InterruptedException {
        Thread[] threads = new Thread[6];

        for (int i = 0; i < inFiles.length; i++) {
            Adder adder = new Adder(inFiles[i], outFiles[i]);
            threads[i] = new Thread(adder);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
