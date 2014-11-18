package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameInputListener implements GestureListener {

	private float x, y;
	
	private Circle copIcon;
	
	private GameCamera camera;
	private Vector3 touchPos;
	
	private boolean copIconPressed = false;
	private boolean wasCopIconPressed = false;
	
	public GameInputListener(GameCamera camera) {
		this.camera = camera;
		
		copIcon = new Circle();
		

		touchPos = new Vector3();
	}
	
	public void update() {
		copIcon.set(camera.position.x-camera.viewportWidth/2+10+35, 
				camera.position.y+camera.viewportHeight/2-75+35,
				35f);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {	
		unproject(x, y);
		
		if(copIcon.contains(this.x, this.y)) 
			copIconPressed = true;		
		
		return false;
	}

	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		unproject(x, y);
		
		freeIcons();
		
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
		unproject(x, y);
		
		if (!copIconPressed)
			if (camera.position.y+deltaY-camera.viewportHeight/2 >= 0 
				&& camera.position.y+deltaY+camera.viewportHeight/2 <= camera.worldHeight) {
				camera.translate(0f,deltaY);
				camera.update();
			}
		
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {	
		unproject(x, y);
		
		freeIcons();
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private void freeIcons() {
		wasCopIconPressed = copIconPressed;
		copIconPressed = false;
	}


	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isCopIconPressed() {
		return copIconPressed;
	}
	
	public boolean wasCopIconPressed() {
		return wasCopIconPressed;
	}
	
	public Circle getCopIconCircle() {
		return copIcon;
	}
	
	private void unproject(float x, float y) {
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		this.x = touchPos.x;
		this.y = touchPos.y;	
	}
}
