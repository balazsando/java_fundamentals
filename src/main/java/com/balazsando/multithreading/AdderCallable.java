package com.balazsando.multithreading;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class AdderCallable implements Callable<Integer> {
    private String inFile;

    public AdderCallable(String inFile) {
        this.inFile = inFile;
    }

    public int add() throws IOException {
        int result = 0;
        String line = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inFile))) {
            while ((line = br.readLine()) != null) {
                result += Integer.parseInt(line);
            }
        }

        return result;
    }

    @Override
    public Integer call() throws Exception {
        return add();
    }

    public static void main(String[] args) throws InterruptedException {
        String[] inFiles = {"./file1.txt", "./file2.txt", "./file3.txt", "./file4.txt", "./file5.txt", "./file6.txt"};
        doExecutorService(inFiles);
    }

    private static void doExecutorService(String[] inFiles) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer>[] results = new Future[inFiles.length];

        for (int i = 0; i < inFiles.length; i++) {
            AdderCallable adder = new AdderCallable(inFiles[i]);
            results[i] = executorService.submit(adder);
        }

        for (Future<Integer> future : results) {
            try {
                int value = future.get();
                System.out.println(value);
            } catch (ExecutionException e) {
                Throwable adderException = e.getCause();
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        executorService.shutdown();
    }
}
