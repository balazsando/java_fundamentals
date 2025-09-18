package com.balazsando.execution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesFromResource {
    public static void main(String[] args) {
        try {
            Properties defaultProps = new Properties();
            try (InputStream is = PropertiesFromResource.class.getResourceAsStream("/defaultvalues.properties")) {
                defaultProps.load(is);
            }
            Properties userProps = new Properties(defaultProps);
            userProps = loadUserProps(userProps);

            String welcomeMsg = userProps.getProperty("welcomeMessage");
            String farewellMsg = userProps.getProperty("farewellMessage");
            System.out.println(welcomeMsg);
            System.out.println(farewellMsg);

            if (userProps.getProperty("isFirstRun").equalsIgnoreCase("Y")) {
                userProps.setProperty("isFirstRun", "N");
                userProps.setProperty("welcomeMessage", "Welcome back!");
                userProps.setProperty("farewellMessage", "Things will be familiar now!");
                saveUserProps(userProps);
            }
        } catch (IOException e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void saveUserProps(Properties userProps) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get("uservalues.properties"))) {
            userProps.store(os, "User specific properties");
        }
    }

    private static Properties loadUserProps(Properties props) throws IOException {
        Path userFile = Paths.get("uservalues.properties");
        if(Files.exists(userFile)) {
            try (InputStream is = Files.newInputStream(userFile)) {
                props.load(is);
            }
        }

        return props;
    }
}
