package com.catinthedark.ld31.impl.view;

import com.catinthedark.ld31.lib.view.Layer;
import com.catinthedark.ld31.lib.view.Screen;

/**
 * Created by over on 06.12.14.
 */
public class GameScreen extends Screen<RenderShared> {

    public GameScreen(){
        super(new Layer<RenderShared>() {
            @Override
            public void render(RenderShared shared) {

            }
        }, new Layer<RenderShared>() {
            @Override
            public void render(RenderShared shared) {

            }
        }, new Layer<RenderShared>() {
            @Override
            public void render(RenderShared shared) {
                System.out.println("pPos.x = " + shared.gameShared.pPos.get().x);
            }
        });
    }


}
