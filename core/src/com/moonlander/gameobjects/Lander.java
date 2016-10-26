package com.moonlander.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.moonlander.Loader;

/**
 * Created by strudel on 10/25/16.
 */
public class Lander extends Entity {

    public Lander(World world, Vector2 pos, Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);


        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(pos);

        // Create our body in the world using our body definition
        this.body = world.createBody(bodyDef);
        body.setUserData(this);

        // Create a box shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        this.fixture = body.createFixture(fixtureDef);

        box.dispose();
    }


}
