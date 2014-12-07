package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.lib.view.Renderable;

/**
 * Created by over on 06.12.14.
 */
public class RenderFactory {
    public static Renderable createRowAttack(RenderShared renderShared, int y) {
        return new Renderable() {
            float stateTime = 0;
            float topSurface = 727;
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            @Override
            public boolean render(SpriteBatch batch) {
                stateTime += renderShared.delay;
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(0,topSurface - y - y % 32, 1366, 32);
//                shapeRenderer.end();
                int yy = (y - y % 32) + (int) Constants.GAME_RECT.getY();
                batch.draw(Assets.textures.runningStringTex, 0, topSurface - yy, 1366, 32);
                return stateTime < Constants.ATTACK_TIME;
            }
        };
    }

    public static Renderable createColAttack(RenderShared renderShared, int x) {
        System.out.println("create col attack at " + x);
        return new Renderable() {
            float stateTime = 0;
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            @Override
            public boolean render(SpriteBatch batch) {
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(x - x % 32, 0, 32, 768);
//                shapeRenderer.end();

                int xx = (x - x % 32) + (int) Constants.GAME_RECT.getX();
                batch.draw(Assets.textures.runningStringTex, xx, 0, 32, 768);
                stateTime += renderShared.delay;
                return stateTime < Constants.ATTACK_TIME;
            }
        };
    }

    public static Renderable createDedFistRow(RenderShared renderShared, int y) {
        return new Renderable() {
            float ATTACK_TIME = Constants.ATTACK_TIME;
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
                batch.draw(Assets.textures.fistLeftTex, 0 - (int) (Math.sin(stateTime * 3.14 /
                    ATTACK_TIME) * 90), OFFSET_Y - lastMouseY);
                stateTime += renderShared.delay;
                if (stateTime > ATTACK_TIME) {
                    renderShared.rowAttack = createRowAttack(renderShared, y);
                    return false;
                }
                return true;
            }
        };
    }

    public static Renderable createDedFistCol(RenderShared renderShared, int x) {
        return new Renderable() {
            float ATTACK_TIME = Constants.ATTACK_TIME;
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
                batch.draw(Assets.textures.fistTopTex, lastMouseX - OFFSET_X, 600 + (int) (Math
                    .sin(stateTime * 3.14 / ATTACK_TIME) * 90));

                stateTime += renderShared.delay;
                if (stateTime > ATTACK_TIME) {
                    renderShared.colAttack = createColAttack(renderShared, x);
                    return false;
                }
                return true;
            }
        };
    }

    public static Renderable createCoolDownAnimationCol(RenderShared renderShared) {
        return new Renderable() {

            float stateTime = 0; // seconds
            @Override
            public boolean render(SpriteBatch batch) {
                batch.draw(Assets.textures.coolDownIndicator,
                        Constants.COOLDOWN_INDICATOR_COL_X,
                        Constants.COOLDOWN_INDICATOR_COL_Y,
                        Assets.textures.coolDownIndicator.getWidth() / 2,
                        Assets.textures.coolDownIndicator.getHeight() / 2,
                        Assets.textures.coolDownIndicator.getWidth(), Assets.textures.coolDownIndicator.getHeight(),
                        1, 1,
                        (stateTime * 1000 * 360 / Constants.COOLDOWN_COL_TIME),
                        0, 0,
                        Assets.textures.coolDownIndicator.getWidth(), Assets.textures.coolDownIndicator.getHeight(),
                        false, false
                        );
                stateTime += renderShared.delay;
                if(stateTime * 1000 > Constants.COOLDOWN_COL_TIME) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    public static Renderable createCoolDownAnimationRow(RenderShared renderShared) {
        return new Renderable() {

            float stateTime = 0; // seconds
            @Override
            public boolean render(SpriteBatch batch) {
                batch.draw(Assets.textures.coolDownIndicator,
                        Constants.COOLDOWN_INDICATOR_ROW_X,
                        Constants.COOLDOWN_INDICATOR_ROW_Y,
                        Assets.textures.coolDownIndicator.getWidth() / 2,
                        Assets.textures.coolDownIndicator.getHeight() / 2,
                        Assets.textures.coolDownIndicator.getWidth(), Assets.textures.coolDownIndicator.getHeight(),
                        1, 1,
                        (stateTime * 1000 * 360 / Constants.COOLDOWN_ROW_TIME),
                        0, 0,
                        Assets.textures.coolDownIndicator.getWidth(), Assets.textures.coolDownIndicator.getHeight(),
                        false, false
                );
                stateTime += renderShared.delay;
                if(stateTime * 1000 > Constants.COOLDOWN_ROW_TIME) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }
}
