package ru.oleg.configurator.domain.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AutoLoginConfigurator {

    private static final String CONFIG_FILE_PATH = "/etc/gdm3/custom.conf";

    public static void changeAutoLogin(String username, boolean value) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE_PATH));
            StringBuilder newContent = new StringBuilder();
            boolean daemonSectionFound = false;
            boolean autoLoginEnabled = false;

            for (String line : lines) {
                if (line.trim().equals("[daemon]")) {
                    daemonSectionFound = true;
                    newContent.append(line).append("\n");
                    continue;
                }
                if (daemonSectionFound && line.trim().startsWith("AutomaticLoginEnable")) {
                    newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                    autoLoginEnabled = true;
                } else if (daemonSectionFound && line.trim().startsWith("AutomaticLogin")) {
                    newContent.append("AutomaticLogin = ").append(username).append("\n");
                } else {
                    newContent.append(line).append("\n");
                }
            }
            if (!daemonSectionFound) {
                newContent.append("[daemon]").append("\n");
                newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                newContent.append("AutomaticLogin = ").append(username).append("\n");
            } else if (!autoLoginEnabled) {
                newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                newContent.append("AutomaticLogin = ").append(username).append("\n");
            }

            // Запись нового содержимого в файл с правами суперпользователя
            ProcessBuilder pb = new ProcessBuilder("sudo", "bash", "-c", "echo \"" + newContent.toString().replace("\"", "\\\"") + "\" > " + CONFIG_FILE_PATH);
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
