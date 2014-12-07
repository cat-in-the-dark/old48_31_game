package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.ld31.impl.bots.Walker;
import com.catinthedark.ld31.impl.common.*;
import com.catinthedark.ld31.impl.message.BlockCreateReq;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

import java.util.*;

import static com.catinthedark.ld31.lib.util.SysUtils.conditional;

/**
 * Created by over on 06.12.14.
 */
public class PhysicsSystemDef extends AbstractSystemDef {
    public PhysicsSystemDef(GameShared gameShared) {
        //masterDelay = 16;
        sys = new Sys(gameShared);
        updater(conditional(() -> sys.state == GameState.IN_GAME, sys::update));
        handlePlayerMove = asyncPort(sys::handlePlayerMove);
        handlePlayerJump = asyncPort(sys::handlePlayerJump);
        handleDaddyAttack = asyncPort(sys::handleDaddyAttack);
        onCreateBlock = asyncPort(sys::createBlock);
        createJumper = serialPort(sys::createJumper);
        jumperJump = asyncPort(sys::jumperJump);
        createWalker = serialPort(sys::createShooter);
        walkerWalk = asyncPort(sys::walkerGo);
        createShooter = serialPort(sys::createWalker);
        onGameStart = serialPort(sys::onGameStart);
    }

    private final Sys sys;
    public final Port<DirectionX> handlePlayerMove;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<BlockCreateReq> onCreateBlock;
    public final Port<Integer> createJumper;
    public final Port<Integer> jumperJump;
    public final Port<Integer> createWalker;
    public final Port<Integer> walkerWalk;
    public final Port<Integer> createShooter;
    public final Port<Nothing> handlePlayerJump;
    public final Port<Nothing> onGameStart;
    public final Pipe<Long> blockDestroyed = new Pipe<>();
    public final Pipe<Integer> jumpersDestroyed = new Pipe<>(this);

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
        Box2DDebugRenderer dbgRender = new Box2DDebugRenderer();
        public Map<Integer, Body> jumpers = new HashMap<>();
        public Map<Integer, Body> walkers = new HashMap<>();
        public Map<Integer, Body> shooters = new HashMap<>();

        void update(float delta) {
            world.step(delta, 6, 100);
            gameShared.pPos.update((pos) -> pos.set(playerBody.getPosition()));
            if (playerBody.getLinearVelocity().y == 0 && oldYVelocity == 0) {
                canJump = true;
            }
            oldYVelocity = playerBody.getLinearVelocity().y;

            jumpers.entrySet().forEach((kv) -> {
                gameShared.jumpers.update(kv.getKey(), (j) -> {
                    j.pos.set(kv.getValue().getPosition());
                });
            });

            walkers.entrySet().forEach((kv) -> {
                gameShared.walkers.update(kv.getKey(), (w) -> {
                    w.pos.set(kv.getValue().getPosition());
                });
            });

            shooters.entrySet().forEach((kv) -> {
                gameShared.shooters.update(kv.getKey(), (s) -> {
                    s.pos.set(kv.getValue().getPosition());
                });
            });

            float camPosX = gameShared.cameraPosX.get().x;
            if (playerBody.getPosition().x * 32 + Constants.GAME_RECT.x < camPosX) {
                playerBody.setLinearVelocity(0, playerBody.getLinearVelocity().y);
                playerBody.getPosition().set((camPosX - Constants.GAME_RECT.x) / 32 + 1, playerBody.getPosition().y);
            }

            Camera cam = new OrthographicCamera(755 / 32, 520 / 32);
            cam.position.set(gameShared.cameraPosX.get().x / 32, gameShared.cameraPosX.get().y /
                32, 0);
            cam.update();
            dbgRender.render(world, cam.combined);

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
                float camPosX = gameShared.cameraPosX.get().x;
                if (playerBody.getPosition().x * 32 + Constants.GAME_RECT.x > camPosX) {
                    if (playerBody.getLinearVelocity().x > -10) {
                        playerBody.applyLinearImpulse(Constants.WALKING_FORCE_LEFT, new Vector2(0,
                                0), true);
                    }
                }
            } else {
                if (playerBody.getLinearVelocity().x < 10)
                    playerBody.applyLinearImpulse(Constants.WALKING_FORCE_RIGHT, new Vector2(0,
                        0), true);
            }
        }

        void handlePlayerJump(Nothing ignored) {
            if (canJump) {
                playerBody.applyLinearImpulse(Constants.JUMP_IMPULSE, new Vector2(0, 0), true);
                canJump = false;
            }
        }


        void handleDaddyAttack(DaddyAttack attack) {
            System.out.println("Physics:" + attack);
            if (attack.direction == AttackDirection.BY_COL) {
                float camPosX = gameShared.cameraPosX.get().x;
                //normalize
                camPosX -= Constants.GAME_RECT.getWidth() / 2;
                if (camPosX < 0)
                    camPosX = 0;

                float attackX = attack.pos.x;
                attackX = attackX - attackX % 32;
                attackX /= 32;
                System.out.println("cam_x:" + camPosX);
                System.out.println("attackX:" + attackX);

                List<Long> blocksForRemove = new ArrayList<>();
                for (Map.Entry<Long, Body> kv : blocks.entrySet()) {
                    //System.out.println("bpos:" + kv.getValue().getPosition());
                    if (Math.abs(camPosX / 32 + attackX - kv.getValue().getPosition().x) < 1) {
                        world.destroyBody(kv.getValue());
                        blocksForRemove.add(kv.getKey());
                        blockDestroyed.write(kv.getKey());
                        System.out.println("desttoy block:" + kv.getValue().getPosition());
                    }
                }
                blocksForRemove.forEach((id) -> blocks.remove(id));

                List<Integer> jumpersForRemove = new ArrayList<>();
                for (Map.Entry<Integer, Body> kv : jumpers.entrySet()) {
                    if (Math.abs(camPosX / 32 + attackX - kv.getValue().getPosition().x) < 1) {
                        world.destroyBody(kv.getValue());
                        jumpersForRemove.add(kv.getKey());
                        jumpersDestroyed.write(kv.getKey(), () ->{
                            gameShared.jumpers.free(kv.getKey());
                        });
                    }
                }
                jumpersForRemove.forEach((id) -> jumpers.remove((Object) id));


//            int delta = ((int) gameShared.cameraPosX.get().x) - ((int) gameShared.cameraPosX.get
//                ().x) % ((int) Constants.GAME_RECT.width);
//            System.out.print("delta: " + delta);
            } else {
                float attackY = Constants.GAME_RECT.height - attack.pos.y;
                attackY -= 10;
                attackY = attackY - attackY % 32;
                attackY /= 32;
                System.out.println("attackY:" + attackY);

                List<Long> forRemove = new ArrayList<>();
                for (Map.Entry<Long, Body> kv : blocks.entrySet()) {
                    //System.out.println("bpos:" + kv.getValue().getPosition());
                    if (Math.abs(attackY - kv.getValue().getPosition().y) < 1) {
                        world.destroyBody(kv.getValue());
                        forRemove.add(kv.getKey());
                        blockDestroyed.write(kv.getKey());
                        System.out.println("desttoy block:" + kv.getValue().getPosition());
                    }
                }

                forRemove.forEach((id) -> blocks.remove(id));

                List<Integer> jumpersForRemove = new ArrayList<>();
                for (Map.Entry<Integer, Body> kv : jumpers.entrySet()) {
                    if (Math.abs(attackY - kv.getValue().getPosition().y) < 1) {
                        world.destroyBody(kv.getValue());
                        jumpersForRemove.add(kv.getKey());
                        jumpersDestroyed.write(kv.getKey(), () ->{
                            gameShared.jumpers.free(kv.getKey());
                        });
                    }
                }
                jumpersForRemove.forEach((id) -> jumpers.remove((Object) id));
            }

        }

        void createBlock(BlockCreateReq req) {
            Body blockBody = BodyFactory.createBlock(world, req.type, req.x, req.y);
            blocks.put(req.id, blockBody);
        }

        void createJumper(Integer id) {
            jumpers.put(id, BodyFactory.createJumper(world, gameShared.jumpers.map(id)));
        }

        void createWalker(Integer id) {
            walkers.put(id, BodyFactory.createWalker(world, gameShared.walkers.map(id)));
        }
        void createShooter(Integer id) {
            shooters.put(id, BodyFactory.createShooter(world, gameShared.shooters.map(id)));
        }

        void jumperJump(Integer id){
            Body jBody = jumpers.get(id);
            //possible deleted
            if(jBody == null)
                return;
            if(jBody.getLinearVelocity().y != 0)
                return;

            Vector2 jVec = null;
            if(playerBody.getPosition().x > jBody.getPosition().x)
                jVec = Constants.JUMP_IMPULSE_PEDO_RIGHT;
            else
                jVec = Constants.JUMP_IMPULSE_PEDO_LEFT;
            jBody.applyLinearImpulse(jVec.cpy().scl(0.5f), new
                Vector2(0, 0), true);
        }


        void walkerGo(Integer id){
            Body wBody = walkers.get(id);
            Walker walker = gameShared.walkers.map(id);
            //possible removed
            if(wBody == null)
                return;
            if(walker.nSteps <= 0){
                walker.nSteps = walker.rand.nextInt(Walker.MAX_STEPS);
                if(walker.rand.nextInt() % 2 == 0)
                    walker.dirX = DirectionX.LEFT;
                else
                    walker.dirX = DirectionX.RIGHT;
            }
            walker.nSteps--;
            Vector2 wVec = null;
            if(walker.dirX ==  DirectionX.LEFT)
                wVec = Constants.WALKING_FORCE_LEFT;
            else
                wVec = Constants.WALKING_FORCE_RIGHT;

            wBody.applyLinearImpulse(wVec.cpy().scl(0.5f), new Vector2(0,0), true);
        }
    }
}
