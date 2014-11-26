package com.nonameddevelopers.congressdefense.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RectangleButton extends Actor{

	
	public static final short MUSIC = 0;
	public static final short SOUND = 1;
	public static final short PAUSE = 3;
	public static final short NORMAL = 4;
	
	private Texture imageOn, imageOff;
	private TextureRegion textureOn, textureOff, texture;
	public Rectangle rectangle;
	
	public boolean condition;
	
	private float width;
	private float height;
	
	public RectangleButton(String srcOn, String srcOff, float width, float height) {
		rectangle = new Rectangle();
		imageOn = new Texture(Gdx.files.internal(srcOn));
		textureOn = new TextureRegion(imageOn, imageOn.getWidth(), imageOn.getHeight());
		
		imageOff = new Texture(Gdx.files.internal(srcOff));
		textureOff = new TextureRegion(imageOff, imageOff.getWidth(), imageOff.getHeight());
		
		this.width = width;
		this.height = height;	
		condition = true;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		rectangle.set(getX(), getY(), width, height);
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
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
