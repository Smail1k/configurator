package ru.oleg.configurator.domain.environment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.environment.dto.*;

@Service
@AllArgsConstructor
public class EnvironmentService {
    public MainParameters getMainParameters() {
        return new MainParameters();
    }

    public MainParameters updateMainParameters(MainParameters mainParameters) {
        return new MainParameters();
    }

    public EdgesScreen getEdgesScreen() {
        return new EdgesScreen();
    }

    public EdgesScreen updateEdgesScreen(EdgesScreen edgesScreen) {
        return new EdgesScreen();
    }

    public ScreenLock getScreenLock() {
        return new ScreenLock();
    }

    public ScreenLock updateScreenLock(ScreenLock screenLock) {
        return new ScreenLock();
    }

    public EdgesScreen getEdgesScreen(EdgesScreen edgesScreen) {
        return new EdgesScreen();
    }

    public EdgesScreen updateEdges(EdgesScreen edgesScreen) {
        return new EdgesScreen();
    }

    public RecentFiles getRecentFiles() {
        return new RecentFiles();
    }

    public RecentFiles updateRecentFiles(RecentFiles recentFiles) {
        return new RecentFiles();
    }

    public MemoryTracking getMemoryTracking() {
        return new MemoryTracking();
    }

    public MemoryTracking updateMemoryTracking(MemoryTracking memoryTracking) {
        return new MemoryTracking();
    }
}
