package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by over on 06.12.14.
 */
public class DaddyAttack {
    public final Vector2 pos;
    public final AttackDirection direction;

    public DaddyAttack(Vector2 pos, AttackDirection direction) {
        this.pos = pos;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("DaddyAttack((%f,%f), %s", pos.x, pos.y, direction.toString());
    }
}
