package com.nonameddevelopers.congressdefense.gameItems;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.screens.TutorialScreen;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;

public class TutorialInputListener implements GestureListener {

	private float x, y;

	private final CongressDefense game;
	private TutorialScreen screen;
	private ObjectMap<String, CheckBoxActor> buttons;
	private GameCamera camera;
	private Vector3 touchPos;

	public TutorialInputListener(final CongressDefense game, TutorialScreen screen,
			GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.screen = screen;
		this.buttons = screen.buttons;
		touchPos = new Vector3();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		unproject(x, y);

		
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		unproject(x, y);
		if (buttons.get("speaker").circle.contains(this.x, this.y)) {
			game.toggleMusic();
			game.playTouch();
			return false;
		}
		if (buttons.get("sounds").circle.contains(this.x, this.y)) {
			game.toggleSound();
			game.playTouch();
			return false;
		}
		if (buttons.get("back").circle.contains(this.x, this.y)) {
			game.playTouch();
			game.loadMenu();
			return false;
		}
		

		if (screen.tip.length >1) {
			game.playTouch();
			screen.nextTip();
			return false;
		}
		
		if (!game.isPaused) {
			if (screen.copSetter == null) {
				game.playTouch();
				screen.copSetter = new CopSetter(game, this.x, this.y);
			}
			else {
				screen.copSetter.click(this.x, this.y);
			}
		}

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
		
		float moveX = 0f;
		float moveY = 0f;
		if (camera.position.x - deltaX - camera.effectiveViewportWidth / 2 >= 0
			&& camera.position.x - deltaX + camera.effectiveViewportWidth / 2 <= camera.worldWidth) {
			moveX = -deltaX;
		}
		if (camera.position.y + deltaY - camera.effectiveViewportHeight / 2 >= 0
			&& camera.position.y + deltaY + camera.effectiveViewportHeight / 2 <= camera.worldHeight) {
			moveY = deltaY;
		}
			
		camera.translate(moveX, moveY);
		camera.update();

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		unproject(x, y);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		camera.zoom(MathUtils.clamp((initialDistance/distance)*camera.zoom, 0.35f, 1f));
		camera.update();
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
