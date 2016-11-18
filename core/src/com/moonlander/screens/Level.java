package com.moonlander.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.moonlander.WorldManager;
import com.moonlander.gameobjects.Lander;

/**
 * Created by asantos07 on 10/26/16.
 */
public class Level extends GameScreen{

    World scenario;
    WorldManager manager;

    public Level(Game game) {
        super(game);
        scenario = new World(new Vector2(0, -1.6f*0.03125f), true);
        manager = new WorldManager(scenario, new Lander(scenario, new Vector2(0, 235), Lander.type.URSS));
        
        System.out.println("Level Loaded!");
    }

    @Override
    public void render(float delta) {
        manager.render(delta);
    }

    @Override
    public void dispose(){
        manager.dispose();
    }
}
