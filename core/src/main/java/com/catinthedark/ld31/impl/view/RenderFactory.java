package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.lib.view.Renderable;

import java.util.Map;

/**
 * Created by over on 06.12.14.
 */
public class RenderFactory {
    public static Renderable createRowAttack(RenderShared renderShared, int y) {
        return new Renderable() {
            float ATTACK_TIME = 0.3f;
            float stateTime = 0;
            float topSurface = 720;
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            @Override
            public boolean render(SpriteBatch batch) {
                stateTime += renderShared.delay;
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(0,topSurface - y - y % 32, 1366, 32);
//                shapeRenderer.end();
                int yy = y + (int) Constants.GAME_RECT.getY();
                batch.draw(Assets.textures.runningStringTex, 0,topSurface - yy - yy % 32, 1366, 32);
                return stateTime < ATTACK_TIME;
            }
        };
    }

    public static Renderable createColAttack(RenderShared renderShared, int x) {
        System.out.println("create col attack at " + x);
        return new Renderable() {
            float ATTACK_TIME = 0.3f;
            float stateTime = 0;
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            @Override
            public boolean render(SpriteBatch batch) {
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(x - x % 32, 0, 32, 768);
//                shapeRenderer.end();

                int xx =(x  - x % 32) + (int) Constants.GAME_RECT.getX();
                batch.draw(Assets.textures.runningStringTex, xx, 0, 32, 768);
                stateTime += renderShared.delay;
                return stateTime < ATTACK_TIME;
            }
        };
    }

    public static Renderable createDedFistRow(RenderShared renderShared, int y) {
        return new Renderable() {
            float ATTACK_TIME = 0.2f;
            float stateTime = 0;
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            final float OFFSET_X = 140;
            final float OFFSET_Y = 610;
            final float lastMouseY = renderShared.lastMouseY;

            @Override
            public boolean render(SpriteBatch batch) {
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(x - x % 32, 0, 32, 768);
//                shapeRenderer.end();
                batch.draw(Assets.textures.fistLeftTex, 0 - (int)(Math.sin(stateTime* 3.14/ATTACK_TIME) * 90), OFFSET_Y - lastMouseY);
                stateTime += renderShared.delay;
                if(stateTime > ATTACK_TIME){
                    renderShared.rowAttack = createRowAttack(renderShared, y);
                    return  false;
                }
                return  true;
            }
        };
    }
    public static Renderable createDedFistCol(RenderShared renderShared, int x) {
        return new Renderable() {
            float ATTACK_TIME = 0.3f;
            float stateTime = 0;
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            final float OFFSET_X = 140;
            final float OFFSET_Y = 610;
            final float lastMouseX = renderShared.lastMouseX;

            @Override
            public boolean render(SpriteBatch batch) {
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(x - x % 32, 0, 32, 768);
//                shapeRenderer.end();
                batch.draw(Assets.textures.fistTopTex, lastMouseX - OFFSET_X, 600 + (int)(Math.sin(stateTime* 3.14/ATTACK_TIME) * 90));

                stateTime += renderShared.delay;
                if(stateTime > ATTACK_TIME){
                    renderShared.colAttack = createColAttack(renderShared, x);
                    return false;
                }
                return true;
            }
        };
    }
}
