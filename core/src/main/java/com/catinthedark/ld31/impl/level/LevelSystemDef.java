package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.bots.Jumper;
import com.catinthedark.ld31.impl.bots.Shooter;
import com.catinthedark.ld31.impl.bots.Walker;
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
public class LevelSystemDef extends AbstractSystemDef {

    public LevelSystemDef(GameShared gameShared) {
        sys = new Sys(gameShared);
        //masterDelay = 16;
        updater(conditional(() -> sys.state == GameState.IN_GAME, sys::update));
        createBlock = new Pipe<>();
        onGameStart = serialPort(sys::onGameStart);
        blockDestroyed = asyncPort(sys::blockDestroyed);
    }

    final Sys sys;
    public final Pipe<BlockCreateReq> createBlock;
    public final Pipe<Integer> createJumper = new Pipe<>();
    public final Pipe<Integer> createWalker = new Pipe<>();
    public final Pipe<Integer> createShooter = new Pipe<>();
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

        void update(float delay) {
            if (gameShared.pPos.get().x * 32 + 500 > currentX) {
                addPreset();
            }
        }

        void onGameStart(Nothing nothing) {
            state = GameState.IN_GAME;
            matrix.reset();
            currentX = 0;
            gameShared.reset();
            Assets.audios.noise_background.setVolume(0.5f);
            Assets.audios.noise_background.setLooping(true);
            Assets.audios.noise_background.play();
        }

        public void addPreset() {
            Preset preset = Preset.presets[rand.nextInt(Preset.presets.length)];

            int presetX = currentX;

            for (BlockType[] col : preset.blocks) {
                LevelMatrix2.ColMapper mapper = matrix.nextCol();
                for (int y = 0; y < col.length; y++) {
                    BlockType block = col[y];
                    if (block == BlockType.EMPTY) {
                        continue;
                    }
                    blockIdSeq++;
                    LevelBlock lBlock = block.at(blockIdSeq, currentX, y * 32);
                    mapper.setCell(y, lBlock);
                    blockMap.put(blockIdSeq, lBlock);
                    createBlock.write(new BlockCreateReq(blockIdSeq, block, currentX / 32, y));
                }
                currentX += 32;
            }
            preset.jumpers.stream().map(j -> (Jumper) j.clone())
                .forEach(j -> {
                    j.pos.x += presetX / 32;
                    int id = gameShared.jumpers.alloc(j);
                    createJumper.write(id);
                });

            preset.walkers.stream().map(j -> (Walker) j.clone())
                .forEach(j -> {
                    j.pos.x += presetX / 32;
                    int id = gameShared.walkers.alloc(j);
                    createWalker.write(id);
                });

            preset.shooters.stream().map(j -> (Shooter) j.clone())
                .forEach(j -> {
                    j.pos.x += presetX / 32;
                    int id = gameShared.shooters.alloc(j);
                    createShooter.write(id);
                });
        }

        void blockDestroyed(Long id) {
            blockMap.get(id).status = LevelBlock.STATUS.DESTROYED;
        }


    }
}
