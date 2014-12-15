package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public abstract class GameCharacter implements Comparable<GameCharacter> {

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


	private static Random r;
	private static Array<GameSound> moans;
	
	protected boolean isDead = false;
	protected boolean isGhost = false;
	protected boolean isHurted = false;
	protected float timeHurted = 0f;
	private float deadX = 0f;
	private float deadY = 0f;
	private float deadAlpha = 1f; 
	
	protected int life;
	protected int initialLife;

	private static Texture lifeBarTexture, lifeBlockTexture, ghostTexture;
	private Sprite lifeBar, lifeBlock, ghost;
	
	static {
		textures = new ObjectMap<String, Texture>();
		lifeBarTexture = new Texture(Gdx.files.internal("sprites/lifebar.png"));
		lifeBlockTexture = new Texture(Gdx.files.internal("sprites/lifeblock.png"));
		ghostTexture = new Texture(Gdx.files.internal("sprites/ghost.png"));
		

		r = new Random();
		moans = new Array<GameSound>();
		moans.add(new GameSound("sounds/moan_0.mp3", 300));
		moans.add(new GameSound("sounds/moan_1.mp3", 300));
		moans.add(new GameSound("sounds/moan_2.mp3", 330));
	}	
	
	public GameCharacter(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		this.game = game;

		this.x = x;
		this.y = y;
		this.type = type;
		
		stateTime = 0f;

		if (type != "") {
			uAnimation = loadAnimation("sprites/"+type+"/up.png", columns, rows, animationSpeed);
			dAnimation = loadAnimation("sprites/"+type+"/down.png", columns, rows, animationSpeed);
			lAnimation = loadAnimation("sprites/"+type+"/left.png", columns, rows, animationSpeed);
			rAnimation = loadAnimation("sprites/"+type+"/right.png", columns, rows, animationSpeed);
			ulAnimation = loadAnimation("sprites/"+type+"/up_left.png", columns, rows, animationSpeed);
			dlAnimation = loadAnimation("sprites/"+type+"/down_left.png", columns, rows, animationSpeed);	
			urAnimation = loadAnimation("sprites/"+type+"/up_right.png", columns, rows, animationSpeed);	
			drAnimation = loadAnimation("sprites/"+type+"/down_right.png", columns, rows, animationSpeed);	
		}
		
	    direction = DOWN;
		updateAnimation();
		boundingCircle = new Circle();
		
		life = 100;
		initialLife = life;
		

		lifeBar = new Sprite(lifeBarTexture);
		lifeBlock = new Sprite(lifeBlockTexture);
		ghost = new Sprite(ghostTexture);
		ghost.setSize(64, 64);
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
	
	protected void update(float delta) {
		if(isGhost) {
			deadY += 50*delta;
			deadAlpha -= delta ;
			if (deadAlpha <= 0f) {
				isDead = true;
			}
		}
		else {
			if (isHurted) {
				timeHurted += delta;
			}
			else { 
				timeHurted = 0;
			}
		}
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
		if (isGhost) {
			ghost.setAlpha(deadAlpha);
			ghost.setPosition(deadX, deadY);
			ghost.draw(batch);
		}
		else {
			if (isHurted) {
				tint(Color.RED);
				if (timeHurted > 0.05f)
					isHurted = false;
			}
			batch.setColor(tint);
			batch.draw(currentFrame, x, y);
			batch.setColor(Color.WHITE);
			tint(Color.WHITE);
			drawLife(batch);
		}
	}
	
	private void drawLife(SpriteBatch batch) {		
		if (initialLife != life) {
			lifeBar.setPosition(x+22, y+64);
			lifeBar.draw(batch);
			for (int i = 0; i<10f*life/initialLife; i++) {
				lifeBlock.setPosition(x+22+i*2, y+64);
				lifeBlock.draw(batch);
			}
		}
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
	

	public void hurt(int damage) {
		moans.get(r.nextInt(3)).play(0.5f);
		isHurted = true;
		life -= damage;
		if (life <= 0 && !isDead)
			kill();
	}
	
	public void kill() {
		isGhost = true;
		deadX = x;
		deadY = y;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public boolean isGhost() {
		return isGhost;
	}
	
	@Override
	public int compareTo(GameCharacter gc) {
		int ownY = 0;
		int gcY = 0;
		if (gc!=null) {
			ownY = MathUtils.floor(y);
			gcY = MathUtils.floor(gc.y);
			return gcY-ownY;
		}
		return 0;
	}
	
}
