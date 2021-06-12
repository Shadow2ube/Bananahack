package ca.unmined.util;

import java.util.TimerTask;
import java.util.function.Predicate;

public class Task extends TimerTask {

    private NoReturn task;

    public Task(NoReturn task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }

}
