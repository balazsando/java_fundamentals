package com.balazsando.execution;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesDemo {
    public static void main(String[] args) {
//        storeProperties();
//        loadProperties();
//        storePropertiesXml();
//        loadPropertiesXml();
        defaultProperties();
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try(Reader reader =Files.newBufferedReader(Paths.get("myapp.properties"))) {
            properties.load(reader);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }

        System.out.println(properties.getProperty("val1"));
        System.out.println(properties.getProperty("val2"));
        System.out.println(properties.getProperty("val3"));
        System.out.println(properties.getProperty("val4"));
    }

    private static void loadPropertiesXml() {
        Properties properties = new Properties();
        try(InputStream reader =Files.newInputStream(Paths.get("xyz.xml"))) {
            properties.loadFromXML(reader);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }

        System.out.println(properties.getProperty("displayName"));
        System.out.println(properties.getProperty("accountNumber"));
        System.out.println(properties.getProperty("position"));
    }

    private static void storeProperties() {
        Properties properties = new Properties();
        properties.put("displayName", "Balazs Ando");
        String name = properties.getProperty("displayName");
        System.out.println(name);
        String acctNum = properties.getProperty("accountNumber");
        System.out.println(acctNum);
        String nextPosition = properties.getProperty("position", "1");
        System.out.println(nextPosition);
        properties.put("accountNumber", "123456");
        properties.put("position", "2");

        try(Writer writer = Files.newBufferedWriter(Paths.get("xyz.properties"))) {
            properties.store(writer, "Sample properties");
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static void storePropertiesXml() {
        Properties properties = new Properties();
        properties.put("displayName", "Balazs Ando");
        properties.put("accountNumber", "123456");
        properties.put("position", "2");

        try(OutputStream writer = Files.newOutputStream(Paths.get("xyz.xml"))) {
            properties.storeToXML(writer, "Sample properties");
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static void defaultProperties() {
        Properties defaults = new Properties();
        defaults.put("displayName", "Balazs Ando");
        defaults.put("accountNumber", "123456");
        defaults.put("position", "2");

        Properties properties = new Properties(defaults);
        System.out.println(properties.getProperty("displayName"));
        int pos = Integer.parseInt(properties.getProperty("position"));
        properties.put("position", Integer.toString(++pos));
        System.out.println(properties.getProperty("position"));
    }
}
