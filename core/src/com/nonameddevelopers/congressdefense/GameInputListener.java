package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameInputListener implements GestureListener {

	private float x, y;
	
	public Circle meleeCopIcon, bazookaCopIcon;
	
	private GameCamera camera;
	private Vector3 touchPos;
	
	public boolean meleeCopIconPressed, wasMeleeCopIconPressed, bazookaCopIconPressed, wasBazookaCopIconPressed;
	
	public GameInputListener(GameCamera camera) {
		this.camera = camera;
		
		meleeCopIcon = new Circle();
		bazookaCopIcon = new Circle();
		
		touchPos = new Vector3();
	}
	
	public void update() {
		meleeCopIcon.set(camera.position.x-camera.viewportWidth/2+10+35, 
				camera.position.y+camera.viewportHeight/2-75+35,
				35f);

		bazookaCopIcon.set(camera.position.x-camera.viewportWidth/2+90+35, 
				camera.position.y+camera.viewportHeight/2-75+35,
				35f);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {	
		unproject(x, y);
		
		if(meleeCopIcon.contains(this.x, this.y)) 
			meleeCopIconPressed = true;		

		if(bazookaCopIcon.contains(this.x, this.y)) 
			bazookaCopIconPressed = true;		
		
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
		
		if (!isAnyIconPressed())
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
	
	
	private boolean isAnyIconPressed() {
		return meleeCopIconPressed || bazookaCopIconPressed;
	}
	
	private void freeIcons() {
		wasMeleeCopIconPressed = meleeCopIconPressed;
		meleeCopIconPressed = false;

		wasBazookaCopIconPressed = bazookaCopIconPressed;
		bazookaCopIconPressed = false;
	}


	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	private void unproject(float x, float y) {
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		this.x = touchPos.x;
		this.y = touchPos.y;	
	}
}
