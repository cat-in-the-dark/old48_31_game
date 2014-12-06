package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.catinthedark.ld31.impl.common.Assets;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.impl.level.LevelBlock;
import com.catinthedark.ld31.lib.view.Layer;
import com.catinthedark.ld31.lib.view.Screen;

import java.util.Random;

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
            final SpriteBatch attacksBatch = new SpriteBatch();
            final ShaderProgram noise = new ShaderProgram(Gdx.files.internal("noise.vert").readString(),
                Gdx.files.internal("noise.frag").readString());
            final Random rand = new Random();
            @Override
            public void render(RenderShared shared) {
                batch.begin();
                batch.draw(Assets.textures.fistTopTex, Gdx.input.getX() - 50, 600);
                batch.draw(Assets.textures.fistLeftTex, 50, 630 - Gdx.input.getY());
                batch.end();

                System.out.println(noise.getLog());
                for(String uniform : noise.getUniforms())
                    System.out.println(uniform);
                noise.begin();
                noise.setUniformf("time", rand.nextFloat());
                noise.end();
                attacksBatch.setShader(noise);

                if(shared.colAttack != null){

                    attacksBatch.begin();
                    boolean res = shared.colAttack.render(attacksBatch);
                    attacksBatch.end();
                    if(!res)
                        shared.colAttack = null;
                }

                if(shared.rowAttack != null){
                    noise.begin();
                    noise.setUniformf("factor", 600);
                    noise.end();
                    attacksBatch.begin();
                    boolean res = shared.rowAttack.render(attacksBatch);
                    attacksBatch.end();
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
