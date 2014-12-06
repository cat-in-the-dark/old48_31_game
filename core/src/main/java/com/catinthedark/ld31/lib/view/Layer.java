package com.catinthedark.ld31.lib.view;

/**
 * Created by over on 12.11.14.
 */
public interface Layer<T> {
    void render(T shared);
    default void init(){

    }
}
