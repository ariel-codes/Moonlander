package com.moonlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.moonlander.gameobjects.Entity;
import com.moonlander.gameobjects.Lander;

public class WorldManager implements InputProcessor {

    Lander player;
    SpriteBatch batch;

    static public float SCALE = 0.03125f;
    static final float WIDTH = 800;
    static final float HEIGHT = 600;

    World world;
    Array<Body> bodies = new Array<Body>();
    OrthographicCamera cam;
    StretchViewport viewport;
    private final OrthographicCamera cameraMiniMap;

    public static final int MARKER_SIZE = 2;
    public static final int MINIMAP_LEFT = 0;
    public static final int MINIMAP_RIGHT = 200;
    public static final int MINIMAP_TOP = 480;
    public static final int MINIMAP_BOTTOM = 280;

    double currentTime;
    double t = 0.0;
    double dt = 1 / 30.0;

    private final Box2DDebugRenderer debugRenderer;
    private final SpriteBatch batchMiniMap;

    public WorldManager(World world, Lander player) {
        batch = new SpriteBatch();
        this.world = world;
        this.player = player;

        Gdx.input.setInputProcessor(this);

//        float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        cam = new OrthographicCamera();
        viewport = new StretchViewport(100, 75, cam);
        viewport.apply();
        world.getBodies(bodies);
        cam.position.set(player.body.getPosition(), 5);
        debugRenderer = new Box2DDebugRenderer();
        cameraMiniMap = new OrthographicCamera(WIDTH, HEIGHT);
        cameraMiniMap.zoom = SCALE;
        batchMiniMap = new SpriteBatch();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        cam.position.set(player.body.getPosition(), 5);
        for (Body b : this.bodies) {
            Entity e = (Entity) b.getUserData();
            if (e != null) {
                // Update the entities/sprites position and angle
//                System.out.println("Tem coisa");
                e.setPos(b.getPosition());
                // We need to convert our angle from radians to degrees
                e.setRot(MathUtils.radiansToDegrees * b.getAngle());
                e.sprite.draw(batch);
                e.updateMotion();
            }
        }
        batch.end();
        renderMinimap();
        debugRenderer.render(world, cam.combined);
        simulateStep(delta);
    }

    public void simulateStep(float frameTime) {

        while (frameTime > 0.0) {
            float deltaTime = (float) (frameTime < dt ? frameTime : dt);
            world.step((float) t, 6, 2);
            frameTime -= deltaTime;
            t += deltaTime;
        }
    }

    void renderMinimap() {
        cameraMiniMap.update();
        batchMiniMap.setProjectionMatrix(cameraMiniMap.combined);
        batchMiniMap.begin();
            //TODO
        batchMiniMap.end();
    }

    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
//        System.out.println("KEY " +Keys.toString(keycode) + " DOWN");
        switch (keycode) {
            case Keys.LEFT:
                player.setLeftMove(true);
                break;
            case Keys.RIGHT:
                player.setRightMove(true);
                break;
            case Keys.UP:
                player.setUpMove(true);
                break;
            case Keys.DOWN:
                player.setDownMove(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
//        System.out.println("KEY " +Keys.toString(keycode) + " UP");
        switch (keycode) {
            case Keys.LEFT:
                player.setLeftMove(false);
                break;
            case Keys.RIGHT:
                player.setRightMove(false);
                break;
            case Keys.UP:
                player.setUpMove(false);
                break;
            case Keys.DOWN:
                player.setDownMove(false);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
