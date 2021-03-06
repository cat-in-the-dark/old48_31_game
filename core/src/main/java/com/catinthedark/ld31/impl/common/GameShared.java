package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.math.Vector2;
import com.catinthedark.ld31.impl.bots.Bottle;
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
    public SharedVal<PlayerState> pState = new SharedVal<>(PlayerState.STAY);
    public  float playerAnimationTime = 0;
    public SharedVal<Vector2> cameraPosX = new SharedVal<>(new Vector2());
    public int gameScore = 0;
    public int moralityLevel = Constants.MAX_MORALITY_LEVEL;
    public SharedPool<Walker> walkers = new SharedPool<>(Walker.class, 100);
    public SharedPool<Shooter> shooters = new SharedPool<>(Shooter.class, 100);
    public SharedPool<Jumper> jumpers = new SharedPool<>(Jumper.class, 100);
    public SharedPool<Bottle> bottles = new SharedPool<>(Bottle.class, 100);

    public void reset(){
        walkers =  new SharedPool<>(Walker.class, 100);
        shooters = new SharedPool<>(Shooter.class, 100);
        jumpers = new SharedPool<>(Jumper.class, 100);
        bottles = new SharedPool<>(Bottle.class, 100);
    }


}
