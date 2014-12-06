package com.catinthedark.ld31.impl.level;

import java.util.stream.Stream;

/**
 * Created by kirill on 06.12.14.
 */
public enum BlockType {
    NORMAL(0, true),
    EMPTY(1, false);

    public int id;
    public boolean collidable;

    BlockType (int id, boolean collidable) {
        this.id = id;
        this.collidable = collidable;
    }

    public LevelBlock at(long id, int x, int y) {
        return new LevelBlock(id, this, x, y);
    }

    public static BlockType byId(int id) {
        return Stream.of(values())
                .filter(type -> type.id == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not lookup BlockType with id: " +
                        id));
    }
}
