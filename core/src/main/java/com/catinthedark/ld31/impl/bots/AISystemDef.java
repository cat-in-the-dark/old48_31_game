package com.catinthedark.ld31.impl.bots;

import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.common.GameState;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

import java.util.ArrayList;
import java.util.List;

import static com.catinthedark.ld31.lib.util.SysUtils.conditional;

/**
 * Created by over on 07.12.14.
 */
public class AISystemDef extends AbstractSystemDef {

    public AISystemDef(GameShared shared) {
        sys = new Sys(shared);
        updater(conditional(() -> sys.state == GameState.IN_GAME, sys::update));
        masterDelay = 100;

        createJumper = serialPort(sys::createJumper);
        createShooter = serialPort(sys::createShooter);
        createWalker = serialPort(sys::createWalker);
        destroyJumper = serialPort(sys::destroyJumper);
        destroyWalker = serialPort(sys::destroyWalker);
        destroyShooter = serialPort(sys::destroyShooter);
        gameStart = serialPort(sys::gameStart);
        gameOver = serialPort(sys::gameOver);
    }

    final Sys sys;
    public final Port<Integer> createJumper;
    public final Port<Integer> createShooter;
    public final Port<Integer> createWalker;
    public final Port<Integer> destroyJumper;
    public final Port<Integer> destroyWalker;
    public final Port<Integer> destroyShooter;
    public final Pipe<Integer> jumperJump = new Pipe<>();
    public final Pipe<Integer> walkerGo = new Pipe<>();
    public final Pipe<Integer> shooterShoot = new Pipe<>();
    public final Port<Nothing> gameStart;
    public final Port<Nothing> gameOver;

    private class Sys {
        Sys(GameShared shared) {
            this.shared = shared;
        }

        GameShared shared;
        List<Integer> jumpersIds = new ArrayList<>();
        List<Integer> walkersIds = new ArrayList<>();
        List<Integer> shootersIds = new ArrayList<>();
        GameState state = GameState.INIT;

        void update(float delta) {
            jumpersIds.forEach(jid -> {
                Jumper jumper = shared.jumpers.map(jid);
                if (Math.abs(jumper.pos.x - shared.pPos.get().x) < 10 && jumper.state == Jumper
                    .State.QUIET) {
                    System.out.println("jumper(" + jid + ") active");
                    jumper.state = Jumper.State.IN_JUMP;
                    jumperJump.write(jid);
                    defer(() -> {
                        jumper.state = Jumper.State.QUIET;
                    }, 500);

                }
            });

            walkersIds.forEach(jid -> {
                Walker walker = shared.walkers.map(jid);
                if (Math.abs(walker.pos.x - shared.pPos.get().x) < 10) {
                    System.out.println("walker(" + jid + ") active");
                    walkerGo.write(jid);
                }
            });

            shootersIds.forEach(jid -> {
                Shooter shooter = shared.shooters.map(jid);
                if (Math.abs(shooter.pos.x - shared.pPos.get().x) < 10 && shooter.state ==
                    Shooter.State.QUIET) {
                    System.out.println("shooter(" + jid + ") active");
                    shooter.state = Shooter.State.SHOOT;
                    shooterShoot.write(jid);
                    defer(() -> {
                        shooter.state = Shooter.State.QUIET;
                    }, 1100);

                }
            });
        }

        void createWalker(Integer id) {
            walkersIds.add(id);
        }

        void createJumper(Integer id) {
            jumpersIds.add(id);
        }

        void destroyJumper(Integer id) {
            jumpersIds.remove((Object) id);
        }

        void destroyWalker(Integer id) {
            walkersIds.remove((Object) id);
        }

        void destroyShooter(Integer id) {
            shootersIds.remove((Object) id);
        }

        void createShooter(Integer id) {
            shootersIds.add(id);
        }

        void gameStart(Nothing nothing) {
            shootersIds.clear();
            jumpersIds.clear();
            walkersIds.clear();
            state = GameState.IN_GAME;
        }

        void gameOver(Nothing nothing) {
            state = GameState.GAME_OVER;
        }

    }

}
