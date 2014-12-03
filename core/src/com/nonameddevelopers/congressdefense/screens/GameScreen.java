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
	private static final int WORLD_WIDTH = 1000*2;
	private static final int WORLD_HEIGHT = 750*2;


	private final CongressDefense game;
	
	public Array<CopIcon> menu;
	public ObjectMap<String, CheckBoxActor> buttons;
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;

	private GameCamera camera;
	private Texture mapTexture, firstBuildingTexture, secondBuildingTexture;
	private Sprite map, firstBuilding, secondBuilding;


	
	private EntityManager entityManager;

	private CopDisplayer copDisp;
	
	private GameInputListener inputListener;

	public GameScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
		if (game.dificulty == game.EASY)
		{
			game.setLife(40);
			game.setMoney(320);
			game.setScore(0);
		}
		else if (game.dificulty == game.NORMAL)
		{
			game.setLife(25);
			game.setMoney(280);
			game.setScore(0);
		}
		else if (game.dificulty == game.HARD)
		{
			game.setLife(15);
			game.setMoney(180);
			game.setScore(0);
		}
		else
		{
			game.setLife(1);
			game.setMoney(70);
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

		mapTexture = new Texture(Gdx.files.internal("lastmap.jpg"));
		map = new Sprite(mapTexture);
		map.setPosition(0, 0);
		map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		

		firstBuildingTexture = new Texture(Gdx.files.internal("firstbuilding.png"));
		firstBuilding = new Sprite(firstBuildingTexture);
		firstBuilding.setPosition(0, 0);
		firstBuilding.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		secondBuildingTexture = new Texture(Gdx.files.internal("secondbuilding.png"));
		secondBuilding = new Sprite(secondBuildingTexture);
		secondBuilding.setPosition(0, 0);
		secondBuilding.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
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

		if (!game.isPaused) {
			copDisp.update();
			entityManager.update(delta);
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		map.draw(game.batch);

		entityManager.draw(game.batch);
		
		secondBuilding.draw(game.batch);

		drawMenu(game.batch);		
		
		game.batch.end();	
	}

	
	private void drawMenu(SpriteBatch batch) {
		for (CopIcon icon : menu) {
			icon.setScale(camera.zoom*2f);
			icon.update();
			icon.draw(batch);
		}

		game.font.setScale(camera.zoom*2);
		starBoard.setSize(181*camera.zoom*2, 50*camera.zoom*2);
		starBoard.setPosition(camera.position.x, camera.position.y+camera.effectiveViewportHeight/2-50*camera.zoom*2);
		starBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.score), camera.position.x+45*camera.zoom*2,  
					   camera.position.y+camera.effectiveViewportHeight/2-13*camera.zoom*2);

		coinsBoard.setSize(181*camera.zoom*2, 50*camera.zoom*2);
		coinsBoard.setPosition(camera.position.x+200*camera.zoom*2, camera.position.y+camera.effectiveViewportHeight/2-50*camera.zoom*2);
		coinsBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.money), camera.position.x+245*camera.zoom*2,  
					   camera.position.y+camera.effectiveViewportHeight/2-13*camera.zoom*2);

		voteBoard.setSize(96*camera.zoom*2, 50*camera.zoom*2);
		voteBoard.setPosition(camera.position.x+400*camera.zoom*2, camera.position.y+camera.effectiveViewportHeight/2-50*camera.zoom*2);
		voteBoard.draw(batch);
		game.font.draw(batch, String.valueOf(game.life), camera.position.x+445*camera.zoom*2,
					   camera.position.y+camera.effectiveViewportHeight/2-13*camera.zoom*2);
		game.font.scale(1f);
		

		buttons.get("pause").setPosition(camera.position.x-camera.effectiveViewportWidth/2+10*camera.zoom*2,  camera.position.y+camera.effectiveViewportHeight/2-60*camera.zoom*2);
		
		buttons.get("speaker").setPosition(camera.position.x-camera.effectiveViewportWidth/2+10*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
			
		buttons.get("sounds").setPosition(camera.position.x-camera.effectiveViewportWidth/2+70*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
		
		buttons.get("back").setPosition(camera.position.x+camera.effectiveViewportWidth/2-60*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
		
		for (CheckBoxActor button : buttons.values()) {
			button.setScale(camera.zoom*2);
			button.draw(batch, 1f);
		}
	}
	
	private void loadMenu() {
		menu = new Array<CopIcon>(); 		
		menu.add(new CopIcon(game, camera, 60, 0, "ui/meleecopicon2.png", CopIcon.MELEE));
		menu.add(new CopIcon(game, camera, 180, 1, "ui/bazookacopicon2.png", CopIcon.BAZOOKA));
		
		
		starBoardTexture = new Texture(Gdx.files.internal("ui/starboard.png"));
		starBoard = new Sprite(starBoardTexture);
		
		coinsBoardTexture = new Texture(Gdx.files.internal("ui/coinsboard.png"));
		coinsBoard = new Sprite(coinsBoardTexture);
		
		voteBoardTexture = new Texture(Gdx.files.internal("ui/voteboard.png"));
		voteBoard = new Sprite(voteBoardTexture);		
		
		buttons = new ObjectMap<String, CheckBoxActor>();
		
		buttons.put("pause", new CheckBoxActor("ui/pause_button.png", "ui/play_button.png", game, CheckBoxActor.PAUSE));
		buttons.put("speaker",  new CheckBoxActor("ui/note_normal_button.png", "ui/note_muted_button.png", game, CheckBoxActor.MUSIC));
		buttons.put("sounds",  new CheckBoxActor("ui/speaker_normal_button.png", "ui/speaker_muted_button.png", game, CheckBoxActor.SOUND));		
		buttons.put("back",  new CheckBoxActor("ui/back_button.png", "ui/back_button.png", game, CheckBoxActor.NORMAL));
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
