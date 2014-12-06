package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.message.BlockCreateReq;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

/**
 * Created by over on 06.12.14.
 */
public class LevelSystemDef extends AbstractSystemDef{

    public LevelSystemDef(GameShared gameShared){
        sys = new Sys(gameShared);
        masterDelay = 16;
        updater(sys::update);
        createBlock = new Pipe<>();
    }

    final Sys sys;
    public final Pipe<BlockCreateReq> createBlock;

    private class Sys {
        final LevelMatrix matrix;
        final GameShared gameShared;
        int currentX = 0;
        long blockIdSeq = 0;

        Sys(GameShared gameShared) {
            this.gameShared = gameShared;
            matrix = new LevelMatrix(10, 70, block -> {

            });
        }

        void update(float delay){
            if (gameShared.pPos.get().x * 32 + 500 > currentX) {
                addPreset();
            }
        }

        public void addPreset() {
            System.out.println("add preset!");

            LevelMatrix.ColMapper mapper = matrix.nextCol();
            BlockType[] col = new BlockType[]{BlockType.NORMAL, BlockType.NORMAL, BlockType.EMPTY};
            for (int y = 0; y < col.length; y++) {
                BlockType block = col[y];
                if (block == BlockType.EMPTY) {
                    continue;
                }
                mapper.setCell(y, block.at(blockIdSeq, currentX, y * 32));
                createBlock.write(new BlockCreateReq(blockIdSeq, block, currentX / 32, y));
            }
            currentX += 32;
        }

    }
}
