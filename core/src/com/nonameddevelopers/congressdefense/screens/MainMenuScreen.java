package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.ui.ImageActor;

public class MainMenuScreen extends Actor implements Screen {
	
private final CongressDefense game;
	

	private Stage stage;
	private ImageActor bgImage, startButton;
	
	
	public MainMenuScreen(final CongressDefense game) {
		this.game = game;
		
		stage = new Stage(new FillViewport(800, 480), game.batch);
		Gdx.input.setInputProcessor(stage);
		
		bgImage = new ImageActor("backgrounds/3.jpg");
		bgImage.setSize(800, 800);
		
		startButton = new ImageActor("ui/startbutton.png");
		startButton.setSize(250, 80);
		startButton.setPosition(270, 150);
		startButton.addListener(new InputListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.playTouch();
				game.setScreen(new GameScreen(game));
				stage.dispose();
				dispose();
				return true;
			}
			
		});
		
		stage.addActor(bgImage);
		stage.addActor(startButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
		
		
		/*
		
		if(Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}*/
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
	}

	@Override
	public void show() {
		game.playBackground();
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
