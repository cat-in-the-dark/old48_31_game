package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.impl.common.DaddyAttack;
import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.message.BlockCreateReq;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Port;

import java.util.HashMap;
import java.util.Map;

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
        onCreateBlock = asyncPort(sys::createBlock);
    }

    private final Sys sys;
    public final Port<DirectionX> handlePlayerMove;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<BlockCreateReq> onCreateBlock;
    public final Port<Nothing> handlePlayerJump;

    private class Sys {
        Sys(GameShared gameShared){
            this.gameShared = gameShared;
            world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
        }

        GameShared gameShared;
        Vector2 pPos = new Vector2();
        World world;
        final Map<Long, Body> blocks = new HashMap<>();

        void update(float delta) {
            gameShared.pPos.update((pos) -> pos.x = pPos.x);
            world.step(delta, 6, 10);
        }

        void onGameStart(Nothing ignored) throws InterruptedException {
            System.out.println("Physics: on game start");
            if (world != null)
                world.dispose();
            world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
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

        void createBlock(BlockCreateReq req) {
            Body blockBody = BodyFactory.createBlock(world, req.type, req.x, req.y);
            blocks.put(req.id, blockBody);
        }
    }
}
