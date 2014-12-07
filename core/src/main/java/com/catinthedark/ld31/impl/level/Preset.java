package com.catinthedark.ld31.impl.level;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;

/**
 * Created by kirill on 07.12.14.
 */
public class Preset {
    public final BlockType[][] blocks;

    public Preset(int[][] blocksDef) {
        blocks = (BlockType[][]) Array.newInstance(BlockType.class, blocksDef.length, blocksDef[0]
                .length);
        for (int i = 0; i < blocksDef.length; i++)
            for (int j = 0; j < blocksDef[i].length; j++)
                blocks[i][j] = BlockType.byId(blocksDef[i][j]);

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
            }),
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
            })
    };
}
