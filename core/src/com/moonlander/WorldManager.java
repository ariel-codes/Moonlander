package com.moonlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.moonlander.gameobjects.Entity;
import com.moonlander.gameobjects.Lander;
import com.sun.glass.ui.SystemClipboard;

public class WorldManager implements InputProcessor, ContactListener{

    Lander player;
    SpriteBatch batch;

    static public float SCALE = 0.03125f;
    static final float WIDTH = 800;
    static final float HEIGHT = 600;
    private final int worldX = 120;
    private final int worldY = 480;

    World world;
    private final Body bounds;
    private final Fixture boundsFixture;
    Array<Body> bodies = new Array<Body>();
    OrthographicCamera cam;
    StretchViewport viewport;
    private final OrthographicCamera cameraMiniMap;

    public static final float MARKER_SIZE = 2;
    public static final float MINIMAP_LEFT = -WIDTH/2;
    public static final float MINIMAP_RIGHT = -WIDTH/2 + 200;
    public static final float MINIMAP_TOP = 200;
    public static final float MINIMAP_BOTTOM = -200;

    double currentTime;
    double t = 0.0;
    double dt = 1 / 30.0;

    private final Box2DDebugRenderer debugRenderer;
    private final SpriteBatch batchMiniMap;
    private final ShapeRenderer shapeRenderer;

    public WorldManager(World world, Lander player) {
        batch = new SpriteBatch();
        this.world = world;
        this.player = player;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 0));
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        player.setPos(new Vector2(0, worldY/2));
        bounds = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(worldX/2, worldY/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.isSensor = true;
        boundsFixture = bounds.createFixture(fixtureDef);
        groundBox.dispose();

        world.setContactListener(this);
        Gdx.input.setInputProcessor(this);

        cam = new OrthographicCamera();
        viewport = new StretchViewport(100, 75, cam);
        viewport.apply();
        world.getBodies(bodies);
        cam.position.set(player.body.getPosition(), 5);
        debugRenderer = new Box2DDebugRenderer();
        cameraMiniMap = new OrthographicCamera(WIDTH, HEIGHT);
//        cameraMiniMap.zoom = 8;
        batchMiniMap = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderMinimap();
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
        batchMiniMap.draw(player.mini, -WIDTH/2, (HEIGHT/2)-100, 200, 100);
        batchMiniMap.end();
        shapeRenderer.setProjectionMatrix(cameraMiniMap.combined);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(MINIMAP_LEFT, -HEIGHT/2+100, 200, 400);
        shapeRenderer.setColor(Color.FOREST);
        Vector2 ppos = player.body.getPosition();
        ppos.x = (200f/worldX) * ppos.x - 300;
        ppos.y = (400f/worldY) * ppos.y;
        shapeRenderer.circle(ppos.x, ppos.y, 3);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeType.Line);
        for(float i = 10; i<180; i+=20)
            shapeRenderer.circle(ppos.x, ppos.y, i);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(MINIMAP_LEFT+200, -HEIGHT/2+100, 180, 400);
        shapeRenderer.rect(MINIMAP_LEFT, -HEIGHT/2, 380, 100);
        shapeRenderer.end();
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
            case Keys.P:
                System.out.println("Posição: " + player.body.getPosition().toString());
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

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == boundsFixture || b == boundsFixture)
            return;
        if(a == player.fixture)
            player.collide(contact, b);
        else if(b == player.fixture)
            player.collide(contact, a);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == player.fixture || b == player.fixture){
            if(a == boundsFixture || b == boundsFixture)
                lose();
        }
    }

    private void lose() {
        System.out.println("Perdeu!");
        System.exit(42);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
