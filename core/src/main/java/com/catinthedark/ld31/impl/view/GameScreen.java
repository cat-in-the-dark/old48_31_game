package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
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
            final SpriteBatch batch = new SpriteBatch();
            final SpriteBatch fboBatch = new SpriteBatch();
            final FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888,
                    (int)Constants.GAME_RECT.width,
                    (int)Constants.GAME_RECT.height,
                    false);
            final ShaderProgram scanlineShader = new ShaderProgram(Gdx.files.internal("scanline.vert").readString(),
                Gdx.files.internal("scanline.frag").readString());



            @Override
            public void render(RenderShared shared) {
                fbo.begin();
                Gdx.gl.glClearColor(1.0f, 0, 0, 1.0f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                shared.camera.update();
                batch.setProjectionMatrix(shared.camera.combined);
                batch.begin();
                shared.levelView.forEach(row -> {
                    for (LevelBlock block : row) {
                        if (block != null) {
                            TextureRegion tex = null;
                            switch (block.type) {
                                case BOTTOM:
                                    tex = Assets.textures.runningStringTR[1][
                                        (block.x / 32) % Assets.textures.runningStringTR[0].length];
                                    break;
                                case TOP:
                                    tex = Assets.textures.runningStringTR[0][
                                            (block.x / 32) % Assets.textures.runningStringTR[0].length];
                                    break;
                                case EMPTY:
                                    break;
                            }
                            batch.draw(tex, block.x, block.y);
                        }
                    }
                });
                Vector2 pPos = shared.gameShared.pPos.get();
                batch.draw(Assets.textures.childTexture, pPos.x * 32 - 16, pPos.y * 32 - 16);
                batch.end();



                fbo.end();
                TextureRegion reg = new TextureRegion(fbo.getColorBufferTexture());
                reg.flip(false, true);
                scanlineShader.begin();
                scanlineShader.setUniformf("resolution", reg.getRegionWidth(), reg.getRegionHeight());
                scanlineShader.end();
                fboBatch.setShader(scanlineShader);
                fboBatch.begin();
                fboBatch.draw(reg, Constants.GAME_RECT.x, 768 - Constants.GAME_RECT.y - Constants.GAME_RECT.height);
                fboBatch.end();
            }
        }, new Layer<RenderShared>() {
            final SpriteBatch batch = new SpriteBatch();
            final SpriteBatch attacksBatch = new SpriteBatch();
            final ShaderProgram noise = new ShaderProgram(Gdx.files.internal("noise.vert").readString(),
                Gdx.files.internal("noise.frag").readString());
            final Random rand = new Random();


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
        }, new Layer<RenderShared>() {
            final SpriteBatch batch = new SpriteBatch();
            final float OFFSET_X = 140;
            final float OFFSET_Y = 610;

            @Override
            public void render(RenderShared shared) {
                batch.begin();
                batch.draw(Assets.textures.roomTex, 0, 0);
                batch.end();
                batch.begin();
                if(shared.dedFistAttackCol != null){
                    boolean res = shared.dedFistAttackCol.render(batch);
                    if(!res)
                        shared.dedFistAttackCol = null;
                }else {
                    batch.draw(Assets.textures.fistTopTex, shared.lastMouseX - OFFSET_X, 600);
                }
                if(shared.dedFistAttackRow != null){
                    boolean res = shared.dedFistAttackRow.render(batch);
                    if(!res)
                        shared.dedFistAttackRow = null;
                }else {
                    batch.draw(Assets.textures.fistLeftTex, 0, OFFSET_Y - shared.lastMouseY);
                }
                batch.end();
            }
        });
    }

    @Override
    public void beforeRender() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
