package com.moonlander.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by asantos07 on 10/26/16.
 */
public abstract class Entity {

    public Body body;
    public Fixture fixture;
    public Sprite sprite;

    public abstract void setPos(Vector2 pos);

    public abstract void setRot(float degrees);
    
    public abstract void updateMotion();

    public abstract void collide(Contact contact, Fixture a);
}
