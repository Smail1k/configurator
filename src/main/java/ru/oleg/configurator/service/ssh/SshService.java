package ru.oleg.configurator.service.ssh;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.service.ssh.dto.SshConfig;
import ru.oleg.configurator.service.utils.dto.UpdateParameterIn;
import ru.oleg.configurator.service.utils.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SshService {
    private final Parser parsers;

    public SshConfig getConfigSSH() {
        String filePath = "/etc/ssh/sshd_config"; // Путь к файлу конфигурации SSH
        SshConfig sshConfig = new SshConfig();
        return parsers.parseFileConfig(sshConfig, filePath);
    }

    public void updateConfigSSH(UpdateParameterIn updateParameterIn) {
        String filePath = "/etc/ssh/sshd_config"; // Путь к файлу конфигурации SSH
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String> updatedLines = lines.stream()
                    .map(line -> parsers.updateParameter(line, updateParameterIn.getParameterName(),
                            updateParameterIn.getParameterValue()))
                    .collect(Collectors.toList());
            Files.write(Paths.get(filePath), updatedLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            // pass
        }
    }
}
