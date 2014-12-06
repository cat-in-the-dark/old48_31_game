package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Port;

/**
 * Created by over on 06.12.14.
 */
public class LevelSystemDef extends AbstractSystemDef{

    public LevelSystemDef(GameShared gameShared){
        sys = new Sys(gameShared);
        masterDelay = 16;
        updater(sys::update);
    }

    final Sys sys;

    private class Sys {
        final GameShared gameShared;
        int currentX = 0;

        Sys(GameShared gameShared) {
            this.gameShared = gameShared;
        }

        void update(float delay){
            if (gameShared.pPos.get().x * 32 + 500 > currentX) {
                addPreset();
            }
        }

        public void addPreset() {
            System.out.println("add preset!");
            //TODO: generate presets
            currentX += 10 * 32;
        }

    }
}
