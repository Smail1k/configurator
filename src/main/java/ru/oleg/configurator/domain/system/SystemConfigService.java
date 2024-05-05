package ru.oleg.configurator.domain.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.system.dto.SystemConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class SystemConfigService {

    public SystemConfig getInformation() {
        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setDeviceName(makeDeviceName());
        systemConfig.setRamInfo(makeRamMemoryInfo());
        systemConfig.setCpuInfo(makeCpuInfo());
        systemConfig.setGraphicsInfo(makeGraphicsInfo());
        systemConfig.setRomInfo(makeRomMemoryInfo());
        systemConfig.setOsName(makeOsName());
        systemConfig.setOsType(makeOsType());
        systemConfig.setEnvironmentVersion(makeEnvironmentVersion());
        systemConfig.setWindowInterface(makeWindowInterface());
        systemConfig.setVirtualization(makeVirtualization());

        return systemConfig;
    }

    private static String executeCommand(String command) throws IOException, InterruptedException {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            process.waitFor();
            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;  // Возврат строки об ошибке или значения по умолчанию
        }
    }

    public static String extractData(String totalDataInfo, String pattern) {
        String data = null;

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(totalDataInfo);

        if (m.find()) {
            data = m.group(1);
        }

        return data;
    }

    private String makeDeviceName() {
        String deviceName;
        try {
            deviceName = executeCommand("hostname");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return extractData(deviceName, "DeviceName:\\s+(.+)");
    }

    private String makeRamMemoryInfo() {
        String memoryInfo;
        try {
            memoryInfo = executeCommand("free -h");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return extractData(memoryInfo, "total\\s+([\\d,]+Gi)");
    }

    private String makeCpuInfo() {
        String cpuInfo;
        try {
            cpuInfo = executeCommand("lscpu");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        String countCore = extractData(cpuInfo, "CPU(S):\\s(.+)");
        String cpuModel = extractData(cpuInfo, "Имя модели:\\s+(.+)");
        return String.format("%s × %s", countCore, cpuModel);
    }

    private String makeGraphicsInfo() {
        String graphicsInfo;
        try {
            graphicsInfo = executeCommand("lshw -c video");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return extractData(graphicsInfo, "");
    }

    private String makeRomMemoryInfo() {
        String romMemoryInfo;
        try {
            romMemoryInfo = executeCommand("df -h --total");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return extractData(romMemoryInfo, "total\\s+([\\d,]+G)");
    }

    private String makeOsName() {
        String osName;
        try {
            osName = executeCommand("lsb_release -a");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return extractData(osName, "Description:\\s+(.+)");
    }

    private String makeOsType() {
        String osType;
        try {
            osType = executeCommand("getconf LONG_BIT");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return osType;
    }

    private String makeEnvironmentVersion() {
        String environmentVersion = null;

        try {
            environmentVersion = executeCommand("gnome-shell --version");
        } catch (IOException | InterruptedException e) {
            // pass
        }
        if (environmentVersion != null) {
            return extractData(environmentVersion, "SHELL\\s+(.+)");
        }

        try {
            environmentVersion = executeCommand("budgie-desktop --version");
        } catch (IOException | InterruptedException e) {
            // pass
        }
        if (environmentVersion != null) {
            return extractData(environmentVersion, "budgie-desktop\\s+(.+)");
        }

        try {
            environmentVersion = executeCommand("kf5-config --version");
        } catch (IOException | InterruptedException e) {
            // pass
        }
        if (environmentVersion != null) {
            return extractData(environmentVersion, "KDE Frameworks:\\s+(.+)");
        }

        try {
            environmentVersion = executeCommand("dde-desktop --version");
        } catch (IOException | InterruptedException e) {
            // pass
        }
        if (environmentVersion != null) {
            return extractData(environmentVersion, "dde-desktop\\s+(.+)");
        }

        return null;
    }

    private String makeWindowInterface() {
        String windowInterface;

        try {
            windowInterface = executeCommand("echo $XDG_SESSION_TYPE");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return windowInterface;
    }

    private String makeVirtualization() {
        String virtualization;

        try {
            virtualization = executeCommand("systemd-detect-virt");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return extractData(virtualization, "Virtualization:\\s+(.+)");
    }
}

