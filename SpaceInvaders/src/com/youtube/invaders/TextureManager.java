package com.youtube.invaders;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class TextureManager implements Disposable, AssetErrorListener {


	// ==========================================================================
	// Texture atlas Management

	public AssetManager assetManager; // Texture Atlas Management

	public TextureAtlas atlas;

	// Singleton Class for Texture atlas Management
	// ==========================================================================
	public static final TextureManager instance = new TextureManager();

	private TextureManager() {

	}

	public void init(AssetManager assetManager) {
		// assetManager = new AssetManager();

		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load("images/spaceinvaders.pack", TextureAtlas.class);
		assetManager.finishLoading();
		// System.out.println("Texture Atlas Correctly Loaded");
		atlas = assetManager.get("images/spaceinvaders.pack");
		// System.out.println (this.atlas.getRegions().size);

	}

	@Override
	public void error(String fileName, @SuppressWarnings("rawtypes") Class type, Throwable throwable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	// ==========================================================================

}
