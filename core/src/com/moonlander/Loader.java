package com.moonlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by strudel on 10/25/16.
 */
public class Loader {

    public Texture usa, urss;
    public Sound audio_RCS, audio_Thruster;

    public void loadAll(){
        usa = new Texture(Gdx.files.internal("usa.png"));
        urss = new Texture(Gdx.files.internal("urss.png"));
        audio_RCS = Gdx.audio.newSound(Gdx.files.internal("audio/RCS.wav"));
        audio_Thruster = Gdx.audio.newSound(Gdx.files.internal("audio/Thrusters.wav"));
    }
}
