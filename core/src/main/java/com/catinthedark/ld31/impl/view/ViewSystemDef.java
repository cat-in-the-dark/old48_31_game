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
        gotoGameOver = serialPort(sys::gotoGameOver);
        onGameStart = serialPort(sys::onGameStart);
        createJumper = serialPort(sys::createJumper);
        createShooter = serialPort(sys::createShooter);
        createWalker = serialPort(sys::createWalker);
        createBottle = serialPort(sys::createBottle);
        bottleDestroyed = serialPort(sys::bottleDestroyed);
        jumperDestroyed = serialPort(sys::jumperDestroyed);
        walkerDestroyed = serialPort(sys::walkerDestroyed);
        shooterDestroyed = serialPort(sys::shooterDestroyed);
    }

    final Sys sys;
    public final Port<DaddyAttack> handleDaddyAttack;
    public final Port<Nothing> gotoTutorial;
    public final Port<Nothing> gotoTutorial2;
    public final Port<Nothing> gotoTutorial3;
    public final Port<Nothing> gotoMenu;
    public final Port<Nothing> gotoGameOver;
    public final Port<Nothing> onGameStart;
    public final Port<Integer> createJumper;
    public final Port<Integer> createShooter;
    public final Port<Integer> createWalker;
    public final Port<Integer> createBottle;
    public final Port<Integer> bottleDestroyed;
    public final Port<Integer> jumperDestroyed;
    public final Port<Integer> walkerDestroyed;
    public final Port<Integer> shooterDestroyed;

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
            renderShared.gameShared.gameScore = (int) renderShared.gameShared.pPos.get().x;
        }

        void _cameraMove() {
            Vector2 ppos = renderShared.gameShared.pPos.get();
            Vector3 camPos = renderShared.camera.position;
            float distance = ppos.x * 32 - camPos.x;

            if (distance > 0) {
                renderShared.camera.position.set(ppos.x * 32, camPos.y, camPos.z);
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
                renderShared.cooldownColAnimation = RenderFactory.createCoolDownAnimationCol(renderShared);
            } else {
//                renderShared.rowAttack = RenderFactory.createRowAttack(renderShared, (int) attack
//                    .pos.y);
                renderShared.dedFistAttackRow = RenderFactory.createDedFistRow(renderShared,
                    (int) attack
                        .pos.y);
                renderShared.cooldownRowAnimation = RenderFactory.createCoolDownAnimationRow(renderShared);
            }
        }

        void createJumper(Integer id){
            renderShared.jumpersIds.add(id);
        }
        void createWalker(Integer id) {
            renderShared.walkerids.add(id);
        }
        void createShooter(Integer id) {
            renderShared.shootersIds.add(id);
        }
        void createBottle(Integer id) {
            renderShared.bottlesIds.add(id);
        }

        void jumperDestroyed(Integer id) {
            System.out.println(renderShared.jumpersIds);
            System.out.println(String.format("remove jumper %d from view", id));
            renderShared.jumpersIds.remove((Object) id);
            System.out.println(renderShared.jumpersIds);
        }

        void bottleDestroyed(Integer id) {
            renderShared.bottlesIds.remove((Object) id);
        }
        void walkerDestroyed(Integer id) {
            renderShared.walkerids.remove((Object) id);
        }

        void shooterDestroyed(Integer id) {
            renderShared.shootersIds.remove((Object) id);
        }

        void gotoTutorial(Nothing none) {
            state = GameState.TUTORIAL1;
            screenManager.goTo(1);
        }

        void gotoTutorial2(Nothing none) {
            state = GameState.TUTORIAL2;
            screenManager.goTo(2);
        }

        void gotoTutorial3(Nothing none) {
            state = GameState.TUTORIAL3;
            screenManager.goTo(3);
        }

        void gotoMenu(Nothing none) {
            state = GameState.MENU;
            screenManager.goTo(4);
        }

        void onGameStart(Nothing none) {
            System.out.println("view: gameStart");
            renderShared.bottlesIds.clear();
            renderShared.jumpersIds.clear();
            renderShared.walkerids.clear();
            renderShared.shootersIds.clear();
            renderShared.camera.position.x = 755/2;
            renderShared.camera.position.y = 520/2;
            renderShared.camera.update();
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
