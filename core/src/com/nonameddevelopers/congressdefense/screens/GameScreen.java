package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopDisplayer;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.GameInputListener;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class GameScreen implements Screen {
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 750;


	private final CongressDefense game;
	
	public Array<CopIcon> menu;
	public ObjectMap<String, CheckBoxActor> buttons;
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;

	private GameCamera camera;
	private Texture mapTexture, buildingTexture;
	private Sprite map, building;


	
	private EntityManager entityManager;

	private CopDisplayer copDisp;
	
	private GameInputListener inputListener;

	public GameScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
		if (game.dificulty == game.EASY)
		{
			game.setLife(80);
			game.setMoney(200);
			game.setScore(0);
		}
		else if (game.dificulty == game.NORMAL)
		{
			game.setLife(40);
			game.setMoney(100);
			game.setScore(0);
		}
		else if (game.dificulty == game.HARD)
		{
			game.setLife(20);
			game.setMoney(50);
			game.setScore(0);
		}
		else
		{
			game.setLife(1);
			game.setMoney(20);
			game.setScore(0);
		}
		

		loadMenu();
		
		inputListener = new GameInputListener(game, this, camera);
		Gdx.input.setInputProcessor(new GestureDetector(inputListener) {			
			@Override
			   public boolean keyDown(int keycode) {
			       
			        if(keycode == Keys.BACK){
			           game.loadMenu();
			        }
			        return false;
			   }
		});

		mapTexture = new Texture(Gdx.files.internal("map2.jpg"));
		map = new Sprite(mapTexture);
		map.setPosition(0, 0);
		map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		

		buildingTexture = new Texture(Gdx.files.internal("building.png"));
		building = new Sprite(buildingTexture);
		building.setPosition(0, 0);
		building.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		EntityManager.empty();
		entityManager = EntityManager.getInstance(game,camera);
		copDisp = new CopDisplayer(entityManager.getCopManager(),game, inputListener);		
		
		game.setMusic("defense.mp3");
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		inputListener.update();

		if (!game.isPaused) {
			copDisp.update();
			entityManager.update(delta);
		}

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
		
		

		buttons.get("pause").setPosition(camera.position.x-camera.viewportWidth/2+10,  camera.position.y+camera.viewportHeight/2-60);
		
		buttons.get("speaker").setPosition(camera.position.x-camera.viewportWidth/2+10,  camera.position.y-camera.viewportHeight/2+10);
			
		buttons.get("sounds").setPosition(camera.position.x-camera.viewportWidth/2+70,  camera.position.y-camera.viewportHeight/2+10);
		
		buttons.get("back").setPosition(camera.position.x+camera.viewportWidth/2-60,  camera.position.y-camera.viewportHeight/2+10);
		
		for (CheckBoxActor button : buttons.values()) 
			button.draw(batch, 1f);
	}
	
	private void loadMenu() {
		menu = new Array<CopIcon>(); 		
		menu.add(new CopIcon(game, camera, 20, camera.position.x+75, 10, "ui/meleecopicon.png", CopIcon.MELEE));
		menu.add(new CopIcon(game, camera, 50, camera.position.x-5, 10, "ui/bazookacopicon.png", CopIcon.BAZOOKA));
		
		
		starBoardTexture = new Texture(Gdx.files.internal("ui/starboard.png"));
		starBoard = new Sprite(starBoardTexture);
		starBoard.setSize(181, 50);
		
		coinsBoardTexture = new Texture(Gdx.files.internal("ui/coinsboard.png"));
		coinsBoard = new Sprite(coinsBoardTexture);
		coinsBoard.setSize(181, 50);
		
		voteBoardTexture = new Texture(Gdx.files.internal("ui/voteboard.png"));
		voteBoard = new Sprite(voteBoardTexture);
		voteBoard.setSize(96, 50);
		
		
		buttons = new ObjectMap<String, CheckBoxActor>();
		
		buttons.put("pause", new CheckBoxActor("ui/pause_button.png", "ui/play_button.png", game, CheckBoxActor.PAUSE));
		buttons.get("pause").setSize(50, 50);

		buttons.put("speaker",  new CheckBoxActor("ui/speaker_normal_button.png", "ui/speaker_muted_button.png", game, CheckBoxActor.MUSIC));
		buttons.get("speaker").setSize(50, 50);				

		buttons.put("sounds",  new CheckBoxActor("ui/note_normal_button.png", "ui/note_muted_button.png", game, CheckBoxActor.SOUND));
		buttons.get("sounds").setSize(50, 50);
		
		buttons.put("back",  new CheckBoxActor("ui/back_button.png", "ui/back_button.png", game, CheckBoxActor.NORMAL));
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
		mapTexture.dispose();
	}

	

}
