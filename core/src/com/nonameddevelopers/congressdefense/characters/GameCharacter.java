package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	protected static final short UP = 5;
	protected static final short DOWN = 6;
	protected static final short LEFT = 7;
	protected static final short RIGHT = 8;

	protected Circle boundingCircle;	
	
	protected final CongressDefense game;	

	private static ObjectMap<String, Texture> textures;
	
	protected Animation uAnimation, dAnimation, lAnimation, rAnimation, ulAnimation, dlAnimation, urAnimation, drAnimation;
	protected TextureRegion currentFrame;
	protected float stateTime;

	protected Animation currentAnimation;
	
	private Color tint = Color.WHITE;
	
	public String type; 
	
	protected float x;
	protected float y;
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

		uAnimation = loadAnimation("sprites/"+type+"/up.png", columns, rows, animationSpeed);
		dAnimation = loadAnimation("sprites/"+type+"/down.png", columns, rows, animationSpeed);
		lAnimation = loadAnimation("sprites/"+type+"/left.png", columns, rows, animationSpeed);
		rAnimation = loadAnimation("sprites/"+type+"/right.png", columns, rows, animationSpeed);
		ulAnimation = loadAnimation("sprites/"+type+"/up_left.png", columns, rows, animationSpeed);
		dlAnimation = loadAnimation("sprites/"+type+"/down_left.png", columns, rows, animationSpeed);	
		urAnimation = loadAnimation("sprites/"+type+"/up_right.png", columns, rows, animationSpeed);	
		drAnimation = loadAnimation("sprites/"+type+"/down_right.png", columns, rows, animationSpeed);	
		
		
	    direction = DOWN;
		updateAnimation();
		boundingCircle = new Circle();		
	}
	
	protected Animation loadAnimation(String src, int columns, int rows, float speed) {
		if (!textures.containsKey(src)) 
			textures.put(src, new Texture(Gdx.files.internal(src)));
		
		
		Texture spriteSheet = textures.get(src);
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/columns, spriteSheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[columns*rows];
		
		int index = 0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				frames[index++] = tmp[i][j];
		
		return new Animation(speed, frames);
	}	

	protected void updateAnimation() {
		switch (direction) {
		case UP:
			currentAnimation = uAnimation;
			break;
		case DOWN:
			currentAnimation = dAnimation;
			break;
		case LEFT:
			currentAnimation = lAnimation;
			break;
		case RIGHT:
			currentAnimation = rAnimation;
			break;
		case UP_RIGHT:
			currentAnimation = urAnimation;
			break;
		case DOWN_RIGHT:
			currentAnimation = drAnimation;
			break;
		case UP_LEFT:
			currentAnimation = ulAnimation;
			break;
		case DOWN_LEFT:
			currentAnimation = dlAnimation;
			break;
		}
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}
	
	
	public void draw(SpriteBatch batch) {
		batch.setColor(tint);
		batch.draw(currentFrame, x, y);
		batch.setColor(Color.WHITE);
		tint = Color.WHITE;
	}
		
	public void tint(Color color) {
		this.tint = color;
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
