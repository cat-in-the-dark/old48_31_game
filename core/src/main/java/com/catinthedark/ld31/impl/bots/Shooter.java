package com.catinthedark.ld31.impl.bots;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.Constants;

/**
 * Created by over on 07.12.14.
 */
public class Shooter {
    public static enum State {
        QUIET, SHOOT
    }

    public Vector2 pos = new Vector2();
    public State state = State.QUIET;
    public float attackTime = 0;

    public float getAttackTime() {
        return attackTime;
    }

    public void updateAttackTime(float delta) {
        if (this.state == State.SHOOT) {
            attackTime += delta;
        } else {
            attackTime = 0;
        }
    }

    public Shooter(float x, float y) {
        pos.set(x, y);
    }

    @Override
    public Object clone() {
        return new Shooter(pos.x, pos.y);
    }
}
