package com.nonameddevelopers.congressdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.GameOverInputListener;
import com.nonameddevelopers.congressdefense.gameItems.ScoreScreenInputListener;
import com.nonameddevelopers.congressdefense.scoresclient.RESTConnector;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;

public class ScoreScreen implements Screen {
		
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 750;
	
	
	private final CongressDefense game;
	public ObjectMap<String, CheckBoxActor> buttons;
	
	private GameCamera camera;
	private Texture bgTexture;
	private Sprite bg;
		
	private ScoreScreenInputListener inputListener;
	
	String puntuacion;
	
	public ScoreScreen(final CongressDefense game) {
		this.game = game;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
	
		loadMenu();
		
		inputListener = new ScoreScreenInputListener(game, this, camera);
		Gdx.input.setInputProcessor(new GestureDetector(inputListener){			
			@Override
			   public boolean keyDown(int keycode) {
			       
			        if(keycode == Keys.BACK){
			           game.loadMenu();
			        }
			        return false;
			   }
		});
	
		bgTexture = new Texture(Gdx.files.internal("gameoverbg.jpg"));
		bg = new Sprite(bgTexture);
		bg.setPosition(0, 0);
		bg.setSize(WORLD_WIDTH, WORLD_HEIGHT);	

		puntuacion = RESTConnector.getScores().toString();
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		bg.draw(game.batch);
		game.font.setScale(3f);
		game.font.draw(game.batch,puntuacion, 300,325);

		game.font.setScale(1f);
		drawMenu(game.batch);
		
		game.batch.end();				
	}
	
	
	private void drawMenu(SpriteBatch batch) {
		buttons.get("speaker").setPosition(camera.position.x-camera.viewportWidth/2+10,  camera.position.y-camera.viewportHeight/2+10);
			
		buttons.get("sounds").setPosition(camera.position.x-camera.viewportWidth/2+70,  camera.position.y-camera.viewportHeight/2+10);
		
		buttons.get("back").setPosition(camera.position.x+camera.viewportWidth/2-60,  camera.position.y-camera.viewportHeight/2+10);
		
		for (CheckBoxActor button : buttons.values()) 
			button.draw(batch, 1f);
	}
	
	private void loadMenu() {
		buttons = new ObjectMap<String, CheckBoxActor>();
			
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
		bgTexture.dispose();
	}
	
	

}
