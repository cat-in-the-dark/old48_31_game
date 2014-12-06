package com.catinthedark.ld31.lib.view;

import java.util.Arrays;
import java.util.List;

/**
 * Created by over on 12.11.14.
 */
public class ScreenManager<T> {
    private final List<Screen<T>> screens;
    private Screen<T> current;

    public ScreenManager(Screen<T>... screens) {
        this.screens = Arrays.asList(screens);
        current = screens[0];
    }

    public static class ScreenIndexOverflow extends RuntimeException {
        ScreenIndexOverflow(Integer index) {
            super(index.toString());
        }
    }

    private interface IndexModifyFn {
        int modify(int index);
    }

    public void _goto(IndexModifyFn fn) {
        int index = fn.modify(screens.indexOf(current));

        if (index >= screens.size() || index < 0)
            throw new ScreenIndexOverflow(index);

        current = screens.get(index);
        current.beforeShow();
    }

    public void next() {
        _goto(i -> i + 1);
    }

    public void prev() {
        _goto(i -> i - 1);
    }

    public void goTo(int index) {
        _goto(i -> index);
    }

    public void render(T shared) {
        current.render(shared);
    }
}
