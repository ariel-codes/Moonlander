package com.moonlander;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.cam.position.set(world.lander.position.x, world.lander.position.y, 0);
        this.batch = batch;
    }

    public void render() {
        cam.position.y = world.lander.position.y;
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderBackground();
        renderObjects();
    }

    void renderObjects(){

    }

    void renderBackground(){

    }
}