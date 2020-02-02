package com.hitran.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hitran.BodyFatShredderGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BodyFatShredderGame.WIDTH;
		config.height = BodyFatShredderGame.HEIGHT;
		new LwjglApplication(new BodyFatShredderGame(), config);
	}
}
