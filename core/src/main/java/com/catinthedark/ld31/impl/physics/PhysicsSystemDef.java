package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Port;

/**
 * Created by over on 06.12.14.
 */
public class PhysicsSystemDef extends AbstractSystemDef {
    public PhysicsSystemDef(GameShared gameShared) {
        masterDelay = 16;
        sys = new Sys(gameShared);
        updater(sys::update);
        handlePlayerMove = asyncPort(sys::handlePlayerMove);
    }

    private final Sys sys;
    public final Port<DirectionX> handlePlayerMove;

    private class Sys {
        Sys(GameShared gameShared){
            this.gameShared = gameShared;
        }

        GameShared gameShared;
        Vector2 pPos = new Vector2();

        void update(float delay) {
            gameShared.pPos.update((pos) -> pos.x = pPos.x);
        }

        void handlePlayerMove(DirectionX dir) {
            if (dir == DirectionX.LEFT) {
                pPos.x -= 5;
            } else {
                pPos.x += 5;
            }
        }
    }
}
