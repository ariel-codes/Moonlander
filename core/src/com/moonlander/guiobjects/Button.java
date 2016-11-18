package com.moonlander.guiobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by asantos07 on 11/18/16.
 */
public abstract class Button {

    public final PolygonShape shape;
    public final Color color_idle, color_active;
    public final Vector2 position;

    public Button(PolygonShape shape, Color idle, Color active, Vector2 pos){
        this.shape = shape;
        this.color_idle = idle;
        this.color_active = active;
        this.position = pos;
    }

    abstract void onClick();
}
