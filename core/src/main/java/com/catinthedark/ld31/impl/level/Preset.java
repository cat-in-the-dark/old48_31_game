package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.bots.Jumper;
import com.catinthedark.ld31.impl.bots.Shooter;
import com.catinthedark.ld31.impl.bots.Walker;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill on 07.12.14.
 */
public class Preset {
    public final BlockType[][] blocks;
    public final List<Jumper> jumpers;
    public final List<Walker> walkers;
    public final List<Shooter> shooters;

    public Preset(int[][] blocksDef, List<Jumper> jumpers, List<Walker> walkers, List<Shooter>
        shooters) {
        blocks = (BlockType[][]) Array.newInstance(BlockType.class, blocksDef.length, blocksDef[0]
            .length);
        for (int i = 0; i < blocksDef.length; i++)
            for (int j = 0; j < blocksDef[i].length; j++)
                blocks[i][j] = BlockType.byId(blocksDef[i][j]);
        this.jumpers = jumpers;
        this.walkers = walkers;
        this.shooters = shooters;

    }

    public static final Preset[] presets = new Preset[]{
        new Preset(new int[][]{
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0},
            {1, 2, 0}
        }, Arrays.asList(new Jumper(4f,10f)), Arrays.asList(new Walker(6f,10f)), Arrays.asList(new Shooter(7f,10f))),
        new Preset(new int[][]{
            {1, 2, 0, 0, 0, 0, 0},
            {1, 2, 0, 0, 0, 0, 0},
            {1, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0},
            {1, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 2},
            {0, 0, 0, 0, 0, 1, 2},
            {0, 0, 0, 0, 0, 1, 2},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 2, 0, 0, 0, 0, 0},
            {1, 2, 0, 0, 0, 0, 0},
            {1, 2, 0, 0, 0, 0, 0}
        }, Arrays.asList(new Jumper(4f, 10f)), Arrays.asList(new Walker(6f,10f)), Arrays.asList(new Shooter(7f,10f)))
    };
}
