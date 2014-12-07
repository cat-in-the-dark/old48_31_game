package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.catinthedark.ld31.impl.bots.Jumper;
import com.catinthedark.ld31.impl.bots.Shooter;
import com.catinthedark.ld31.impl.bots.Walker;
import com.catinthedark.ld31.impl.common.Constants;
import com.catinthedark.ld31.impl.level.BlockType;

/**
 * Created by kirill on 06.12.14.
 */
public class BodyFactory {
    public static Body createBlock(World world, BlockType type, float atX, float atY) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(atX, atY);
        Body blockBody = world.createBody(def);

        PolygonShape blockShape;
        Vector2 dots[];
        Fixture blockFixture = null;

        switch (type) {
            case BOTTOM:
            case TOP:
                blockShape = new PolygonShape();
                blockShape.setAsBox(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
                blockFixture = blockBody.createFixture(blockShape, 0);
                break;
            default:
        }

        blockFixture.setUserData(new BlockUserData());

        return blockBody;
    }

    public static Body createPlayer(World world){
        CircleShape playerShapeLow = new CircleShape();
        playerShapeLow.setRadius(Constants.PLAYER_WIDTH / 2);

        CircleShape playerShapeMid = new CircleShape();
        playerShapeMid.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeMid.setPosition(new Vector2(0, Constants.PLAYER_WIDTH));

        CircleShape playerShapeHigh = new CircleShape();
        playerShapeHigh.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeHigh.setPosition(new Vector2(0, Constants.PLAYER_WIDTH * 2));

        PolygonShape playerShapeLeft = new PolygonShape();
        playerShapeLeft.setAsBox(0.01f, Constants.PLAYER_WIDTH, new Vector2(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_WIDTH), 0);

        PolygonShape playerShapeRight = new PolygonShape();
        playerShapeRight.setAsBox(0.01f, Constants.PLAYER_WIDTH, new Vector2(-Constants.PLAYER_WIDTH / 2, Constants.PLAYER_WIDTH), 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 10);
        Body playerBody = world.createBody(bodyDef);

        PLayerUserData playerUserData = new PLayerUserData();

        Fixture pFixLow = playerBody.createFixture(playerShapeLow, 0.1f);
        pFixLow.setUserData(playerUserData);
        pFixLow.setFriction(Constants.FRICTION);

        Fixture pFixMid = playerBody.createFixture(playerShapeMid, 0.1f);
        pFixMid.setUserData(playerUserData);

        Fixture pFixHigh = playerBody.createFixture(playerShapeHigh, 0.1f);
        pFixHigh.setUserData(playerUserData);

        Fixture pFixLeft = playerBody.createFixture(playerShapeLeft, 0.1f);
        pFixLeft.setFriction(0);
        pFixLeft.setUserData(playerUserData);

        Fixture pFixRight = playerBody.createFixture(playerShapeRight, 0.1f);
        pFixRight.setFriction(0);
        pFixRight.setUserData(playerUserData);

        return  playerBody;
    }
    public static Body createJumper(World world, Jumper jumper){
        CircleShape playerShapeLow = new CircleShape();
        playerShapeLow.setRadius(Constants.PLAYER_WIDTH / 2);

        CircleShape playerShapeMid = new CircleShape();
        playerShapeMid.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeMid.setPosition(new Vector2(0, Constants.PLAYER_WIDTH));

        CircleShape playerShapeHigh = new CircleShape();
        playerShapeHigh.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeHigh.setPosition(new Vector2(0, Constants.PLAYER_WIDTH * 2));

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(jumper.pos.x, jumper.pos.y);
        Body jumperBody = world.createBody(bodyDef);

        PLayerUserData playerUserData = new PLayerUserData();

        Fixture pFixLow = jumperBody.createFixture(playerShapeLow, 0.05f);
        pFixLow.setUserData(playerUserData);
        pFixLow.setFriction(Constants.FRICTION);

        Fixture pFixMid = jumperBody.createFixture(playerShapeMid, 0.1f);
        pFixMid.setUserData(playerUserData);

        Fixture pFixHigh = jumperBody.createFixture(playerShapeHigh, 0.05f);
        pFixHigh.setUserData(playerUserData);

        return  jumperBody;
    }

    public static Body createShooter(World world, Shooter shooter){
        CircleShape playerShapeLow = new CircleShape();
        playerShapeLow.setRadius(Constants.PLAYER_WIDTH / 2);

        CircleShape playerShapeMid = new CircleShape();
        playerShapeMid.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeMid.setPosition(new Vector2(0, Constants.PLAYER_WIDTH));

        CircleShape playerShapeHigh = new CircleShape();
        playerShapeHigh.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeHigh.setPosition(new Vector2(0, Constants.PLAYER_WIDTH * 2));

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(shooter.pos.x, shooter.pos.y);
        Body shooterBody = world.createBody(bodyDef);

        PLayerUserData playerUserData = new PLayerUserData();

        Fixture pFixLow = shooterBody.createFixture(playerShapeLow, 0.05f);
        pFixLow.setUserData(playerUserData);
        pFixLow.setFriction(Constants.FRICTION);

        Fixture pFixMid = shooterBody.createFixture(playerShapeMid, 0.1f);
        pFixMid.setUserData(playerUserData);

        Fixture pFixHigh = shooterBody.createFixture(playerShapeHigh, 0.05f);
        pFixHigh.setUserData(playerUserData);

        return  shooterBody;
    }

    public static Body createWalker(World world, Walker walker){
        CircleShape playerShapeLow = new CircleShape();
        playerShapeLow.setRadius(Constants.PLAYER_WIDTH / 2);

        CircleShape playerShapeMid = new CircleShape();
        playerShapeMid.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeMid.setPosition(new Vector2(0, Constants.PLAYER_WIDTH));

        CircleShape playerShapeHigh = new CircleShape();
        playerShapeHigh.setRadius(Constants.PLAYER_WIDTH / 2);
        playerShapeHigh.setPosition(new Vector2(0, Constants.PLAYER_WIDTH * 2));

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(walker.pos.x, walker.pos.y);
        Body walkerBody = world.createBody(bodyDef);

        PLayerUserData playerUserData = new PLayerUserData();

        Fixture pFixLow = walkerBody.createFixture(playerShapeLow, 0.05f);
        pFixLow.setUserData(playerUserData);
        pFixLow.setFriction(Constants.FRICTION);

        Fixture pFixMid = walkerBody.createFixture(playerShapeMid, 0.1f);
        pFixMid.setUserData(playerUserData);

        Fixture pFixHigh = walkerBody.createFixture(playerShapeHigh, 0.05f);
        pFixHigh.setUserData(playerUserData);

        return  walkerBody;
    }
}
