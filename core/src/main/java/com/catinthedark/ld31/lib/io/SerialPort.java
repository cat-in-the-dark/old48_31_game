package com.catinthedark.ld31.lib.io;

import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.DispatchableLogicFunction;

/**
 * Created by over on 14.11.14.
 */
public class SerialPort<T> implements Port<T> {
    final AbstractSystemDef systemDef;
    final DispatchableLogicFunction<T> fn;

    public SerialPort(AbstractSystemDef systemDef, DispatchableLogicFunction<T> fn) {
        this.systemDef = systemDef;
        this.fn = fn;
    }

    @Override
    public void write(T msg, Runnable onWrite) {
        systemDef.masterQueue.add(() -> {
            fn.apply(msg);
            onWrite.run();
        });
    }
}
