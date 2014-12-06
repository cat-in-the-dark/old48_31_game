package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.lib.view.Renderable;

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
                batch.draw(Assets.textures.runningStringTex, 0,topSurface - y - y % 32, 1366, 32);
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
                batch.draw(Assets.textures.runningStringTex, x - x % 32, 0, 32, 768);
                stateTime += renderShared.delay;
                return stateTime < ATTACK_TIME;
            }
        };
    }
}
