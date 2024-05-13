package ru.oleg.configurator.domain.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AutoLoginConfigurator {

    private static final String CONFIG_FILE_PATH = "/etc/gdm3/custom.conf";

    public static void changeAutoLogin(String username, boolean value) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE_PATH));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH))) {
                boolean daemonSectionFound = false;
                boolean autoLoginEnabled = false;
                for (String line : lines) {
                    if (line.trim().equals("[daemon]")) {
                        daemonSectionFound = true;
                        writer.write(line);
                        writer.newLine();
                        continue;
                    }
                    if (daemonSectionFound && line.trim().startsWith("AutomaticLoginEnable")) {
                        writer.write("AutomaticLoginEnable = " + value);
                        autoLoginEnabled = true;
                    } else if (daemonSectionFound && line.trim().startsWith("AutomaticLogin")) {
                        writer.write("AutomaticLogin = " + username);
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
                if (!daemonSectionFound) {
                    writer.write("[daemon]");
                    writer.newLine();
                    writer.write("AutomaticLoginEnable = " + value);
                    writer.newLine();
                    writer.write("AutomaticLogin = " + username);
                } else if (!autoLoginEnabled) {
                    writer.write("AutomaticLoginEnable = " + value);
                    writer.newLine();
                    writer.write("AutomaticLogin = " + username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

