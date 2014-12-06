package com.catinthedark.ld31.impl.message;

import com.catinthedark.ld31.impl.level.BlockType;

/**
 * Created by kirill on 06.12.14.
 */
public class BlockCreateReq {
    public final BlockType type;
    public final float x;
    public final float y;
    public final long id;

    public BlockCreateReq(long id, BlockType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.id = id;
    }
}
