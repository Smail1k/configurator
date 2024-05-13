package ru.oleg.configurator.domain.ssh;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.ssh.dto.ConfigSSH;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SshService {

    public ConfigSSH getConfigSSH() {
        String filePath = "/etc/ssh/ssh_config"; // Путь к файлу конфигурации SSH
        return new ConfigSSH();
    }

    public ConfigSSH updateConfigSSH(ConfigSSH configSSH) {
        String filePath = "/etc/ssh/ssh_config"; // Путь к файлу конфигурации SSH
        return new ConfigSSH();
    }

    private String parseFileConfig(String filePath) throws IOException {
        try {
            String fileContent = readFileToString(filePath);
            return Objects.requireNonNullElse(fileContent, "No configurations found");
        } catch (NullPointerException e) {
            return "Failed to read the file";
        }
    }

    private String readFileToString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
