package com.catinthedark.ld31.lib.shm;

import java.util.function.Consumer;

/**
 * Created by over on 03.12.14.
 */

/**
 * T must be mutable data structure with no nullable fields!
 * T must be valid value as soon as constructed!
 *
 * @param <T>
 */
public class SharedVal<T> {
    private final T val;

    public SharedVal(T val){
        this.val = val;
    }
    public T get(){
        return val;
    }

    public void update(Consumer<T> updater){
        updater.accept(val);
    }
}
