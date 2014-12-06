package com.catinthedark.ld31.lib;

import com.catinthedark.ld31.lib.common.*;
import com.catinthedark.ld31.lib.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by over on 08.11.14.
 */
public abstract class AbstractSystemDef {
    protected List<Updater> updaters = new ArrayList<>();
    protected boolean isQueueBlocking = false;
    public int masterDelay = 0;

    public int getMasterDelay() {
        return masterDelay;
    }

    public final BlockingQueue<RunnableEx> masterQueue = new LinkedBlockingQueue<>();
    private final List<BlockingQueue<RunnableEx>> secondaryQueues = new ArrayList<>();


    public <T> Port<T> serialPort(DispatchableLogicFunction<T> fn) {
        return new SerialPort<>(this, fn);
    }

    public <T> Port<T> directPort(DispatchableLogicFunction<T> fn) {
        return new DirectPort<>(fn);
    }

    public <T> Port<T> asyncPort(DispatchableLogicFunction<T> fn) {
        return new AsyncPort<>(masterQueue, fn);
    }

    public <T> Port<T> singleMsgPort(DispatchableLogicFunction<T> fn) {
        BlockingQueue<RunnableEx> queue = new LinkedBlockingDeque<>();
        secondaryQueues.add(queue);
        return new SingleMsgPort<>(queue, fn);
    }

    public Updater updater(LogicalFunction fn) {
        return updater(fn, 1);
    }

    public Updater updater(LogicalFunction fn, int period) {
        Updater updater = new Updater(fn, period);
        updaters.add(updater);
        return updater;
    }


    public void update(float delay) throws InterruptedException {
        for (Updater updater : updaters)
            updater.fn.apply(delay);

        NoArgFunction<RunnableEx> queueOp;
        if (isQueueBlocking)
            queueOp = masterQueue::take;
        else
            queueOp = masterQueue::poll;

        RunnableEx runnableEx;
        while ((runnableEx = queueOp.apply()) != null) {
            //System.out.println("get runnableEx");
            runnableEx.run();
        }

        for (BlockingQueue<RunnableEx> queue : secondaryQueues)
            while ((runnableEx = queue.poll()) != null)
                runnableEx.run();

    }

    public void start() {
        System.out.println("Starting system: " + this.toString());
        onStart();
    }

    public void onStart() {
        if (!secondaryQueues.isEmpty() && isQueueBlocking)
            throw new RuntimeException("Could not mix blocking master queue with Async ports! Choose one feature");
    }


    protected void defer(RunnableEx runnableEx, int delay) {
        new Thread(() -> {
            try {
                System.out.println("add defer:orig");
                Thread.currentThread().sleep(delay);
                masterQueue.put(runnableEx);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Shutdown defer thread");
        }).start();

    }
}