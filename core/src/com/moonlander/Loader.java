package com.moonlander;

import com.badlogic.gdx.Gdx;

/**
 * Created by strudel on 10/25/16.
 */
public class Loader {
    public void loadAll(){
        audio_RCS = Gdx.audio.newSound(Gdx.files.internal("audio/RCS.wav"));
        audio_Thruster = Gdx.audio.newSound(Gdx.files.internal("audio/Thrusters.wav"));
    }
}
