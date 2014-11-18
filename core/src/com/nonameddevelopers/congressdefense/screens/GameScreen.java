package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
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
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;

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
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
		
		
		// Provisional, segurament se tendra que crear una clase personalizada que herede de GestureDetector
		Gdx.input.setInputProcessor(new GestureDetector(camera));

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
		

		starBoardTexture = new Texture(Gdx.files.internal("ui/starboard.png"));
		starBoard = new Sprite(starBoardTexture);
		starBoard.setSize(181, 50);
		
		coinsBoardTexture = new Texture(Gdx.files.internal("ui/coinsboard.png"));
		coinsBoard = new Sprite(coinsBoardTexture);
		coinsBoard.setSize(181, 50);
		
		voteBoardTexture = new Texture(Gdx.files.internal("ui/voteboard.png"));
		voteBoard = new Sprite(voteBoardTexture);
		voteBoard.setSize(96, 50);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		copDisp.update();
		entityManager.update(delta);

		//policeCar.update(delta, entityManager.getCopManager());

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		map.draw(game.batch);


		//policeCar.draw(game.batch);
		
		entityManager.draw(game.batch);
		
		building.draw(game.batch);

	
		starBoard.setPosition(camera.position.x, camera.position.y+camera.viewportHeight/2-50);
		starBoard.draw(game.batch);
		game.font.draw(game.batch, String.valueOf(game.score), camera.position.x+45,  
						camera.position.y+camera.viewportHeight/2-13);
		
		coinsBoard.setPosition(camera.position.x+200, camera.position.y+camera.viewportHeight/2-50);
		coinsBoard.draw(game.batch);
		game.font.draw(game.batch, String.valueOf(game.money), camera.position.x+245,  
						camera.position.y+camera.viewportHeight/2-13);
		
		voteBoard.setPosition(camera.position.x+400, camera.position.y+camera.viewportHeight/2-50);
		voteBoard.draw(game.batch);
		game.font.draw(game.batch, String.valueOf(game.life), camera.position.x+445,
						camera.position.y+camera.viewportHeight/2-13);
		
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
