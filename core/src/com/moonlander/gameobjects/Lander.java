package com.moonlander.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.moonlander.Loader;
import com.moonlander.WorldManager;

/**
 * Created by strudel on 10/25/16.
 */
public class Lander extends Entity {

    private boolean rightMove;
    private boolean leftMove;
    private boolean upMove;
    private boolean downMove;

    private Sound RCS, thruster;
    public Sprite mini;

    public enum type {
        USA, URSS;
    }

    private Vector2 linearVel;
    private float angularVel;

    public Lander(World world, Vector2 pos, type t) {
        RCS = Loader.audio_RCS;
        thruster = Loader.audio_Thruster;
        switch (t) {
            case USA:
                this.sprite = new Sprite(Loader.usa);
                this.mini = new Sprite(Loader.flag_usa);
                break;
            case URSS:
                this.sprite = new Sprite(Loader.urss);
                this.mini = new Sprite(Loader.flag_urss);
        }
        this.sprite.setSize(sprite.getWidth() * WorldManager.SCALE, sprite.getHeight() * WorldManager.SCALE);
        this.sprite.setOriginCenter();
        this.mini.setSize(sprite.getWidth(), sprite.getHeight());
        this.mini.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(pos);

        // Create our body in the world using our body definition
        this.body = world.createBody(bodyDef);
        body.setUserData(this);
        body.getMassData().center.set(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setAngularDamping(1);
        body.setLinearDamping(1);

        this.leftMove = false;
        this.rightMove = false;
        this.upMove = false;
        this.downMove = false;
        angularVel = 0;
        linearVel = new Vector2();

        // Create a box shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 2f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        // Create our fixture and attach it to the body
        this.fixture = body.createFixture(fixtureDef);

        box.dispose();
    }

    @Override
    public void setPos(Vector2 pos) {
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
    }

    @Override
    public void setRot(float degrees) {
        sprite.setRotation(degrees);
    }

    public void setLeftMove(boolean b) {
        if (rightMove && b) {
            rightMove = false;
        }
        if(b)
            RCS.loop(0.1f, 0.2f, 1);
        else
            RCS.stop();
        leftMove = b;
    }

    public void setRightMove(boolean b) {
        if (leftMove && b) {
            leftMove = false;
        }
        if(b)
            RCS.loop(0.1f, 0.2f, 1);
        else
            RCS.stop();
        rightMove = b;
    }

    public void setUpMove(boolean b) {
        if (downMove && b) {
            downMove = false;
        }
        if(b)
            thruster.loop(1.5f);
        else
            thruster.stop();
        upMove = b;
    }

    public void setDownMove(boolean b) {
        if (upMove && b) {
            upMove = false;
        }
        if(b)
            RCS.loop(0.1f, 0.2f, 1);
        else
            RCS.stop();
        downMove = b;
    }

    @Override
    public void updateMotion() {
        if (leftMove) {
            angularVel += 3f * Gdx.graphics.getDeltaTime();

        }
        if (rightMove) {
            angularVel -= 3f * Gdx.graphics.getDeltaTime();
        }
        if (upMove) {
            linearVel.y += 15 * cos(body.getAngle()) * Gdx.graphics.getDeltaTime();
            linearVel.x -= 15 * sin(body.getAngle()) * Gdx.graphics.getDeltaTime();
        }
        if (downMove) {
            linearVel.y -= 3f * cos(body.getAngle()) * Gdx.graphics.getDeltaTime();
            linearVel.x += 3f * sin(body.getAngle()) * Gdx.graphics.getDeltaTime();
        }
        body.applyTorque(angularVel * Gdx.graphics.getDeltaTime(), true);
        body.applyLinearImpulse(linearVel, body.getPosition(), upMove);
    }

}
