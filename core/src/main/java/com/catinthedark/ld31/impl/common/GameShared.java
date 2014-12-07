package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.bots.Jumper;
import com.catinthedark.ld31.impl.bots.Shooter;
import com.catinthedark.ld31.impl.bots.Walker;
import com.catinthedark.ld31.lib.shm.SharedPool;
import com.catinthedark.ld31.lib.shm.SharedVal;

/**
 * Created by over on 06.12.14.
 */
public class GameShared {
    public SharedVal<Vector2> pPos = new SharedVal<>(new Vector2());
    public SharedVal<Vector2> cameraPosX = new SharedVal<>(new Vector2());
    public int gameScore = 0;
    public int moralityLevel = Constants.MAX_MORALITY_LEVEL;
    public SharedPool<Walker> walkers = new SharedPool<>(Walker.class, 20);
    public SharedPool<Shooter> shooters = new SharedPool<>(Shooter.class, 20);
    public SharedPool<Jumper> jumpers = new SharedPool<>(Jumper.class, 20);

}
