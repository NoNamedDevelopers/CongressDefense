package com.nonamedevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.nonamedevelopers.congressdefense.CongressDefense;
import com.nonamedevelopers.congressdefense.GameCamera;
import com.nonamedevelopers.congressdefense.characters.Crowd;
import com.nonamedevelopers.congressdefense.characters.PoliceCaller;

public class GameScreen implements Screen {
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 1000;
	
	private final CongressDefense game;

	private GameCamera camera;
	private Sprite map;
	
	private PoliceCaller shield;
	
	private Crowd crowd;
	
	public GameScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(1000,100);		
		
		map = new Sprite(new Texture(Gdx.files.internal("map.jpg")));
		map.setPosition(0, 0);
		map.setSize(WORLD_WIDTH, WORLD_HEIGHT);		
		
		crowd = new Crowd(game,10);
		
		shield = new PoliceCaller(game, 150,350, camera);
	}	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();			
		crowd.update(delta);
		shield.update(delta);
		
		shield.checkCollision(crowd);
		
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		map.draw(game.batch);
		
		game.font.draw(game.batch, "Life: "+game.life, 20, 200);
		crowd.draw(game.batch);
		shield.draw(game.batch);
		game.batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
