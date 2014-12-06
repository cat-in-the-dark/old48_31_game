package com.catinthedark.ld31.impl.view;

import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;

/**
 * Created by over on 06.12.14.
 */
public class ViewSystemDef extends AbstractSystemDef {
    public ViewSystemDef(GameShared gameShared) {
        sys = new Sys(gameShared);
        updater(sys::render);
    }

    final Sys sys;

    private class Sys {
        Sys(GameShared gameShared) {
            this.gameShared = gameShared;
        }

        final GameShared gameShared;

        void render(float delay) {
            System.out.println("View: ppos.x = " + gameShared.pPos.get().x);
        }
    }


}
