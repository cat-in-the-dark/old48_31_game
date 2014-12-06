package com.catinthedark.ld31.lib.view;

/**
 * Created by over on 12.11.14.
 */
public abstract class Screen<T> {
    private final Layer<T>[] layers;
    public final int ttl;

    public Screen(Layer<T>... layers) {
        this(0, layers);
    }

    public Screen(int ttl, Layer<T>... layers) {
        this.layers = layers;
        this.ttl = ttl;
    }

    public void beforeShow() {

    }

    public void beforeRender() {

    }

    public void render(T shared) {
        beforeRender();
        for (Layer layer : layers)
            layer.render(shared);
        postEffect(shared);
    }

    /**
     * no post effects by default
     *
     * @param shared
     */
    public void postEffect(T shared) {

    }
}
