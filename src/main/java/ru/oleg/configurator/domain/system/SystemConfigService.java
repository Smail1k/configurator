package ru.oleg.configurator.domain.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.system.dto.SystemConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class SystemConfigService {

    public SystemConfig getInformation() {
        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setDeviceName(makeDeviceName());
        systemConfig.setRamMemory(makeRamMemoryInfo());
        systemConfig.setCpu(makeCpuInfo());
        systemConfig.setGraphics(makeGraphicsInfo());
        systemConfig.setCapacityDisk(makeRomMemoryInfo());
        systemConfig.setOsName(makeOsName());
        systemConfig.setOsType(makeOsType());
        systemConfig.setEnvironmentVersion(makeEnvironmentVersion());
        systemConfig.setInterfaceWindow(makeWindowInterface());
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
        return deviceName;
    }

    private String makeRamMemoryInfo() {
        String memoryInfo;
        try {
            memoryInfo = executeCommand("free -h");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return extractData(memoryInfo, "Память:\\s+([\\d,]+)\\s*Gi");
    }

    private String makeCpuInfo() {
        String cpuInfo;
        try {
            cpuInfo = executeCommand("lscpu");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        String countCore = extractData(cpuInfo, "CPU\\(s\\):\\s+(\\d+)");
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

        return extractData(graphicsInfo, "описание:\\s+(.+)");
    }

    private String makeRomMemoryInfo() {
        String romMemoryInfo;
        try {
            romMemoryInfo = executeCommand("df -h --total");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        String result = extractData(romMemoryInfo, "total\\s+([\\d,]*G)");
        return  result.substring(0, result.length() - 1);
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
            return environmentVersion;
        }

        try {
            environmentVersion = executeCommand("budgie-desktop --version");
        } catch (IOException | InterruptedException e) {
            // pass
        }
        if (environmentVersion != null) {
            return environmentVersion;
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
            return environmentVersion;
        }

        return null;
    }

    private String makeWindowInterface() {
        String windowInterface;

        try {
            windowInterface = executeCommand("cat /etc/gdm3/custom.conf");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        String waylandEnable = extractData(windowInterface, "#WaylandEnable=(.*)");
        if (Objects.equals(waylandEnable, "true")) {
            return "Wayland";
        } else {
            return "X11";
        }
    }

    private String makeVirtualization() {
        String virtualization;

        try {
            virtualization = executeCommand("systemd-detect-virt");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return virtualization;
    }
}
