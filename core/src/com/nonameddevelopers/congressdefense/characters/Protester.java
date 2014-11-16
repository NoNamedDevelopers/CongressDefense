package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.screens.GameOverScreen;

public abstract class Protester extends GameCharacter {

	protected Circle boundingCircle;
	private static Random r;

	private int xGoal = 813;
	private int yGoal = 369;

	private Array<Sound> moans;
	private Sound joy;

	public int life;
	protected float appearTime;
	private boolean isDead = false;

	static {
		r = new Random();
	}

	public Protester(final CongressDefense game, float x, float y,
			float appearTime) {
		super(game, x, y, "protester" + r.nextInt(3), 4, 5, 0.02f);

	    direction = UP_RIGHT;

		this.appearTime = appearTime;

		life = 100;

		stateTime = 0f;

		boundingCircle = new Circle();
		
		moans = new Array<Sound>();
		moans.add(Gdx.audio.newSound(Gdx.files.internal("sounds/moan_0.mp3")));
		moans.add(Gdx.audio.newSound(Gdx.files.internal("sounds/moan_1.mp3")));
		
		joy = Gdx.audio.newSound(Gdx.files.internal("sounds/joy.mp3"));

		updateAnimation();
	}
	
	public void update(float delta) {
		stateTime += delta;
		if (stateTime < appearTime)
			return;


		approach();
		updateAnimation();

		boundingCircle.set(x + 16, y + 16, 20f);
	}

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

	public void draw(SpriteBatch batch) {
		batch.draw(currentFrame, x, y);
		game.font.setScale(0.6f);
		game.font.draw(batch, "|" + life + "|", x + 5, y + 40);
		game.font.setScale(1.5f);
	}

	public void hurt(int damage) {
		
		life -= damage;
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
		moans.get(r.nextInt(2)).play();
		isDead = true;
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
