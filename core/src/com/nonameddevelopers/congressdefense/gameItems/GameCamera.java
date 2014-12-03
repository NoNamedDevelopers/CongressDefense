package com.nonameddevelopers.congressdefense.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {

	public float worldWidth, worldHeight;

	public float effectiveViewportWidth;
    public float effectiveViewportHeight;
	
	public GameCamera(float worldWidth, float worldHeight) {
		this.viewportWidth = worldWidth;
		this.viewportHeight = worldWidth * Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.effectiveViewportWidth = viewportWidth;
		this.effectiveViewportHeight = viewportHeight;
		this.position.set(this.viewportWidth/2f, this.worldHeight/2f, 0);
		this.update();
	}
	
	public void resize(int width, int height) {
		this.viewportWidth = this.worldWidth;
		this.viewportHeight = this.viewportWidth * height/width;
		this.position.set(this.viewportWidth/2f, this.worldHeight/2f, 0);
		this.update();
	}
	
	public void zoom(float zoom) {
		this.effectiveViewportWidth = viewportWidth * zoom;
		this.effectiveViewportHeight = viewportHeight * zoom;
		if (this.position.x-effectiveViewportWidth/2 <0){
			this.position.x = effectiveViewportWidth/2;			
		}
		if (effectiveViewportWidth/2+this.position.x>this.worldWidth) {
			this.position.x = worldWidth-effectiveViewportWidth/2;
		}
		if (this.position.y-effectiveViewportHeight/2 <0) {
			this.position.y = effectiveViewportHeight/2;
		}
		if (effectiveViewportHeight/2+this.position.y>this.worldHeight) {
			this.position.y = worldHeight-effectiveViewportHeight/2;
		}
		this.zoom = zoom;
	}
}
