package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.screens.StartScreen;

public class CongressDefense extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public int life;
	
	@Override
	public void create () {
		life = 100;
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setScale(1.5f);
		this.setScreen(new StartScreen(this));
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
}
