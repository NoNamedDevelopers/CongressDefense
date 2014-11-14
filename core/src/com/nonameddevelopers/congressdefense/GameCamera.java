package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {
	
	private float width;
	private int offsetY;;
	
	public GameCamera(float width, int offsetY) {
		super(width, width * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
		this.width = width;
		this.offsetY = offsetY;
		this.position.set(this.viewportWidth/2f, this.viewportHeight/2f, 0);
		this.update();
	}
	
	public void resize(int width, int height) {
		this.viewportWidth = this.width;
		this.viewportHeight = this.viewportWidth * height/width;
		this.position.set(this.viewportWidth/2f, this.viewportHeight/2f+offsetY, 0);
		this.update();
	}

}
