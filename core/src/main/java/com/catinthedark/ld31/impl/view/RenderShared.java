package com.catinthedark.ld31.impl.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.level.LevelMatrix2;
import com.catinthedark.ld31.lib.view.Renderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by over on 06.12.14.
 */
public class RenderShared {
    public final Camera camera = new OrthographicCamera(755, 520);
    {
        camera.position.x += 755/2;
        camera.position.y += 520/2;
        camera.update();
    }
    public float delay;
    public GameShared gameShared;
    public Renderable rowAttack;
    public Renderable colAttack;
    public Renderable dedFistAttackRow;
    public Renderable dedFistAttackCol;
    public Renderable cooldownRowAnimation;
    public Renderable cooldownColAnimation;
    public LevelMatrix2.View levelView = null;
    int lastMouseX = 0;
    int lastMouseY = 0;

    public List<Integer> shootersIds = new ArrayList<>();
    public List<Integer> walkerids = new ArrayList<>();
    public List<Integer> jumpersIds = new ArrayList<>();
    public List<Integer> bottlesIds = new ArrayList<>();
}
