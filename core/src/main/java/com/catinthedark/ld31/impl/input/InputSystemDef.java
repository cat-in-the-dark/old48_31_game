package com.catinthedark.ld31.impl.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Pipe;

/**
 * Created by over on 06.12.14.
 */
public class InputSystemDef extends AbstractSystemDef{
    final Sys sys;
    public final Pipe<DirectionX> playerMove = new Pipe<>();

    public InputSystemDef(){
        sys = new Sys();
        masterDelay = 50;
        updater(sys::pollMove);

    }
    private class Sys{
         Sys(){}

        void pollMove(float delta){
            if(Gdx.input.isKeyPressed(Input.Keys.A))
                playerMove.write(DirectionX.LEFT);
            if(Gdx.input.isKeyPressed(Input.Keys.D))
                playerMove.write(DirectionX.RIGHT);
            if(Gdx.input.isKeyPressed(Input.Keys.W))
                System.out.println("jump");
        }
    }
}
