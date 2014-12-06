package com.catinthedark.ld31.lib.common;

/**
 * Created by over on 08.11.14.
 */
public class Updater {
    public final LogicalFunction fn;
    public final int period;

    public Updater(LogicalFunction fn, int period) {
        this.fn = fn;
        this.period = period;
    }

    public Updater(LogicalFunction fn) {
        this(fn, 1);
    }
}
