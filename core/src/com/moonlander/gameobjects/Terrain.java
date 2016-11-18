package com.moonlander.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**i
 * Created by asantos on 14/11/16.
 */
public class Terrain {
    public Body body;
    public Fixture fixture;
    public float[] verts;

    public Terrain(float width, float maxHeight, Vector2 pos, World w){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(pos);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        this.body = w.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();

        Vector2[] edges = new Vector2[8];
        float segLength = width/(edges.length-3);
        edges[0] = new Vector2( -width/2, maxHeight/2);
        edges[1] = new Vector2( 0, 0);
        edges[2] = new Vector2( width/2, maxHeight/2);
        float x = width/2 + segLength;
        for (int i = 3; i < 8; i++) {
            edges[i] = new Vector2(x, MathUtils.random(maxHeight));
            x = x + segLength;
        }

        groundBox.set(edges);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        this.fixture = body.createFixture(fixtureDef);
        groundBox.dispose();

        verts = setVerts(edges);
    }

    public float[] setVerts (Vector2[] vertices) {
        float[] verts = new float[vertices.length * 2];
        for (int i = 0, j = 0; i < vertices.length * 2; i += 2, j++) {
            verts[i] = vertices[j].x;
            verts[i + 1] = vertices[j].y;
        }
        return verts;
    }

}
