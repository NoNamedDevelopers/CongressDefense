package com.nonameddevelopers.congressdefense.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {

	public float worldWidth, worldHeight;
	
	public GameCamera(float worldWidth, float worldHeight) {
		this.viewportWidth = worldWidth;
		this.viewportHeight = worldWidth * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.position.set(this.viewportWidth/2f, this.worldHeight/2f, 0);
		this.update();
	}
	
	public void resize(int width, int height) {
		this.viewportWidth = this.worldWidth;
		this.viewportHeight = this.viewportWidth * height/width;
		this.position.set(this.viewportWidth/2f, this.worldHeight/2f, 0);
		this.update();
	}
}
