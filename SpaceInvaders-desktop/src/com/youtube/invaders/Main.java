package com.youtube.invaders;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
// ============================================================================
//Texture atlas Management
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
// ============================================================================
import com.youtube.invaders.MainGame;

public class Main {
	
	
	
	public static void main(String[] args) {
		// ==================================
		// Texture atlas Management
		// ==================================
		
		if(MainGame.rebuildAtlas){
			 Settings settings = new Settings();
			 settings.maxWidth = 1024;
			 settings.maxHeight = 1024;
			 settings.debug = MainGame.drawDebugOutline;  // Set  on/off  debug texture lines
			 TexturePacker2.process(settings,"assets-raw/images", "../SpaceInvaders-android/assets/images","spaceinvaders.pack");
		}
		
		
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SpaceInvaders";
		cfg.useGL20 = true;
		cfg.width = MainGame.WIDTH;
		cfg.height = MainGame.HEIGHT;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
