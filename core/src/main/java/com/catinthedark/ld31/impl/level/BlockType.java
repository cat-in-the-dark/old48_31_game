package com.catinthedark.ld31.impl.level;

import java.util.stream.Stream;

/**
 * Created by kirill on 06.12.14.
 */
public enum BlockType {
    EMPTY(0, false),
    BOTTOM(1, true),
    TOP(2, true);

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
