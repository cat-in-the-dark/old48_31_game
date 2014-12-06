package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.catinthedark.ld31.impl.common.Assets;
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

            final float OFFSET_X = 140;
            final float OFFSET_Y = 610;

            int validX1 = 470;
            int validX2 = 1000;
            int validY1 = 280;
            int validY2 = 570;
            //final Rectangle validRect = new Rectangle(470,280, 705, 470);

            @Override
            public void render(RenderShared shared) {
                if(Gdx.input.getX() >= validX1 && Gdx.input.getX() <= validX2)
                    shared.lastMouseX = Gdx.input.getX();
                else if(Gdx.input.getX() < validX1)
                    shared.lastMouseX = validX1;
                else if(Gdx.input.getX() > validX2)
                    shared.lastMouseX = validX2;

                if(Gdx.input.getY() >= validY1 && Gdx.input.getY() <= validY2)
                    shared.lastMouseY = Gdx.input.getY();
                else if(Gdx.input.getY() < validY1)
                    shared.lastMouseY = validY1;
                else if(Gdx.input.getY() > validY2)
                    shared.lastMouseY = validY2;



                batch.begin();
                batch.draw(Assets.textures.fistTopTex, shared.lastMouseX - OFFSET_X, 600);
                batch.draw(Assets.textures.fistLeftTex, 0, OFFSET_Y - shared.lastMouseY);
                batch.end();

//                System.out.println(noise.getLog());
//                for(String uniform : noise.getUniforms())
//                    System.out.println(uniform);
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
