package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
            _cameraMove();
        }

        void _cameraMove() {
            Vector2 ppos = renderShared.gameShared.pPos.get();
            Vector3 camPos = renderShared.camera.position;
            float distance = ppos.x * 32 - camPos.x;
            System.out.println("PLR: " + ppos.x * 32);
            System.out.println("CAM: " + camPos.x);

            if (distance > 128) {
                renderShared.camera.position.set(camPos.x + 5, camPos.y, camPos.z);
                renderShared.camera.update();
            }
        }

        void handleDaddyAttack(DaddyAttack attack) {
            System.out.println("View:" + attack);
            if (attack.direction == AttackDirection.BY_COL) {
//                renderShared.colAttack = RenderFactory.createColAttack(renderShared, (int) attack
//                    .pos.x);
                renderShared.dedFistAttackCol = RenderFactory.createDedFistCol(renderShared, (int) attack
                    .pos.x);
            }
            else {
//                renderShared.rowAttack = RenderFactory.createRowAttack(renderShared, (int) attack
//                    .pos.y);
                renderShared.dedFistAttackRow = RenderFactory.createDedFistRow(renderShared,(int) attack
                    .pos.y);
            }
        }
    }


}
