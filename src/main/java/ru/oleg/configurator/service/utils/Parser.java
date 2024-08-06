package ru.oleg.configurator.service.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Parser {
    public String extractData(String totalDataInfo, String pb) {
        String data = null;

        Pattern pattern = Pattern.compile(pb);
        Matcher m = pattern.matcher(totalDataInfo);

        if (m.find()) {
            try {
                data = m.group(1);
            } catch (Exception e) {
                data = m.group(0);
            }
        }
        return data;
    }

    public <T> T parseFileConfig(T config, String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
//            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/testConfig"));

            Class<?> objectClass = config.getClass();
            for (String line : lines) {
                if (line.isEmpty()){
                    continue;
                }
                if (line.contains("Example")){
                    break;
                }
                String result = line.startsWith("#") ? extractData(line, "^#([^ ].*)") : line;
                if (result != null) {
                    if (result.contains("\t")) {
                        result = result.replaceFirst("\t", " ");
                    }
                    String[] map = result.split(" ", 2);

                    try {
                        String nameField = map[0].substring(0, 1).toLowerCase() + map[0].substring(1);

                        Field field = objectClass.getDeclaredField(nameField);
                        field.setAccessible(true);

                        if (Pattern.matches("\\d+", map[1])) {
                            field.set(config, Integer.parseInt(map[1]));
                        } else if (Pattern.matches("yes|no", map[1])) {
                            field.set(config, parseToBoolean(map[1]));
                        } else if (map[1].equals("none")) {
                            field.set(config, null);
                        } else {
                            field.set(config, map[1]);
                        }
                    } catch (IllegalAccessException | NoSuchFieldException ignored) {
                        System.out.println("ERROR");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    public static boolean parseToBoolean(String value) {
        return value.equalsIgnoreCase("yes");
    }

    public String updateParameter(String line, String parameterName, String parameterValue) {
        parameterName = parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1);
        // Создаем шаблон для поиска строки с параметром
        String regex = "^[#\\s]*" + parameterName + "\\s*.*";
        if (line.matches(regex)) {
            // Убираем символ "#" если он есть и изменяем значение параметра
            return parameterName + " " + parameterValue;
        }
        return line;
    }
}