package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.screens.MainMenuScreen;

public class CongressDefense extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	private Music bgMusic;
	private Sound touchSound;
	
	public boolean isMusicPlayed = true;
	public boolean isSoundOn = true;
	
	public int life;
	public int score;
	public int money;
	
	@Override
	public void create () {
		

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bg.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(0.08f);
		 
		touchSound = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
		
		money = 80;
		score = 0;
		life = 30;
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setScale(1.5f);
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
	
	public void playBackground() {
		bgMusic.play();
	}
	
	public void stopBackground() {
		bgMusic.stop();
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
	
	
}
