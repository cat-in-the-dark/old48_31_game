package com.catinthedark.ld31.impl.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.common.AttackDirection;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.impl.common.DaddyAttack;
import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;

/**
 * Created by over on 06.12.14.
 */
public class InputSystemDef extends AbstractSystemDef {
    final Sys sys;
    public final Pipe<DirectionX> playerMove = new Pipe<>();
    public final Pipe<Nothing> playerJump = new Pipe<>();
    public final Pipe<DaddyAttack> daddyAttack = new Pipe<>();


    public InputSystemDef() {
        sys = new Sys();
        masterDelay = 50;
        updater(sys::pollMove);


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
                    if (Constants.GAME_RECT.contains(screenX, screenY + Constants.WND_HEADER_SIZE))
                        daddyAttack.write(new DaddyAttack(new Vector2(screenX, screenY + Constants.WND_HEADER_SIZE),
                            attackDir));

                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.W)
                    playerJump.write(Nothing.NONE);
                return true;
            }
        });

    }

    private class Sys {
        Sys() {
        }

        void pollMove(float delta) {
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                playerMove.write(DirectionX.LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                playerMove.write(DirectionX.RIGHT);
        }

    }
}
