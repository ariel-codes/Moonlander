package com.moonlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.moonlander.gameobjects.Entity;

public class WorldManager {

    SpriteBatch batch;
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    World world;
    Array<Body> bodies = new Array<Body>();
    OrthographicCamera cam;
    double currentTime;
    double t = 0.0;
    double dt = 1 / 30.0;

    public WorldManager(World world) {
        batch = new SpriteBatch();
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.currentTime = System.nanoTime();
    }

    public void render() {
        world.getBodies(bodies);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for(Body b : bodies){
            Entity e = (Entity) b.getUserData();
            if (e != null) {
                // Update the entities/sprites position and angle
                e.setPos(b.getPosition());
                // We need to convert our angle from radians to degrees
                e.setRot(MathUtils.radiansToDegrees * b.getAngle());
                e.sprite.draw(batch);
            }
        }
        batch.end();
        cam.update();
        //simulateStep();
    }

    public void simulateStep(){
        long newTime = System.nanoTime();
        double frameTime = newTime - currentTime;
        currentTime = newTime;

        while ( frameTime > 0.0 )
        {
            float deltaTime = (float) (frameTime < dt ? frameTime : dt);
            world.step((float) t, 6, 2);
            frameTime -= deltaTime;
            t += deltaTime;
        }
    }


    void renderBackground(){

    }

    public void dispose(){

    }
}