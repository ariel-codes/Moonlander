package com.moonlander.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by asantos07 on 10/26/16.
 */
public abstract class Entity {

    public Body body;
    public Fixture fixture;
    public Sprite sprite;

    public void setPos(Vector2 pos){
        sprite.setPosition(pos.x, pos.y);
    }

    public void setRot(float degrees){
        sprite.setRotation(degrees);
    }
}
