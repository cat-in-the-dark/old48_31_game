package com.catinthedark.ld31.impl.view;

import com.catinthedark.ld31.impl.common.AttackDirection;
import com.catinthedark.ld31.impl.common.DaddyAttack;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.level.LevelMatrix;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Port;
import com.catinthedark.ld31.lib.view.ScreenManager;

/**
 * Created by over on 06.12.14.
 */
public class ViewSystemDef extends AbstractSystemDef {
    public ViewSystemDef(GameShared gameShared, LevelMatrix.View levelView) {
        sys = new Sys(gameShared, levelView);
        updater(sys::render);
        handleDaddyAttack = asyncPort(sys::handleDaddyAttack);
    }

    final Sys sys;
    public final Port<DaddyAttack> handleDaddyAttack;

    private class Sys {
        Sys(GameShared gameShared, LevelMatrix.View levelView) {
            renderShared = new RenderShared();
            renderShared.levelView = levelView;
            renderShared.gameShared = gameShared;
        }

        final RenderShared renderShared;
        final ScreenManager<RenderShared> screenManager = new ScreenManager<>(new GameScreen());

        void render(float delay) {
            renderShared.delay = delay;
            screenManager.render(renderShared);
        }

        void handleDaddyAttack(DaddyAttack attack) {
            System.out.println("View:" + attack);
            if (attack.direction == AttackDirection.BY_COL)
                renderShared.colAttack = RenderFactory.createColAttack(renderShared, (int) attack
                    .pos.x);
            else
                renderShared.rowAttack = RenderFactory.createRowAttack(renderShared, (int) attack
                    .pos.y);
        }
    }


}
