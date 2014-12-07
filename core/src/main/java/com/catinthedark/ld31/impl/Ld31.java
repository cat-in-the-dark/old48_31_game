package com.catinthedark.ld31.impl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.catinthedark.ld31.impl.bots.AISystemDef;
import com.catinthedark.ld31.impl.common.Assets;
import com.catinthedark.ld31.impl.common.GameShared;
import com.catinthedark.ld31.impl.common.GameState;
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
        Pipe<Nothing> gotoTutorial = new Pipe<>();
        InputSystemDef inputSystem = new InputSystemDef();
        PhysicsSystemDef physicsSystem = new PhysicsSystemDef(gameShared);
        LevelSystemDef levelSystem = new LevelSystemDef(gameShared);
        ViewSystemDef viewSystem = new ViewSystemDef(gameShared, levelSystem.levelView());
        AISystemDef aiSystem = new AISystemDef(gameShared);

        inputSystem.playerMove.connect(physicsSystem.handlePlayerMove);
        inputSystem.daddyAttack.connect(physicsSystem.handleDaddyAttack, viewSystem
            .handleDaddyAttack);
        inputSystem.playerJump.connect(physicsSystem.handlePlayerJump);
        levelSystem.createBlock.connect(physicsSystem.onCreateBlock);

        gotoTutorial.connect(inputSystem.gotoTutorial, viewSystem.gotoTutorial);
        //gotoTutorial.connect(physicsSystem.onGameStart, levelSystem.onGameStart);
        inputSystem.gotoTutorial2.connect(viewSystem.gotoTutorial2);
        inputSystem.gotoTutorial3.connect(viewSystem.gotoTutorial3);
        inputSystem.gotoMenu.connect(viewSystem.gotoMenu);
        inputSystem.onGameStart.connect(physicsSystem.onGameStart, levelSystem.onGameStart,
            viewSystem.onGameStart);

        physicsSystem.blockDestroyed.connect(levelSystem.blockDestroyed);

        levelSystem.createJumper.connect(physicsSystem.createJumper, viewSystem.createJumper,
            aiSystem.createJumper);
        levelSystem.createWalker.connect(physicsSystem.createWalker, viewSystem.createWalker,
            aiSystem.createWalker);
        levelSystem.createShooter.connect(physicsSystem.createShooter, viewSystem.createShooter,
            aiSystem.createShooter);

        aiSystem.jumperJump.connect(physicsSystem.jumperJump);
        aiSystem.walkerGo.connect(physicsSystem.walkerWalk);
        physicsSystem.jumpersDestroyed.connect(viewSystem.jumperDestroyed, aiSystem.destroyJumper);

        Launcher.inThread(inputSystem);
        Launcher.inThread(aiSystem);
        //Launcher.inThread(physicsSystem);
        //Launcher.inThread(levelSystem);
        callbackRunner = Launcher.viaCallback(viewSystem, physicsSystem, levelSystem);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gotoTutorial.write(Nothing.NONE);
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
