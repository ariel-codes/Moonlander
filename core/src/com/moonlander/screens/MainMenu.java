package com.moonlander.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.moonlander.guiobjects.Button;

/**
 * Created by asantos07 on 10/25/16.
 */
public class MainMenu extends GameScreen{

    Button[] buttons;

    public MainMenu(Game game) {
        super(game);
        buttons = new Button[3];
    }

    @Override
    public void render(float delta) {
        for(Button b : buttons){
                      
        }
    }
}
