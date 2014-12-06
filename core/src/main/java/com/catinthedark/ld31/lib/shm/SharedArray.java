package com.catinthedark.ld31.lib.shm;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by over on 03.12.14.
 */
public class SharedArray<T> implements Iterable<T>{
    public final T[] array;
    public final int length;

    public SharedArray(T[] array) {
        this.array = array;
        this.length = array.length;
    }

    public T get(int index) {
        return array[index];
    }
    public void update(int index, Consumer<T> updater) {
        updater.accept(array[index]);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(array, Spliterator.SIZED);
    }

    public Stream<T> stream(){
        return StreamSupport.stream(spliterator(), false);
    }
}
