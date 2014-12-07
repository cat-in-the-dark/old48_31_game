package com.catinthedark.ld31.impl.bots;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by over on 07.12.14.
 */
public class Jumper {
    public static enum State{
        QUIET, IN_JUMP
    }

    public State state = State.QUIET;
    public Vector2 pos = new Vector2();

    public Jumper(float x, float y){
        pos = new Vector2(x,y);
    }

    @Override
    public Object clone()  {
        return new Jumper(pos.x, pos.y);
    }
}
