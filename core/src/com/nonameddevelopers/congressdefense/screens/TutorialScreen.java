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
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.gameItems.CopSetter;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.TutorialInputListener;
import com.nonameddevelopers.congressdefense.ui.CheckBoxActor;

public class TutorialScreen implements Screen {
	private static final int WORLD_WIDTH = 1000*2;
	private static final int WORLD_HEIGHT = 750*2;


	private final CongressDefense game;
	
	public ObjectMap<String, CheckBoxActor> buttons;
	
	private Texture starBoardTexture, coinsBoardTexture, voteBoardTexture;
	private Sprite starBoard, coinsBoard, voteBoard;

	private GameCamera camera;
	private Texture mapTexture, firstBuildingTexture, secondBuildingTexture, helpTexture;
	private Sprite map, firstBuilding, secondBuilding, helpSprite;
	
	private EntityManager entityManager;
	
	private TutorialInputListener inputListener;
	
	public CopSetter copSetter;
	
	public String[] tip;
	private Array<String[]> tips;
	private int tipIndex;

	public TutorialScreen(final CongressDefense game) {
		this.game = game;
		game.isPaused = true;
		camera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
		
		game.setDificulty(CongressDefense.TUTORIAL);
		game.setLife(2);
		game.setMoney(60);
		game.setScore(0);
		game.setLevel("Tutorial");
		
		game.isCrowdPaused = true;
		
		setUpTips();
		tip = tips.first();
		
		loadMenu();
		loadHelpFrame();
		
		inputListener = new TutorialInputListener(game, this, camera);
		Gdx.input.setInputProcessor(new GestureDetector(inputListener) {			
			@Override
			   public boolean keyDown(int keycode) {
			       
			        if(keycode == Keys.BACK){
			           game.loadMenu();
			        }
			        return false;
			   }
		});

		mapTexture = new Texture(Gdx.files.internal("tutorialmap.jpg"));
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
		
		if (tip.length>1) {
			drawTip(game.batch, tip);
		}
		
		drawMenu(game.batch);		
		
		game.batch.end();	
		
		
		switch(tipIndex) {
		case 3:
			game.isPaused = false;
			break;
		case 5:
			game.isCrowdPaused = false;
			break;
		case 7:
		case 9:
			game.loadMenu();
			break;
		}
		
		if (game.score>0) {
			game.isPaused = true;
			tipIndex = 5;
			nextTip();
		}
		
		if (game.life<2) {
			game.isPaused = true;
			tipIndex = 7;
			nextTip();
		}
		
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
		
	
		buttons.put("speaker",  new CheckBoxActor("ui/note_normal_button.png", "ui/note_muted_button.png", game, CheckBoxActor.MUSIC));
		buttons.put("sounds",  new CheckBoxActor("ui/speaker_normal_button.png", "ui/speaker_muted_button.png", game, CheckBoxActor.SOUND));		
		buttons.put("back",  new CheckBoxActor("ui/back_button.png", "ui/back_button.png", game, CheckBoxActor.NORMAL));
	}
	
	private void loadHelpFrame() {
		helpTexture = new Texture(Gdx.files.internal("ui/frametutorial.png"));
		helpSprite = new Sprite(helpTexture);
	}
	
	private void drawTip(SpriteBatch batch, String[] lines) {
		helpSprite.setSize(1966*0.8f*camera.zoom, 855*0.8f*camera.zoom);
		helpSprite.setCenter(camera.position.x, camera.position.y);
		helpSprite.draw(batch);		
		game.font.setScale(camera.zoom*2f);
		for (int i=0; i<lines.length ; i++) {
			if (i!=0) {
				game.font.setScale(camera.zoom*1.5f);
			}
			game.font.draw(batch, lines[i], camera.position.x-470*0.8f*camera.zoom,  camera.position.y+(200-i*75)*0.8f*camera.zoom);
		}
		game.font.setScale(camera.zoom*1.2f);
		game.font.draw(batch,"Tap to continue", camera.position.x+550*0.8f*camera.zoom,  camera.position.y-320*0.8f*camera.zoom);
		game.font.setScale(1f);
	}
	
	private void setUpTips() {
		tips = new Array<String[]>();
		tips.add(new String[]{"Welcome!","","Right now we are in trouble, could you help us?"});
		tips.add(new String[]{"This is Spain...","","Elections are closer and the government is", "stealing time and money from spanish people.", "We agree with masses, but our job is to take orders", "from those thieves."});
		tips.add(new String[]{"How do you could help?","","You can place us by tapping the screen. Then you", "will choose one of us, if you have money enough,", "and the cost will be withdrawn from your budget.","", "Let's start placing a cop in the mark!"});
		tips.add(new String[]{""});
		tips.add(new String[]{"Well done!","","Let's see what happens.", "", "You can zoom for more detail."});
		tips.add(new String[]{""});
		tips.add(new String[]{"Great job!","","Now you can join us in our defense of the congress.", "The next time, you will be able to buy other cops","and cool things, but you will be alone. Be careful!","", "See you soon :)"});
		tips.add(new String[]{""});
		tips.add(new String[]{"Ouch!","","One protester is getting into the congress", "and the government is losing votes. Place the cop", "better the next time :(, otherwise a better country", "would come."});
		tips.add(new String[]{""});
	}
	
	public void nextTip() {
		if (++tipIndex<tips.size) {
			tip = tips.get(tipIndex);
		}
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
		helpTexture.dispose();
	}

	

}
