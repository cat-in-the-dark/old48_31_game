package com.catinthedark.ld31.lib.run;

import com.catinthedark.ld31.lib.AbstractSystemDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by over on 08.11.14.
 */
public class Launcher {

    public static class SystemTask {
        final AbstractSystemDef system;
        final public int delay;

        public SystemTask(AbstractSystemDef system, int delay) {
            this.system = system;
            this.delay = delay;
        }
    }

    private static List<SystemTask> prepare(AbstractSystemDef... systems) {
        List<AbstractSystemDef> sorted = Arrays.stream(systems)
                .sorted((s1, s2) -> Integer.compare(s1.masterDelay, s2.masterDelay))
                .collect(Collectors.toList());

        final List<SystemTask> tasks = new ArrayList<>();

        int delay = sorted.get(0).masterDelay;
        tasks.add(new SystemTask(sorted.get(0), sorted.get(0).masterDelay));

        for (int i = 1; i < sorted.size(); i++) {
            AbstractSystemDef system = sorted.get(i);
            tasks.add(new SystemTask(system, system.masterDelay - delay));
            delay += system.masterDelay;
        }

        return tasks;
    }

    public static Thread inThread(AbstractSystemDef... systems) {
        final List<SystemTask> tasks = prepare(systems);

        Thread thread = new Thread(new Runnable() {
            boolean running = true;
            long lastStep = System.currentTimeMillis();

            @Override
            public void run() {
                tasks.forEach(t -> t.system.start());

                while (running) {
                    try {
                        for (SystemTask task : tasks) {
                            Thread.sleep(task.delay);
                            task.system.update(((float)(System.currentTimeMillis() - lastStep))/1000f);
                        }
                    } catch (InterruptedException ex) {
                        running = false;
                    }
                    lastStep = System.currentTimeMillis();
                }
            }
        });
        thread.start();
        return thread;
    }

    public static CallbackRunner viaCallback(AbstractSystemDef... systems) {
        final List<SystemTask> tasks = prepare(systems);
        tasks.forEach(t -> t.system.start());

        return (delay) -> {
            for (SystemTask task : tasks) {
                Thread.sleep(task.delay);
                task.system.update(delay);
            }
        };

    }

}
