package com.catinthedark.ld31.impl.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.*;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.common.RunnableEx;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

import java.awt.*;

/**
 * Created by over on 06.12.14.
 */
public class InputSystemDef extends AbstractSystemDef {
    final Sys sys;
    public final Pipe<Nothing> onGameStart = new Pipe<>();
    public final Pipe<DirectionX> playerMove = new Pipe<>();
    public final Pipe<Nothing> playerJump = new Pipe<>();
    public final Pipe<DaddyAttack> daddyAttack = new Pipe<>();
    public final Port<Nothing> gotoTutorial;
    public final Pipe<Nothing> gotoTutorial2 = new Pipe<>();
    public final Pipe<Nothing> gotoTutorial3 = new Pipe<>();
    public final Pipe<Nothing> gotoMenu = new Pipe<>();


    public InputSystemDef() {
        sys = new Sys();
        masterDelay = 50;
        updater(sys::pollMove);
        gotoTutorial = serialPort(sys::gotoTutorial);


        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                AttackDirection attackDir = null;
                switch (button) {
                    case Input.Buttons.LEFT:
                        attackDir = AttackDirection.BY_COL;
                        break;
                    case Input.Buttons.RIGHT:
                        attackDir = AttackDirection.BY_ROW;
                        break;
                }
                if (attackDir != null)
                    if (Constants.GAME_RECT.contains(screenX, screenY + Constants.WND_HEADER_SIZE)) {
                        daddyAttack.write(new DaddyAttack(new Vector2(screenX, screenY +
                            Constants.WND_HEADER_SIZE),
                            attackDir));
                        defer(() -> {
                            Assets.audios.hit_tv.play();
                            Assets.audios.noise_sfx.play();
                        }, (int) (Constants.ATTACK_TIME * 1000));
                    }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.W)
                    playerJump.write(Nothing.NONE);

                if (keycode == Input.Keys.ENTER) {
                    switch (sys.state) {

                        case INIT:
                            break;
                        case TUTORIAL1:
                            gotoTutorial2.write(Nothing.NONE);
                            sys.state = GameState.TUTORIAL2;
                            break;
                        case TUTORIAL2:
                            gotoTutorial3.write(Nothing.NONE);
                            sys.state = GameState.TUTORIAL3;
                            break;
                        case TUTORIAL3:
                            gotoMenu.write(Nothing.NONE);
                            sys.state = GameState.MENU;
                            break;
                        case MENU:
                            onGameStart.write(Nothing.NONE);
                            sys.state = GameState.IN_GAME;
                            break;
                        case IN_GAME:
                            break;
                        case GAME_OVER:
                            break;
                        case GAME_WIN:
                            break;
                    }
                }
                return true;
            }
        });

    }

    private class Sys {
        Sys() {
        }

        GameState state = GameState.INIT;

        void pollMove(float delta) {
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                playerMove.write(DirectionX.LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                playerMove.write(DirectionX.RIGHT);
        }

        void gotoTutorial(Nothing none) {
            state = GameState.TUTORIAL1;
        }


    }
}
