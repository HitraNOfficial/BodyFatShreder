package com.hitran;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hitran.util.FontGenerator;
import com.hitran.screen.MainScreen;

public class BodyFatShredderGame extends Game {
    // Constants
    public static final int WIDTH = 960;
    public static final int HEIGHT = 1600;

    private SpriteBatch batch;
    private Preferences prefs;

    @Override
    public void create() {
        batch = new SpriteBatch();

        FontGenerator.init();

        prefs = Gdx.app.getPreferences("BodyFatShredder");

        if (!prefs.contains("mode")) {
            prefs.putInteger("mode", 1);
            prefs.flush();
        }

        setScreen(new MainScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
