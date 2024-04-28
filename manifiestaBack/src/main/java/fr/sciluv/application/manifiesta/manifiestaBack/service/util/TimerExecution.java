package fr.sciluv.application.manifiesta.manifiestaBack.service.util;

import java.util.Timer;
import java.util.TimerTask;

public class TimerExecution {
    private final Timer timer;

    public TimerExecution() {
        this.timer = new Timer();
    }

    public void executeAfterDelay(long delay, Runnable task) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }

    public static void main(String[] args) {
        TimerExecution executor = new TimerExecution();

        // Exemple d'utilisation
        executor.executeAfterDelay(5000, () -> {
            System.out.println("Ce message est affiché après un délai de 5 secondes.");
        });
    }
}
