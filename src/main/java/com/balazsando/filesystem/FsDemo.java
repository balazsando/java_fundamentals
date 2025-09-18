package com.balazsando.filesystem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FsDemo {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String[] data = {
                "Line 1",
                "Line 2 2",
                "Line 3 3 3",
                "Line 4 4 4 4",
                "Line 5 5 5 5 5"
        };

        try (FileSystem zipFs = openZip(Paths.get("mydata.zip"))){
            copyToZip(zipFs);
            writeToFileInZip(zipFs, data);
            writeToFileInZip2(zipFs, data);
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static FileSystem openZip(Path p) throws IOException, URISyntaxException {
        Map<String, String> props = new HashMap<>();
        props.put("create", "true");

        URI zipUri = new URI("jar:file", p.toUri().getPath(), null);
        FileSystem zipFs = FileSystems.newFileSystem(zipUri, props);

        return  zipFs;
    }

    private static void copyToZip(FileSystem zipFs) throws  IOException {
        Path sourceFile = Paths.get("file1.txt");
//        Path sourceFile = FileSystems.getDefault().getPath("file1.txt");
        Path destFile = zipFs.getPath("/file1copy.txt");

        Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void writeToFileInZip(FileSystem zipFs, String[] data) {
        try(BufferedWriter writer = Files.newBufferedWriter(zipFs.getPath("/newFile1.txt"))) {
            for (String s : data) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static void writeToFileInZip2(FileSystem zipFs, String[] data) throws IOException {
        Files.write(zipFs.getPath("/newFile2.txt"), Arrays.asList(data), Charset.defaultCharset(), StandardOpenOption.CREATE);
    }
}
