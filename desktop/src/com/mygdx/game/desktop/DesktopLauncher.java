package com.mygdx.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.packtpub.libgdx.canyonbunny.CanyonBunnyMain;

public class DesktopLauncher {
	
	private static boolean rebuildAtlas = false;
    private static boolean drawDebugOutline = false;
	
	public static void main (String[] arg) {
		
		if (rebuildAtlas) {
		      Settings settings = new Settings();
		      settings.maxWidth = 1024;
		      settings.maxHeight = 1024;
		      settings.debug = drawDebugOutline;
		      TexturePacker.process(settings, "../../desktop/assets-raw/images","images","canyonbunny");
		      TexturePacker.process(settings, "../../desktop/assets-raw/images-ui","images","canyonbunny-ui");
		}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new CanyonBunnyMain(), config);
	}
}