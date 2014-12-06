package com.catinthedark.ld31.impl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.input.InputSystemDef;
import com.catinthedark.ld31.impl.level.LevelSystemDef;
import com.catinthedark.ld31.impl.physics.PhysicsSystemDef;
import com.catinthedark.ld31.impl.view.ViewSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Pipe;
import com.catinthedark.ld31.lib.run.CallbackRunner;
import com.catinthedark.ld31.lib.run.Launcher;

public class Ld31 extends ApplicationAdapter {

    private CallbackRunner callbackRunner;


    @Override
    public void create() {
        Assets.init();

        GameShared gameShared = new GameShared();
        Pipe<Nothing> gameStart = new Pipe<>();
        InputSystemDef inputSystem = new InputSystemDef();
        PhysicsSystemDef physicsSystem = new PhysicsSystemDef(gameShared);
        LevelSystemDef levelSystem = new LevelSystemDef(gameShared);
        ViewSystemDef viewSystem = new ViewSystemDef(gameShared, levelSystem.levelView());

        inputSystem.playerMove.connect(physicsSystem.handlePlayerMove);
        inputSystem.daddyAttack.connect(physicsSystem.handleDaddyAttack, viewSystem
            .handleDaddyAttack);
        inputSystem.playerJump.connect(physicsSystem.handlePlayerJump);
        levelSystem.createBlock.connect(physicsSystem.onCreateBlock);
        inputSystem.playerJump.connect(physicsSystem.handlePlayerJump);

        gameStart.connect(physicsSystem.onGameStart, levelSystem.onGameStart);

        Launcher.inThread(inputSystem);
        Launcher.inThread(physicsSystem);
        Launcher.inThread(levelSystem);
        callbackRunner = Launcher.viaCallback(viewSystem);

        new Thread(new Runnable() {
            @Override
            public void run() {
                gameStart.write(Nothing.NONE);
            }
        }).start();
    }

    @Override
    public void render() {
        try {
            callbackRunner.step(Gdx.graphics.getDeltaTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
