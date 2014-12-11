package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;
import com.nonameddevelopers.congressdefense.ui.ImageActor;

public class MainMenuScreen extends Actor implements Screen {

	private final CongressDefense game;

	private Stage stage;
	private ImageActor bgImage, startButton, scoresButton, optionsButton,
			rateButton;
	private CheckBoxActor musicButton, soundsButton;

	public MainMenuScreen(final CongressDefense game) {
		this.game = game;
		stage = new Stage(new FillViewport(1000, 750), game.batch) {
			@Override
			public boolean keyDown(int keycode) {

				if (keycode == Keys.BACK) {
					Gdx.app.exit();
				}
				return false;
			}
		};
		Gdx.input.setInputProcessor(stage);

		bgImage = new ImageActor("backgrounds/cityintro.jpg");
		bgImage.setSize(1000, 750);

		musicButtonAction(game);

		soundsButtonAction(game);

		startButtonAction(game);

		scoresButtonAction(game);

		optionsButtonAction();
	

		rateButtonAction();

		stage.addActor(bgImage);
		stage.addActor(musicButton);
		stage.addActor(soundsButton);
		stage.addActor(startButton);
		stage.addActor(scoresButton);
		stage.addActor(rateButton);
		stage.addActor(optionsButton);
	}

	private void rateButtonAction() {
		rateButton = new ImageActor("ui/rate_button.png");
		rateButton.setSize(250, 80);
		rateButton.setPosition(70, 350);
		rateButton.addListener(new InputListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.playTouch();
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.nonamedevelopers.congressdefense.android&hl=es");
				stage.dispose();
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
			
			
		});
		
		
		
		
	}

	private void optionsButtonAction() {
		optionsButton = new ImageActor("ui/options_button.png");
		optionsButton.setSize(250, 80);
		optionsButton.setPosition(350, 350);
	}

	private void scoresButtonAction(final CongressDefense game) {
		scoresButton = new ImageActor("ui/scores_button.png");
		scoresButton.setSize(250, 80);
		scoresButton.setPosition(350, 500);
		scoresButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.playTouch();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				stage.dispose();
				try {
					game.setScreen(new ScoreScreen(game));
				} catch (Exception e) {
					
				}
				dispose();
			}

		});
	}

	private void startButtonAction(final CongressDefense game) {
		startButton = new ImageActor("ui/start_button.png");
		startButton.setSize(250, 80);
		startButton.setPosition(70, 500);
		startButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.playTouch();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				stage.dispose();
				game.setScreen(new SelectLevelScreen(game));
				dispose();
			}

		});
	}

	private void soundsButtonAction(final CongressDefense game) {
		soundsButton = new CheckBoxActor("ui/note_normal_button.png",
				"ui/note_muted_button.png", game, CheckBoxActor.SOUND);

		soundsButton = new CheckBoxActor("ui/speaker_normal_button.png", "ui/speaker_muted_button.png", game, CheckBoxActor.SOUND);
		soundsButton.setSize(60, 60);
		soundsButton.setPosition(140, 100);
		soundsButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.playTouch();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.toggleSound();
				dispose();
			}

		});
	}

	private void musicButtonAction(final CongressDefense game) {
		musicButton = new CheckBoxActor("ui/speaker_normal_button.png",
				"ui/speaker_muted_button.png", game, CheckBoxActor.MUSIC);
		
		
		musicButton = new CheckBoxActor("ui/note_normal_button.png", "ui/note_muted_button.png", game, CheckBoxActor.MUSIC);
		musicButton.setSize(60, 60);
		musicButton.setPosition(60, 100);
		musicButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.playTouch();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.toggleMusic();
				dispose();
			}

		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);

	}

	@Override
	public void show() {
		game.setMusic("bg.mp3");
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
