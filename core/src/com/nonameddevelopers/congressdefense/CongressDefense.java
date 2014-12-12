package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.characters.Plane;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;
import com.nonameddevelopers.congressdefense.screens.MainMenuScreen;

public class CongressDefense extends Game {
	
	public final static int EASY = 0;
	public final static int NORMAL = 1;
	public final static int HARD = 2;
	public final static int CHUCK_NORRIS = 3;
	public final static int TUTORIAL= 4;
	
	public SpriteBatch batch;
	public BitmapFont font;
	private Music bgMusic;
	private GameSound touchSound;
	private GameSound coinSound;
	
	public boolean isMusicPlayed = true;
	public boolean isSoundOn = true;
	public boolean isPaused = false;
	
	public boolean isCrowdPaused = false;
	
	public int life;
	public int score;
	public int money;
	
	public int dificulty = 1;
	public Plane plane;
	public String level;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		
		GameSound.GAME = this;
		 
		touchSound = new GameSound("sounds/punch.mp3", 330);
		coinSound = new GameSound("sounds/coin.mp3", 330);
		

		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("opensans.fnt"));
		font.setColor(new Color(255,255,255, 1f));
		font.setScale(0.9f);

		this.setScreen(new MainMenuScreen(this));
	}
	
	public void loadMenu() {
		if (isPaused) {
			if (this.getScreen()!=null)
				this.getScreen().dispose();
			this.setScreen(new MainMenuScreen(this));
		}
		else {
			isPaused = true;
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		bgMusic.dispose();
		touchSound.dispose();
		coinSound.dispose();
	}
	
	public void setMusic(String src) {
		if (bgMusic == null)
			bgMusic = Gdx.audio.newMusic(Gdx.files.internal(src));
		if (isMusicPlayed) {
			bgMusic.stop();
			bgMusic = Gdx.audio.newMusic(Gdx.files.internal(src));
			bgMusic.play();
		}
		else {
			bgMusic = Gdx.audio.newMusic(Gdx.files.internal(src));
		}

		bgMusic.setLooping(true);
		bgMusic.setVolume(0.1f);
	}
	
	public void setMusicLooping(boolean condition) {
		bgMusic.setLooping(condition);
	}
	
	public void playMusic() {
		if (isMusicPlayed)
			bgMusic.play();
	}
	
	public void playTouch() {
		touchSound.play(0.3f);
	}
	
	public void playCoin() {
		coinSound.play();
	}
	
	public void toggleMusic() {
		if (isMusicPlayed)
			bgMusic.stop();
		else 
			bgMusic.play();
		isMusicPlayed = !isMusicPlayed;
	}
	
	public void toggleSound() {
		isSoundOn = !isSoundOn;
	}


	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}
	
	public void togglePause() {
		isPaused = !isPaused;
	}
	
	public void setDificulty(int dif)
	{
		this.dificulty = dif;
	}
	
	
}
