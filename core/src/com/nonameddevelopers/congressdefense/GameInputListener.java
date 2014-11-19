package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.screens.GameScreen;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class GameInputListener implements GestureListener {

	private float x, y;
		
	private final CongressDefense game;
	public Array<CopIcon> menu;
	private ObjectMap<String, CheckBoxActor> buttons;
	private GameCamera camera;
	private Vector3 touchPos;
	
	public GameInputListener(final CongressDefense game, GameScreen screen, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.menu = screen.menu;
		this.buttons = screen.buttons;
		touchPos = new Vector3();
	}
	
	public void update() {
		for (CopIcon icon : menu) 
			icon.update();
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {	
		unproject(x, y);
		
		if (buttons.get("speaker").circle.contains(this.x, this.y))
			game.toggleMusic();
		if (buttons.get("sounds").circle.contains(this.x, this.y))
			game.toggleSound();
		if (buttons.get("pause").circle.contains(this.x, this.y))
			game.togglePause();
		if (buttons.get("back").circle.contains(this.x, this.y)) {
			game.loadMenu();
			return false;
		}
		
		
		for (CopIcon icon : menu) 
			icon.setPressed(this.x,this.y);
		
		return false;
	}

	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		unproject(x, y);		
		releaseIcons();
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
		releaseIcons();
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
		for (CopIcon icon : menu) 
			if (icon.isPressed())
				return true;
		return false;
	}
	
	private void releaseIcons() {
		for (CopIcon icon : menu) 
			icon.release();
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
