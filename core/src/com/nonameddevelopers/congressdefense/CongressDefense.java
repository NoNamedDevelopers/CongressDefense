package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.screens.MainMenuScreen;
import com.nonameddevelopers.congressdefense.screens.StartScreen;

public class CongressDefense extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public final CollissionManager collisionManager = new CollissionManager();
	
	public int life;
	public int score;
	public int money;
	
	@Override
	public void create () {
		 
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
