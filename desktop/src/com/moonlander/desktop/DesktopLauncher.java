package com.moonlander.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonlander.Moonlander;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Moonbase Lander";
        config.width = 800;
        config.height = 600;
        config.resizable = false;
        config.vSyncEnabled = true;
        new LwjglApplication(new Moonlander(), config);
    }
}
