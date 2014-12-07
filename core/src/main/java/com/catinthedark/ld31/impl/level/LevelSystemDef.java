package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.common.GameState;
import com.catinthedark.ld31.impl.message.BlockCreateReq;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.io.Port;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.catinthedark.ld31.lib.util.SysUtils.conditional;
/**
 * Created by over on 06.12.14.
 */
public class LevelSystemDef extends AbstractSystemDef{

    public LevelSystemDef(GameShared gameShared){
        sys = new Sys(gameShared);
        //masterDelay = 16;
        updater(conditional(() -> sys.state ==GameState.IN_GAME ,sys::update));
        createBlock = new Pipe<>();
        onGameStart = serialPort(sys::onGameStart);
        blockDestroyed = asyncPort(sys::blockDestroyed);
    }

    final Sys sys;
    public final Pipe<BlockCreateReq> createBlock;
    public final Port<Nothing> onGameStart;
    public final Port<Long> blockDestroyed;
    private final Random rand = new Random();
    private final Map<Long, LevelBlock> blockMap = new HashMap<>();

    public LevelMatrix2.View levelView() {
        return sys.matrix.view;
    }

    private class Sys {
        final LevelMatrix2 matrix;
        final GameShared gameShared;
        int currentX = 0;
        long blockIdSeq = 0;
        GameState state = GameState.INIT;

        Sys(GameShared gameShared) {
            this.gameShared = gameShared;
            matrix = new LevelMatrix2(10, 70, block -> {

            });
        }

        void update(float delay){
            if (gameShared.pPos.get().x * 32 + 500 > currentX) {
                addPreset();
            }
        }

        void onGameStart(Nothing nothing){
            state = GameState.IN_GAME;
            Assets.audios.noise_background.setVolume(0.5f);
            Assets.audios.noise_background.setLooping(true);
            Assets.audios.noise_background.play();
        }

        public void addPreset() {
            //Preset preset = Preset.presets[rand.nextInt(Preset.presets.length)];
            Preset preset = Preset.presets[0];

            for (BlockType[] col : preset.blocks) {
                LevelMatrix2.ColMapper mapper = matrix.nextCol();
                for (int y = 0; y < col.length; y++) {
                    BlockType block = col[y];
                    if (block == BlockType.EMPTY) {
                        continue;
                    }
                    blockIdSeq++;
                    mapper.setCell(y, block.at(blockIdSeq, currentX, y * 32));
                    createBlock.write(new BlockCreateReq(blockIdSeq, block, currentX / 32, y));
                }
                currentX += 32;
            }
        }

        void blockDestroyed(Long id){

        }


    }
}
