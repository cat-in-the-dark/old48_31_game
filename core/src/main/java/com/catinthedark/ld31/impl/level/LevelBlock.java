package com.catinthedark.ld31.impl.level;

/**
 * Created by kirill on 06.12.14.
 */
public class LevelBlock {
    public final long id;
    public final int x;
    public final int y;
    public final BlockType type;

    public LevelBlock(long id, BlockType type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
