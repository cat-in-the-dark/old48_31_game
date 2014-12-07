package com.catinthedark.ld31.impl.bots;

import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by over on 07.12.14.
 */
public class AISystemDef extends AbstractSystemDef {

    public AISystemDef(GameShared shared) {
        sys = new Sys(shared);
        updater(sys::update);
        masterDelay = 100;

        createJumper = serialPort(sys::createJumper);
        createShooter = serialPort(sys::createShooter);
        createWalker = serialPort(sys::createWalker);
    }

    final Sys sys;
    public final Port<Integer> createJumper;
    public final Port<Integer> createShooter;
    public final Port<Integer> createWalker;
    public final Pipe<Integer> jumperJump = new Pipe<>();

    private class Sys {
        Sys(GameShared shared) {
            this.shared = shared;
        }

        GameShared shared;
        List<Integer> jumpersIds = new ArrayList<>();
        List<Integer> walkersIds = new ArrayList<>();
        List<Integer> shootersIds = new ArrayList<>();

        void update(float delta) {
            jumpersIds.forEach(jid -> {
                Jumper jumper = shared.jumpers.map(jid);
                if (Math.abs(jumper.pos.x - shared.pPos.get().x) < 10 && jumper.state == Jumper
                    .State.QUIET) {
                    System.out.println("jumper(" + jid + ") active");
                    jumper.state = Jumper.State.IN_JUMP;
                    jumperJump.write(jid);
                    defer(()->{
                        jumper.state = Jumper.State.QUIET;
                    },500);

                }
            });
        }

        void createWalker(Integer id) {
            walkersIds.add(id);
        }

        void createJumper(Integer id) {
            jumpersIds.add(id);
        }

        void createShooter(Integer id) {
            shootersIds.add(id);
        }

    }

}