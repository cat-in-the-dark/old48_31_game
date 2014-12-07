package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.catinthedark.ld31.impl.common.*;
import com.catinthedark.ld31.impl.level.LevelMatrix2;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Port;
import com.catinthedark.ld31.lib.view.ScreenManager;

/**
 * Created by over on 06.12.14.
 */
public class ViewSystemDef extends AbstractSystemDef {
    public ViewSystemDef(GameShared gameShared, LevelMatrix2.View levelView) {
        sys = new Sys(gameShared, levelView);
        updater(sys::render);
        handleDaddyAttack = asyncPort(sys::handleDaddyAttack);
        gotoTutorial = serialPort(sys::gotoTutorial);
        gotoTutorial2 = serialPort(sys::gotoTutorial2);
        gotoTutorial3 = serialPort(sys::gotoTutorial3);
        gotoMenu = serialPort(sys::gotoMenu);
        onGameStart = serialPort(sys::onGameStart);
    }

    final Sys sys;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<Nothing> gotoTutorial;
    public final Port<Nothing> gotoTutorial2;
    public final Port<Nothing> gotoTutorial3;
    public final Port<Nothing> gotoMenu;
    public final Port<Nothing> onGameStart;

    private class Sys {
        Sys(GameShared gameShared, LevelMatrix2.View levelView) {
            renderShared = new RenderShared();
            renderShared.levelView = levelView;
            renderShared.gameShared = gameShared;
            gameShared.cameraPosX.update(pos -> pos.set(renderShared.camera.position.x,
                renderShared.camera.position.y));
        }

        GameState state = GameState.INIT;
        final RenderShared renderShared;
        final ScreenManager<RenderShared> screenManager = new ScreenManager<>(new TextureScreen
            (Assets.textures.logo),
            new TextureScreen(Assets.textures.t1), new TextureScreen(Assets.textures.t2), new
            TextureScreen(Assets.textures.t3), new TextureScreen(Assets.textures.menu), new
            GameScreen(), new GameOverScreen(), new GameWinScreen());

        void render(float delay) {
            renderShared.delay = delay;
            screenManager.render(renderShared);
            _cameraMove();
        }

        void _cameraMove() {
            Vector2 ppos = renderShared.gameShared.pPos.get();
            Vector3 camPos = renderShared.camera.position;
            float distance = ppos.x * 32 - camPos.x;

            if (distance > 128) {
                renderShared.camera.position.set(camPos.x + 5, camPos.y, camPos.z);
                renderShared.camera.update();
                renderShared.gameShared.cameraPosX.update(vec -> vec.x = renderShared.camera
                    .position.x);
            }
        }

        void handleDaddyAttack(DaddyAttack attack) {
            System.out.println("View:" + attack);
            if (attack.direction == AttackDirection.BY_COL) {
//                renderShared.colAttack = RenderFactory.createColAttack(renderShared, (int) attack
//                    .pos.x);
                renderShared.dedFistAttackCol = RenderFactory.createDedFistCol(renderShared,
                    (int) attack
                        .pos.x);
            } else {
//                renderShared.rowAttack = RenderFactory.createRowAttack(renderShared, (int) attack
//                    .pos.y);
                renderShared.dedFistAttackRow = RenderFactory.createDedFistRow(renderShared,
                    (int) attack
                        .pos.y);
            }
        }

        void gotoTutorial(Nothing none) {
            state = GameState.TUTORIAL1;
            screenManager.goTo(1);
        }

        void gotoTutorial2(Nothing none) {
            state = GameState.TUTORIAL1;
            screenManager.goTo(2);
        }

        void gotoTutorial3(Nothing none) {
            state = GameState.TUTORIAL1;
            screenManager.goTo(3);
        }

        void gotoMenu(Nothing none) {
            state = GameState.TUTORIAL1;
            screenManager.goTo(4);
        }

        void onGameStart(Nothing none) {
            state = GameState.IN_GAME;
            screenManager.goTo(5);
        }

        void gotoGameOver(Nothing none) {
            state = GameState.GAME_OVER;
            screenManager.goTo(6);
        }

        void gotoGameWin(Nothing none) {
            state = GameState.GAME_WIN;
            screenManager.goTo(7);
        }
    }


}
