package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopDisplayer;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.GameCamera;
import com.nonameddevelopers.congressdefense.GameInputListener;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class GameScreen implements Screen {
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 750;


	private final CongressDefense game;
	
	public Array<CopIcon> menu;
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;	

	private GameCamera camera;
	private Texture mapTexture, buildingTexture;
	private Sprite map, building;


	public CheckBoxActor musicButton, soundsButton;
	
	private EntityManager entityManager;

	private CopDisplayer copDisp;
	
	private GameInputListener inputListener;

	public GameScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

		loadMenu();
		
		inputListener = new GameInputListener(game, this, camera);
		Gdx.input.setInputProcessor(new GestureDetector(inputListener));

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
		copDisp = new CopDisplayer(entityManager.getCopManager(),game, inputListener);		
		
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		inputListener.update();
		copDisp.update();
		entityManager.update(delta);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		map.draw(game.batch);

		entityManager.draw(game.batch);
		
		building.draw(game.batch);

		drawMenu(game.batch);
		
		
		game.batch.end();
		
	}

	
	private void drawMenu(SpriteBatch batch) {
		for (CopIcon icon : menu) 
			icon.draw(batch);
		
		starBoard.setPosition(camera.position.x, camera.position.y+camera.viewportHeight/2-50);
		starBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.score), camera.position.x+45,  
					   camera.position.y+camera.viewportHeight/2-13);
		
		coinsBoard.setPosition(camera.position.x+200, camera.position.y+camera.viewportHeight/2-50);
		coinsBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.money), camera.position.x+245,  
					   camera.position.y+camera.viewportHeight/2-13);
		
		voteBoard.setPosition(camera.position.x+400, camera.position.y+camera.viewportHeight/2-50);
		voteBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.life), camera.position.x+445,
					   camera.position.y+camera.viewportHeight/2-13);
		

		musicButton.setPosition(camera.position.x-camera.viewportWidth/2+5,  camera.position.y-camera.viewportHeight/2+5);
		musicButton.draw(batch, 1f);
		soundsButton.setPosition(camera.position.x-camera.viewportWidth/2+60,  camera.position.y-camera.viewportHeight/2+5);
		soundsButton.draw(batch, 1f);
	}
	
	private void loadMenu() {
		menu = new Array<CopIcon>(); 		
		menu.add(new CopIcon(game, camera, 20, 10, 75, "ui/meleecopicon.png", CopIcon.MELEE));
		menu.add(new CopIcon(game, camera, 50, 90, 75, "ui/bazookacopicon.png", CopIcon.BAZOOKA));
		
		
		starBoardTexture = new Texture(Gdx.files.internal("ui/starboard.png"));
		starBoard = new Sprite(starBoardTexture);
		starBoard.setSize(181, 50);
		
		coinsBoardTexture = new Texture(Gdx.files.internal("ui/coinsboard.png"));
		coinsBoard = new Sprite(coinsBoardTexture);
		coinsBoard.setSize(181, 50);
		
		voteBoardTexture = new Texture(Gdx.files.internal("ui/voteboard.png"));
		voteBoard = new Sprite(voteBoardTexture);
		voteBoard.setSize(96, 50);
		
		

		musicButton = new CheckBoxActor("ui/speaker_normal_button.png", "ui/speaker_muted_button.png", game, CheckBoxActor.MUSIC);
		musicButton.setSize(50, 50);
				

		soundsButton = new CheckBoxActor("ui/note_normal_button.png", "ui/note_muted_button.png", game, CheckBoxActor.SOUND);
		soundsButton.setSize(50, 50);
		
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
