package com.catinthedark.ld31.impl.bots;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by over on 07.12.14.
 */
public class Walker {
    public static enum State {
        QUIET, WALK
    }

    public Vector2 pos = new Vector2();

    public Walker(float x, float y) {
        pos.set(x, y);
    }

    @Override
    public Object clone() {
        return new Walker(pos.x, pos.y);
    }
}
