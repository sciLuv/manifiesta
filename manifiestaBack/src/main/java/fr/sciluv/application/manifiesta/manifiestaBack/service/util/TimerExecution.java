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
}
