package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.lib.view.Layer;
import com.catinthedark.ld31.lib.view.Screen;

/**
 * Created by over on 07.12.14.
 */
public class GameOverScreen extends Screen<RenderShared> {
    public GameOverScreen(){
        super(new Layer<RenderShared>() {
            final SpriteBatch batch = new SpriteBatch();
            @Override
            public void render(RenderShared shared) {
                batch.begin();
                batch.draw(Assets.textures.gameOver, 0,0);
                Assets.fonts.hudFont.draw(batch, "Score: " + shared.gameShared.gameScore,
                        Constants.HUD_LEFT, Constants.HUD_TOP + 50);

                Assets.fonts.hudFont.draw(batch, "Morality: " + shared.gameShared.moralityLevel,
                        Constants.HUD_LEFT, Constants.HUD_TOP + 80);
                batch.end();
            }
        });
    }
}
