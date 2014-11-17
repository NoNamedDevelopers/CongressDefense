package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.GameCamera;

public class GameOverScreen implements Screen {
	
private final CongressDefense game;
	
	private GameCamera camera;
	
	public GameOverScreen(final CongressDefense game) {
		this.game = game;
		
		camera = new GameCamera(100, 0);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Game Over", 100, 150);
		game.batch.end();
		
		if(Gdx.input.isTouched()) {
			game.setScreen(new MainMenuScreen(game));
			game.setLife(30);
			game.setMoney(80);
			game.setScore(0);
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
		
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
