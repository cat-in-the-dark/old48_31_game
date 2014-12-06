package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.DaddyAttack;
import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
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
        handlePlayerJump = asyncPort(sys::handlePlayerJump);
        handleDaddyAttack = asyncPort(sys::handleDaddyAttack);
    }

    private final Sys sys;
    public final Port<DirectionX> handlePlayerMove;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<Nothing> handlePlayerJump;

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

        void handlePlayerJump(Nothing ignored){
            System.out.println("Physics: jump");
        }

        void handleDaddyAttack(DaddyAttack attack){
            System.out.println("Physics:" + attack);
        }
    }
}
