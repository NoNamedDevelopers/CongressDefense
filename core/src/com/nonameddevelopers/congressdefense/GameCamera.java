package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameCamera extends OrthographicCamera implements GestureListener {

	private float worldWidth, worldHeight;
	
	
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

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (this.position.y+deltaY-viewportHeight/2 >= 0 && this.position.y+deltaY+viewportHeight/2 <= worldHeight)
			this.translate(0f,deltaY);
		this.update();
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
