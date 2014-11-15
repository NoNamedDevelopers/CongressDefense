package com.nonameddevelopers.congressdefense.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class CheckBoxActor extends Actor {
	
	public static final short MUSIC = 0;
	public static final short SOUND = 1;
	
	private Texture imageOn, imageOff;
	private TextureRegion textureOn, textureOff, texture;
	
	private CongressDefense game;
	private short type;
	private boolean condition;
	
	public CheckBoxActor(String srcOn, String srcOff, CongressDefense game, short type) {
		imageOn = new Texture(Gdx.files.internal(srcOn));
		textureOn = new TextureRegion(imageOn, imageOn.getWidth(), imageOn.getHeight());
		
		imageOff = new Texture(Gdx.files.internal(srcOff));
		textureOff = new TextureRegion(imageOff, imageOff.getWidth(), imageOff.getHeight());
		
		this.game = game;
		this.type = type;
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (type == MUSIC)
			condition = game.isMusicPlayed;
		else if (type == SOUND)
			condition = game.isSoundOn;
		else 
			condition = false;
		texture = condition ? textureOn : textureOff;
		batch.draw(texture, getX(), getY(), 
				   getOriginX(), getOriginY(),
				   getWidth(), getHeight(), 
				   getScaleX(), getScaleY(),
				   getRotation());
	}
	
	
	public void dispose() {
		imageOn.dispose();
		imageOff.dispose();
	}

}
