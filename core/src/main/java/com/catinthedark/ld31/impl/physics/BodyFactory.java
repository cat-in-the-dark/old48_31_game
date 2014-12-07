package com.catinthedark.ld31.impl.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
        CircleShape playerShape = new CircleShape();
        playerShape.setRadius(Constants.PLAYER_WIDTH / 2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 10);
        Body playerBody = world.createBody(bodyDef);
        Fixture pFix = playerBody.createFixture(playerShape, 0.1f);
        pFix.setUserData(new PLayerUserData());
        pFix.setFriction(Constants.FRICTION);

        return  playerBody;
    }
}
