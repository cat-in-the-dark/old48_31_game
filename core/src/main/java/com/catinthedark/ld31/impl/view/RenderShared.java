package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.level.LevelMatrix;
import com.catinthedark.ld31.lib.view.Renderable;

/**
 * Created by over on 06.12.14.
 */
public class RenderShared {
    public final Camera camera = new OrthographicCamera(1366, 768);
    public float delay;
    public GameShared gameShared;
    public Renderable rowAttack;
    public Renderable colAttack;
    public Renderable dedFistAttackRow;
    public Renderable dedFistAttackCol;
    public LevelMatrix.View levelView = null;
    int lastMouseX = 0;
    int lastMouseY = 0;
}
