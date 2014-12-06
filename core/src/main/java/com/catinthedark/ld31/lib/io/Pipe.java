package com.catinthedark.ld31.lib.io;

import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.RunnableEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by over on 08.11.14.
 */
public class Pipe<T> {
    private final List<Port<T>> ports = new ArrayList<>();
    private final AbstractSystemDef target;

    public Pipe() {
        this(null);
    }

    /**
     * use this constructor if you want to use
     * Pipe.write with onSend callback;
     * callback will be invoked in master queue of target System
     *
     * @param target
     */
    public Pipe(AbstractSystemDef target) {
        this.target = target;
    }

    public void connect(Port<T>... ports) {
        for (Port port : ports)
            this.ports.add(port);
    }

    public void write(T msg) {
        write(msg, null);
    }

    /**
     * ^^ recursion
     * <p>
     * Be VERY careful with onSend!
     * onSend will be invoked after data was write to destination port;
     * for serial port it's equal to 'after message apply' ONLY
     *
     * @param msg
     * @throws InterruptedException
     */
    public void write(T msg, RunnableEx onSend){
        if (onSend != null)
            if (target == null)
                throw new RuntimeException("Could not use Pipe.write with onSend with empty target system");

        new Runnable() {
            int index = -1;

            @Override
            public void run(){
                index++;
                if (index >= ports.size()) {
                    if (onSend != null)
                        target.masterQueue.add(onSend);
                    return;
                }
                //System.out.println(toString() + ": write to port " + index);
                try {
                    ports.get(index).write(msg, this);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }.run();
    }
}
