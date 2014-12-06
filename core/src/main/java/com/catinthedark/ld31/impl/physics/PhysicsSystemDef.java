package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.ld31.impl.common.*;
import com.catinthedark.ld31.impl.message.BlockCreateReq;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Port;

import java.util.HashMap;
import java.util.Map;

import static com.catinthedark.ld31.lib.util.SysUtils.conditional;

/**
 * Created by over on 06.12.14.
 */
public class PhysicsSystemDef extends AbstractSystemDef {
    public PhysicsSystemDef(GameShared gameShared) {
        masterDelay = 16;
        sys = new Sys(gameShared);
        updater(conditional(() -> sys.state == GameState.IN_GAME, sys::update));
        handlePlayerMove = asyncPort(sys::handlePlayerMove);
        handlePlayerJump = asyncPort(sys::handlePlayerJump);
        handleDaddyAttack = asyncPort(sys::handleDaddyAttack);
        onCreateBlock = asyncPort(sys::createBlock);
        onGameStart = serialPort(sys::onGameStart);
    }

    private final Sys sys;
    public final Port<DirectionX> handlePlayerMove;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<BlockCreateReq> onCreateBlock;
    public final Port<Nothing> handlePlayerJump;
    public final Port<Nothing> onGameStart;

    private class Sys {
        Sys(GameShared gameShared) {
            this.gameShared = gameShared;
        }

        GameShared gameShared;
        Vector2 pPos = new Vector2();
        World world;
        final Map<Long, Body> blocks = new HashMap<>();
        Body playerBody;
        GameState state = GameState.INIT;
        float oldYVelocity = 0;
        boolean canJump = true;

        void update(float delta) {
            System.out.print("step");
            world.step(delta, 6, 10);
            gameShared.pPos.update((pos) -> pos.set(playerBody.getPosition()));
            if(playerBody.getLinearVelocity().y == 0 && oldYVelocity == 0) {
                canJump = true;
            }
            oldYVelocity = playerBody.getLinearVelocity().y;

        }

        void onGameStart(Nothing ignored) throws InterruptedException {
            System.out.println("Physics: on game start");
            if (world != null)
                world.dispose();
            world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
            playerBody = BodyFactory.createPlayer(world);
            state = GameState.IN_GAME;
        }

        void handlePlayerMove(DirectionX dir) {
            if (dir == DirectionX.LEFT) {
            } else {
                if (playerBody.getLinearVelocity().x < 10)
                    playerBody.applyLinearImpulse(Constants.WALKING_FORCE_RIGHT, new Vector2(0,
                        0), true);
            }
        }

        void handlePlayerJump(Nothing ignored) {
            if(canJump) {
                playerBody.applyLinearImpulse(Constants.JUMP_IMPULSE, new Vector2(0, 0), true);
                canJump = false;
            }
        }

        void handleDaddyAttack(DaddyAttack attack) {
            System.out.println("Physics:" + attack);
        }

        void createBlock(BlockCreateReq req) {
            Body blockBody = BodyFactory.createBlock(world, req.type, req.x, req.y);
            blocks.put(req.id, blockBody);
        }
    }
}
