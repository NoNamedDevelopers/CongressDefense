package com.nonameddevelopers.congressdefense.screens;

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
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.characters.cops.PoliceVan;
import com.nonameddevelopers.congressdefense.gameItems.CopSetter;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.GameInputListener;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;

public class GameScreen implements Screen {
	private static final int WORLD_WIDTH = 1000*2;
	private static final int WORLD_HEIGHT = 750*2;


	private final CongressDefense game;
	
	public ObjectMap<String, CheckBoxActor> buttons;
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;

	private GameCamera camera;
	private Texture mapTexture, firstBuildingTexture, secondBuildingTexture;
	private Sprite map, firstBuilding, secondBuilding;
	
	private EntityManager entityManager;
	
	private GameInputListener inputListener;
	
	public CopSetter copSetter;

	public GameScreen(final CongressDefense game) {
		this.game = game;
		game.isPaused = true;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
		if (game.dificulty == CongressDefense.EASY)
		{
			game.setLife(40);
			game.setMoney(320);
			game.setScore(0);
			game.setLevel("Easy");
		}
		else if (game.dificulty == CongressDefense.NORMAL)
		{
			game.setLife(25);
			game.setMoney(280);
			game.setScore(0);
			game.setLevel("Normal");
		}
		else if (game.dificulty == CongressDefense.HARD)
		{
			game.setLife(15);
			game.setMoney(180);
			game.setScore(0);
			game.setLevel("Hard");
		}
		else
		{
			game.setLife(1);
			game.setMoney(70);
			game.setScore(0);
			game.setLevel("ChuckNorris");
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
		CopSetter.setCopManager(entityManager.getCopManager());	
		
		game.setMusic("defense.mp3");
		
		//entityManager.getCopManager().getCops().add(new PoliceVan(game, 190, 1500, 1200, 700));
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();	
		
		
		if (!game.isPaused) {
			entityManager.update(delta);
			if (copSetter!=null) {
				copSetter.update(delta, camera);
			}
			if (game.plane != null) {
				game.plane.setCamera(camera);
				game.plane.setScale(camera.zoom);
				game.plane.update(delta);
				if (game.plane.end()) {
					game.plane = null;
				}					
			}
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		map.draw(game.batch);

		entityManager.draw(game.batch);
		
		secondBuilding.draw(game.batch);


		if (copSetter!=null) {
			copSetter.draw(game.batch);
		}
		
		if (game.plane != null) {
			game.plane.draw(game.batch);
		}
		
		drawMenu(game.batch);		
		
		game.batch.end();	
	}

	
	private void drawMenu(SpriteBatch batch) {
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
		

		for (CheckBoxActor button : buttons.values()) {
			button.setScale(camera.zoom*2);
		}
		
		if (game.isPaused) {
			buttons.get("pause").setScale(camera.zoom*8f);
			buttons.get("pause").setPosition(camera.position.x-buttons.get("pause").getWidth()*camera.zoom*4f,  camera.position.y-buttons.get("pause").getHeight()*camera.zoom*4f);
			buttons.get("pause").draw(batch, 0.8f);
		}
		else {
			buttons.get("pause").setPosition(camera.position.x-camera.effectiveViewportWidth/2+10*camera.zoom*2,  camera.position.y+camera.effectiveViewportHeight/2-60*camera.zoom*2);
			buttons.get("pause").draw(batch, 1f);
		}
		
		buttons.get("speaker").setPosition(camera.position.x-camera.effectiveViewportWidth/2+10*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
		buttons.get("speaker").draw(batch, 1f);
		
		buttons.get("sounds").setPosition(camera.position.x-camera.effectiveViewportWidth/2+70*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
		buttons.get("sounds").draw(batch, 1f);
		
		buttons.get("back").setPosition(camera.position.x+camera.effectiveViewportWidth/2-60*camera.zoom*2,  camera.position.y-camera.effectiveViewportHeight/2+10*camera.zoom*2);
		buttons.get("back").draw(batch, 1f);
	}
	
	private void loadMenu() {		
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
