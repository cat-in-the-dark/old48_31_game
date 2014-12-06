package com.catinthedark.ld31.impl.view;

import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.view.ScreenManager;

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
            renderShared = new RenderShared();
            renderShared.gameShared = gameShared;
        }

        final RenderShared renderShared;
        final ScreenManager<RenderShared> screenManager = new ScreenManager<>(new GameScreen());

        void render(float delay) {
            renderShared.delay  = delay;
            screenManager.render(renderShared);
        }
    }


}
