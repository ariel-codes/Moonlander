package com.moonlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.moonlander.gameobjects.Entity;

public class WorldManager {

    Entity player;
    SpriteBatch batch;
    static final float _WIDTH = 250;
    static final float _HEIGHT = 125;
    World world;
    Array<Body> bodies = new Array<Body>();
    OrthographicCamera cam;
    StretchViewport viewport;
    double currentTime;
    double t = 0.0;
    double dt = 1 / 30.0;

    public WorldManager(World world, Entity player) {
        batch = new SpriteBatch();
        this.world = world;
        this.player = player;

        float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        cam = new OrthographicCamera();
        viewport = new StretchViewport(1200,720,cam);
        viewport.apply();
        world.getBodies(bodies);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for(Body b : this.bodies){
            Entity e = (Entity) b.getUserData();
            if (e != null) {
                // Update the entities/sprites position and angle
//                System.out.println("Tem coisa");
                e.setPos(b.getPosition());
                // We need to convert our angle from radians to degrees
                e.setRot(MathUtils.radiansToDegrees * b.getAngle());
                e.sprite.draw(batch);
            }
        }
        batch.end();
        cam.position.set(player.body.getPosition(), 0);
        simulateStep(delta);
    }

    public void simulateStep(float frameTime){

        while ( frameTime > 0.0 )
        {
            float deltaTime = (float) (frameTime < dt ? frameTime : dt);
            world.step((float) t, 6, 2);
            frameTime -= deltaTime;
            t += deltaTime;
            System.out.println(frameTime);
        }
    }


    void renderBackground(){

    }

    public void dispose(){

    }
}