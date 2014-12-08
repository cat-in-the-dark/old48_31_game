package com.catinthedark.ld31.impl.bots;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.DirectionX;

import java.util.Random;

/**
 * Created by over on 07.12.14.
 */
public class Walker {

    public float nSteps = 0;
    public DirectionX dirX = DirectionX.LEFT;
    public final Random rand = new Random();
    public static final int MAX_STEPS = 5;

    public Vector2 pos = new Vector2();
    public float walkTime = 0;

    public Walker(float x, float y) {
        pos.set(x, y);
    }

    @Override
    public Object clone() {
        return new Walker(pos.x, pos.y);
    }
}
