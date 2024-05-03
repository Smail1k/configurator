package ru.oleg.configurator.domain.ssh;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.ssh.dto.ConfigSSH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@AllArgsConstructor
public class SshService {

    public ConfigSSH getConfigSSH() {
        String filePath = "/etc/ssh/sshd_config"; // Путь к файлу конфигурации SSH
        readFileAndParse(filePath);
        return new ConfigSSH();
    }

    private static void readFileAndParse(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line); // Обработка каждой строки
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static void processLine(String line) {
        // Убираем комментарии и лишние пробелы
        if (line.trim().startsWith("#") || line.trim().isEmpty()) {
            return; // Пропускаем комментарии и пустые строки
        }

        // Выводим содержимое без комментариев
        System.out.println(line.split("#")[0].trim());

        // Здесь можно добавить дополнительный парсинг, например, для поиска определенных параметров:
        if (line.contains("PermitRootLogin")) {
            System.out.println("Найден параметр PermitRootLogin: " + line);
        }
    }

    public ConfigSSH updateConfigSSH(ConfigSSH configSSH) {
        String filePath = "/etc/ssh/sshd_config"; // Путь к файлу конфигурации SSH
        readFileAndParse(filePath);
        return new ConfigSSH();
    }

}
