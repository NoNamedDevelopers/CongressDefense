package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopDisplayer;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.GameCamera;
import com.nonameddevelopers.congressdefense.TopMenu;
import com.nonameddevelopers.congressdefense.characters.BazookaCop;
import com.nonameddevelopers.congressdefense.characters.PoliceVan;

public class GameScreen implements Screen {
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 750;

	private final CongressDefense game;

	private GameCamera camera;
	private Texture mapTexture, buildingTexture;
	private Sprite map, building;

	private EntityManager entityManager;

	private CopDisplayer copDisp;
	private PoliceVan policeCar;
	private PoliceVan policeCar2;
	
	private TopMenu topMenu;

	public GameScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, 100);

		mapTexture = new Texture(Gdx.files.internal("map2.jpg"));
		map = new Sprite(mapTexture);
		map.setPosition(0, 0);
		map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		

		buildingTexture = new Texture(Gdx.files.internal("building.png"));
		building = new Sprite(buildingTexture);
		building.setPosition(0, 0);
		building.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		entityManager = EntityManager.getInstance(game,camera);
		entityManager.setCamera(camera);
		copDisp = new CopDisplayer(entityManager.getCopManager(),game, camera);
		
		policeCar = new PoliceVan(game, 150, 350, camera);
		policeCar2 = new PoliceVan(game, 200, 180, camera);
		
		entityManager.getCopManager().addCop(new BazookaCop(game, 300, 300));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isKeyPressed(Input.Keys.N))
			camera.zoom -= 0.002f;
		
		camera.update();
		copDisp.update();

		policeCar.update(delta, entityManager.getCopManager());

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		map.draw(game.batch);


		policeCar.draw(game.batch);
		policeCar2.draw(game.batch);
		entityManager.update(delta, game.batch);
		
		building.draw(game.batch);

		game.font.draw(game.batch, "Life: " + game.life, 20, 200);
		game.font.draw(game.batch, "Money: " + game.money, 20, 240);
		game.font.draw(game.batch, "Score: " + game.score, 20, 280);
		
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
		mapTexture.dispose();
	}

	

}
