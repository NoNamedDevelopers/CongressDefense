package com.nonameddevelopers.congressdefense.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.screens.GameOverScreen;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;
import com.nonameddevelopers.congressdefense.ui.RectangleButton;

public class GameOverInputListener implements GestureListener {

	private float x, y;
		
	private final CongressDefense game;
	private ObjectMap<String, CheckBoxActor> buttons;
	private GameCamera camera;
	private Vector3 touchPos;
	private RectangleButton tweet;
	
	public GameOverInputListener(final CongressDefense game, GameOverScreen screen, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.buttons = screen.buttons;
		this.tweet = screen.tweet;
		touchPos = new Vector3();
	}
		
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {	
		unproject(x, y);
		
		if (buttons.get("speaker").circle.contains(this.x, this.y))
			game.toggleMusic();
		if (buttons.get("sounds").circle.contains(this.x, this.y))
			game.toggleSound();
		if (buttons.get("back").circle.contains(this.x, this.y)) {
			game.loadMenu();
			return false;
		}
		if (tweet.rectangle.contains(this.x, this.y)) {
			Gdx.net.openURI("https://twitter.com/share?text=I+have+reached+"+game.score+"+points+in+level+"+game.getLevel()+"+defending+our+awful+congress.+Join+the+fight%21&hashtags=CongressDefense&url=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.nonamedevelopers.congressdefense.android");
			return false;
		}
		return false;
	}

	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		unproject(x, y);		
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
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {	
		unproject(x, y);
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
