package com.catinthedark.ld31.lib.common;

/**
 * Created by over on 03.11.14.
 */
@FunctionalInterface
public interface DispatchableLogicFunction<T> {
    public void apply(T emitted) throws InterruptedException;
}
