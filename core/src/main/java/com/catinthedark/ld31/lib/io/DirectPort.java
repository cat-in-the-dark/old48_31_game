package com.catinthedark.ld31.lib.io;

import com.catinthedark.ld31.lib.common.DispatchableLogicFunction;

/**
 * Created by over on 14.11.14.
 */
public class DirectPort<T> implements Port<T> {
    private final DispatchableLogicFunction<T> fn;

    public DirectPort(DispatchableLogicFunction<T> fn) {
        this.fn = fn;
    }

    @Override
    public void write(T msg, Runnable onWrite) throws InterruptedException{
        fn.apply(msg);
        onWrite.run();
    }
}
