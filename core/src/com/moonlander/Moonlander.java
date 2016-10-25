package com.moonlander;

import com.badlogic.gdx.Game;
import com.moonlander.screens.*;

public class Moonlander extends Game {
	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}
}
