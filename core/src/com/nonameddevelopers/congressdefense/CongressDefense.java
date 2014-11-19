package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.screens.MainMenuScreen;

public class CongressDefense extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	private Music bgMusic;
	private GameSound touchSound;
	
	public boolean isMusicPlayed = true;
	public boolean isSoundOn = true;
	public boolean isPaused = false;
	
	public int life;
	public int score;
	public int money;
	
	@Override
	public void create () {
		GameSound.GAME = this;

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bg.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(0.08f);
		 
		touchSound = new GameSound("sounds/punch.mp3", 330);
		
		money = 80;
		score = 0;
		life = 30;
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("opensans.fnt"));
		font.setColor(new Color(255,255,255, 1f));
		font.setScale(0.9f);
		loadMenu();
		playMusic();
	}
	
	public void loadMenu() {
		if (this.getScreen()!=null)
			this.getScreen().dispose();
		this.setScreen(new MainMenuScreen(this));		
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
	}
	
	public void playMusic() {
		if (isMusicPlayed)
			bgMusic.play();
	}
	
	public void playTouch() {
		touchSound.play(0.3f);
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
	
	
}
