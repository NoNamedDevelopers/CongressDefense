package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.screens.GameOverScreen;

public abstract class Protester extends GameCharacter {

	private static Random r;

	private int xGoal = 813;
	private int yGoal = 369;

	private static Array<Sound> moans;
	private static Sound joy, die;
	private static Texture lifeBarTexture, lifeBlockTexture;
	private Sprite lifeBar, lifeBlock;

	public int life;
	protected float appearTime;
	private boolean isDead = false;

	static {
		r = new Random();
		moans = new Array<Sound>();
		moans.add(Gdx.audio.newSound(Gdx.files.internal("sounds/moan_0.mp3")));
		moans.add(Gdx.audio.newSound(Gdx.files.internal("sounds/moan_1.mp3")));
		
		joy = Gdx.audio.newSound(Gdx.files.internal("sounds/joy.mp3"));
		die = Gdx.audio.newSound(Gdx.files.internal("sounds/die.mp3"));
		
		lifeBarTexture = new Texture(Gdx.files.internal("sprites/lifebar.png"));
		lifeBlockTexture = new Texture(Gdx.files.internal("sprites/lifeblock.png"));
	}

	public Protester(final CongressDefense game, float x, float y, String type, int columns, int rows,float appearTime) {
		super(game, x, y, type, columns, rows, 0.02f);

		this.appearTime = appearTime;

		life = 100;
		lifeBar = new Sprite(lifeBarTexture);
		lifeBlock = new Sprite(lifeBlockTexture);
	}
	
	public void update(float delta) {
		stateTime += delta;
		if (stateTime < appearTime)
			return;


		approach();
		updateAnimation();

		boundingCircle.set(x + 16, y + 16, 20f);
	}

	@Override
	protected void updateAnimation() {
		switch (direction) {
		case UP_RIGHT:
			currentFrame = ulAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN_RIGHT:
			currentFrame = dlAnimation.getKeyFrame(stateTime, true);
			break;
		case UP_LEFT:
			currentFrame = urAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN_LEFT:
			currentFrame = drAnimation.getKeyFrame(stateTime, true);
			break;
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
				

		lifeBar.setPosition(x+6, y+32);
		lifeBar.draw(batch);
		for (int i = 0; i<life/10; i++) {
			lifeBlock.setPosition(x+6+i*2, y+32);
			lifeBlock.draw(batch);
		}
	}

	public void hurt(int damage) {	
		life -= damage;
		moans.get(r.nextInt(2)).play();
		if (life <= 0 && !isDead)
			kill();
	}

	protected void attackCongress() {
		if (game.life - 1 <= 0)
			game.setScreen(new GameOverScreen(game));
		else {
			joy.play();
			isDead = true;
			game.life--;
		}
	}

	protected void kill() {
		isDead = true;
		die.play();
		game.score += 5;
		game.money += 10;
	}

	public boolean isDead() {
		return isDead;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public abstract void approach();

	protected void arrived() {
		if (x>803 && x<823)
		{
			if (y < 379 && y >359)
			{
				attackCongress();
			}
		}
		
	}

	public int getxGoal() {
		return xGoal;
	}

	public void setxGoal(int xGoal) {
		this.xGoal = xGoal;
	}

	public int getyGoal() {
		return yGoal;
	}

	public void setyGoal(int yGoal) {
		this.yGoal = yGoal;
	}
	
	

}
