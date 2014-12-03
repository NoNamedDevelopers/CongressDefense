package com.nonameddevelopers.congressdefense.screens;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.ScoreScreenInputListener;
import com.nonameddevelopers.congressdefense.scoresclient.RESTConnector;
import com.nonameddevelopers.congressdefense.scoresclient.User;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;

public class ScoreScreen implements Screen {

	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 750;

	private final CongressDefense game;
	public ObjectMap<String, CheckBoxActor> buttons;

	private GameCamera camera;
	private Texture bgTexture, bgTopTexture, headerTexture;
	private Sprite bg, bgTop, header;

	private ScoreScreenInputListener inputListener;

	List<User> puntuacion;

	public ScoreScreen(final CongressDefense game) throws Exception {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

		loadMenu();

		inputListener = new ScoreScreenInputListener(game, this, camera);
		Gdx.input.setInputProcessor(new GestureDetector(inputListener) {
			@Override
			public boolean keyDown(int keycode) {

				if (keycode == Keys.BACK) {
					game.loadMenu();
				}
				return false;
			}
		});

		bgTexture = new Texture(Gdx.files.internal("scores-background.jpg"));
		bg = new Sprite(bgTexture);
		bg.setSize(WORLD_WIDTH, WORLD_HEIGHT);

		bgTopTexture = new Texture(Gdx.files.internal("scores-top.png"));
		bgTop = new Sprite(bgTopTexture);
		bgTop.setSize(WORLD_WIDTH, WORLD_HEIGHT);

		headerTexture = new Texture(Gdx.files.internal("scores-header.png"));
		header = new Sprite(headerTexture);
		header.setSize(592, 120);

		puntuacion = RESTConnector.getScores();
	}

	private void sortList() {
		Collections.sort(puntuacion,
				Collections.reverseOrder(new Comparator<User>() {
					@Override
					public int compare(User user1, User user2) {
						return user1.Score.compareTo(user2.Score);
					}
				}));
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		bg.setPosition(camera.position.x - camera.viewportWidth / 2,
				camera.position.y - camera.viewportHeight / 2);
		bg.draw(game.batch);

		sortList();

		for (int i = 0; i < puntuacion.size(); i++) {
			game.font.draw(game.batch, (i + 1) + "º- "
					+ 			   puntuacion.get(i).Username, 325, 600 - i * 30);
			game.font.draw(game.batch, puntuacion.get(i).Score.toString(), 600,
					600 - i * 30);
		}

		bgTop.setPosition(camera.position.x - camera.viewportWidth / 2,
				camera.position.y - camera.viewportHeight / 2);
		bgTop.draw(game.batch);

		header.setPosition(camera.position.x - 285, camera.position.y
				+ camera.viewportHeight / 2 - 70);
		header.draw(game.batch);
		game.font.draw(game.batch, "Name:", 325, camera.position.y
				+ camera.viewportHeight / 2 - 20);
		game.font.draw(game.batch, "Points", 600, camera.position.y
				+ camera.viewportHeight / 2 - 20);
		drawMenu(game.batch);

		game.batch.end();
	}

	private void drawMenu(SpriteBatch batch) {
		buttons.get("speaker").setPosition(
				camera.position.x - camera.viewportWidth / 2 + 10,
				camera.position.y - camera.viewportHeight / 2 + 10);

		buttons.get("sounds").setPosition(
				camera.position.x - camera.viewportWidth / 2 + 70,
				camera.position.y - camera.viewportHeight / 2 + 10);

		buttons.get("back").setPosition(
				camera.position.x + camera.viewportWidth / 2 - 60,
				camera.position.y - camera.viewportHeight / 2 + 10);

		for (CheckBoxActor button : buttons.values())
			button.draw(batch, 1f);
	}

	private void loadMenu() {
		buttons = new ObjectMap<String, CheckBoxActor>();

		buttons.put("speaker", new CheckBoxActor(
				"ui/speaker_normal_button.png", "ui/speaker_muted_button.png",
				game, CheckBoxActor.MUSIC));
		buttons.get("speaker").setSize(50, 50);

		buttons.put("sounds", new CheckBoxActor("ui/note_normal_button.png",
				"ui/note_muted_button.png", game, CheckBoxActor.SOUND));
		buttons.get("sounds").setSize(50, 50);

		buttons.put("back", new CheckBoxActor("ui/back_button.png",
				"ui/back_button.png", game, CheckBoxActor.NORMAL));
		buttons.get("back").setSize(50, 50);
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
		bgTexture.dispose();
	}

}
