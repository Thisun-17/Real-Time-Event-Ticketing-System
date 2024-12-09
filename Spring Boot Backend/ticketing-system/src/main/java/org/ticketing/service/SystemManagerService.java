package org.ticketing.service;

import org.springframework.stereotype.Service;
import org.ticketing.entity.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SystemManagerService {
    private ExecutorService executorService;
    private volatile boolean isRunning;

    public void initializeSystem(SystemConfig config) {
        stopSystem();
        executorService = Executors.newCachedThreadPool();
    }

    public void startSystem() {
        if (isRunning) {
            return;
        }
        isRunning = true;
    }

    public void stopSystem() {
        if (!isRunning) {
            return;
        }
        isRunning = false;

        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}