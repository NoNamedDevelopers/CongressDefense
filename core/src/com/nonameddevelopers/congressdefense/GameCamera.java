package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {
	
	private int offsetY;
	
	public GameCamera(int width, int offsetY) {
		super(width, width * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
		this.offsetY = offsetY;
		this.position.set(this.viewportWidth/2f, this.viewportHeight/2f, 0);
		this.update();
	}
	
	public void resize(int width, int height) {
		this.viewportWidth = 1000f;
		this.viewportHeight = this.viewportWidth * height/width;
		this.position.set(this.viewportWidth/2f, this.viewportHeight/2f+offsetY, 0);
		this.update();
	}

}
