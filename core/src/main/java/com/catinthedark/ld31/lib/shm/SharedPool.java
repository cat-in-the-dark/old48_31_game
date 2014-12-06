package com.catinthedark.ld31.lib.shm;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by over on 15.11.14.
 */
public class SharedPool<T> {
    private final T[] memory;
    Queue<Integer> pointerPool;

    public SharedPool(Class<? extends T> clazz, int size) {
        memory = (T[]) Array.newInstance(clazz, size);
        pointerPool = IntStream.rangeClosed(0, size - 1)
                .boxed()
                .collect(Collectors.toCollection(() -> new LinkedList<>()));
    }

    public T map(int pointer) {
        return memory[pointer];
    }

    public int alloc(T data) {
        int pointer = pointerPool.poll();
        memory[pointer] = data;
        return pointer;
    }


    public void update(int pointer, Consumer<T> update) {
        update.accept(memory[pointer]);
    }

    public void free(int pointer) {
        //System.out.println("free:" + pointer);
        pointerPool.add(pointer);
        //System.out.print("pointers:" + pointerPool);
    }
}
