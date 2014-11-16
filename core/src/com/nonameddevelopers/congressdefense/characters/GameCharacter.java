package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;

public abstract class GameCharacter {
	
	protected static final short UP_LEFT = 1;
	protected static final short DOWN_LEFT = 2;
	protected static final short UP_RIGHT = 3;
	protected static final short DOWN_RIGHT = 4;	

	protected Circle boundingCircle;
	
	protected final CongressDefense game;	

	private static ObjectMap<String, Texture> textures;
	
	protected Animation ulAnimation, dlAnimation, urAnimation, drAnimation;
	protected TextureRegion currentFrame;
	protected float stateTime;
	
	private String type; 
	
	protected float x, y;
	protected short direction;
	
	static {
		textures = new ObjectMap<String, Texture>();
	}	
	
	public GameCharacter(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		this.game = game;

		this.x = x;
		this.y = y;
		this.type = type;
		
		stateTime = 0f;
		
		ulAnimation = loadAnimation("sprites/"+type+"/up_left.png", columns, rows, animationSpeed);
		dlAnimation = loadAnimation("sprites/"+type+"/down_left.png", columns, rows, animationSpeed);	
		urAnimation = loadAnimation("sprites/"+type+"/up_right.png", columns, rows, animationSpeed);	
		drAnimation = loadAnimation("sprites/"+type+"/down_right.png", columns, rows, animationSpeed);	
		

	    direction = DOWN_RIGHT;
		updateAnimation();
		boundingCircle = new Circle();
	}
	
	protected Animation loadAnimation(String src, int columns, int rows, float speed) {
		if (!textures.containsKey(type)) 
			textures.put(type, new Texture(Gdx.files.internal(src)));
		
		Texture spriteSheet = textures.get(type);
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/columns, spriteSheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[columns*rows];
		
		int index = 0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				frames[index++] = tmp[i][j];
		
		return new Animation(speed, frames);
	}	
	
	protected abstract void updateAnimation();
	
	public void draw(SpriteBatch batch) {
		batch.draw(currentFrame, x, y);
	}
		
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
}
