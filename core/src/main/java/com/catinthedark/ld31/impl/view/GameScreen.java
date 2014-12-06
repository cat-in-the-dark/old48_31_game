package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.ld31.impl.common.Assets;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.impl.level.LevelBlock;
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
                //System.out.println("pPos.x = " + shared.gameShared.pPos.get().x);
                final SpriteBatch batch = new SpriteBatch();
                batch.setProjectionMatrix(shared.camera.combined);
                batch.begin();
                shared.levelView.forEach(row -> {
                    for (LevelBlock block : row) {
                        if (block != null) {
                            TextureRegion tex = null;
                            switch (block.type) {
                                case NORMAL:
                                    tex = Assets.textures.runningStringTR[0][0];
                                case EMPTY:
                                    break;
                            }
                            batch.draw(tex, block.x - Constants.BLOCK_WIDTH * 32 / 2, block.y - Constants.BLOCK_HEIGHT * 32 / 2);
                        }
                    }
                });
                batch.end();
            }
        }, new Layer<RenderShared>() {
            final SpriteBatch batch = new SpriteBatch();
            @Override
            public void render(RenderShared shared) {
                batch.begin();
                batch.draw(Assets.textures.roomTex, 0, 0);
                batch.end();
            }
        }, new Layer<RenderShared>() {
            final SpriteBatch batch = new SpriteBatch();
            @Override
            public void render(RenderShared shared) {
                batch.begin();
                batch.draw(Assets.textures.fistTopTex, Gdx.input.getX() - 50, 600);
                batch.draw(Assets.textures.fistLeftTex, 50, 630 - Gdx.input.getY());
                batch.end();

                if(shared.colAttack != null){
                    boolean res = shared.colAttack.render(null);
                    if(!res)
                        shared.colAttack = null;
                }

                if(shared.rowAttack != null){
                    boolean res = shared.rowAttack.render(null);
                    if(!res)
                        shared.rowAttack = null;
                }
            }
        });
    }

    @Override
    public void beforeRender() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
