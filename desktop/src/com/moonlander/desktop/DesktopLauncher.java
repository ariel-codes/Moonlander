package com.moonlander.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonlander.Moonlander;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Moonbase Lander";
		config.width = 1200;
		config.height = 720;
		new LwjglApplication(new Moonlander(), config);
	}
}
