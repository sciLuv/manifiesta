package fr.sciluv.application.manifiesta.manifiestaBack.service.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerExecution {
    private ScheduledExecutorService scheduler;

    public TimerExecution() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void executeAfterDelay(long delay, Runnable task) {
        if (!scheduler.isShutdown()) {
            scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
        }
    }

    public void shutdown() {
        scheduler.shutdownNow();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Tâches non terminées après arrêt !");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}