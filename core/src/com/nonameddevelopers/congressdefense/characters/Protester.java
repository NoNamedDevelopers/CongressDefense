package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;
import com.nonameddevelopers.congressdefense.screens.GameOverScreen;

public abstract class Protester extends GameCharacter {

	private int xGoal = 1540;
	private int yGoal = 835;

	protected float speedFactor;

	private static GameSound joy, die;

	protected float appearTime;

	protected float timeInDirection = 0.2f;

	protected static Random random;
	protected Vector2 goal;
	protected Vector2 position;
	protected Vector2 direction2;

	static {
		joy = new GameSound("sounds/joy.mp3", 1150);
		die = new GameSound("sounds/die.mp3", 670);
		random = new Random();
	}

	public Protester(final CongressDefense game, float x, float y, String type,
			int columns, int rows, float appearTime, float frameSpeed) {
		super(game, x, y, type, columns, rows, frameSpeed);

		this.appearTime = appearTime;
		this.speedFactor = 1f;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		stateTime += delta;
		timeInDirection += delta;
		if (stateTime < appearTime)
			return;

		approach(delta);
		updateAnimation();

		boundingCircle.set(x + 32, y + 32, 40f);
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}

	protected void attackCongress() {
		if (!isGhost) {
			if (game.life - 1 <= 0)
				game.setScreen(new GameOverScreen(game));
			else {
				joy.play();
				isDead = true;
				game.life--;
			}
		}
	}

	@Override
	public void hurt(int damage) {
		super.hurt(damage);
	}

	@Override
	public void kill() {
		super.kill();
		die.play();
		addScoreAndMoney();
	}

	public void addScoreAndMoney() {
		game.score += 5;
		game.money += 2;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void approach(float delta) {
		arrived();

		direction2 = new Vector2();

		int n = random.nextInt(100);
		if (n < 60) {
			goal = new Vector2(getxGoal(), getyGoal());
			position = new Vector2(x, y);
		} else if (n < 80) {
			goal = new Vector2(getxGoal(), 0);
			position = new Vector2(x, 0);
		} else {
			goal = new Vector2(0, getyGoal());
			position = new Vector2(0, y);
		}

		direction2.set(goal).sub(position).nor();
		x += direction2.x * (random.nextInt(3)) * delta * 100 * speedFactor;
		y += direction2.y * (random.nextInt(3)) * delta * 100 * speedFactor;

		if (timeInDirection > 0.2f) {
			timeInDirection = 0f;
			if (Math.abs(direction2.x) < 0.1f && direction2.y >= 0)
				this.direction = UP;
			else if (Math.abs(direction2.x) < 0.1f && direction2.y < 0)
				this.direction = DOWN;
			else if (direction2.x > 0 && Math.abs(direction2.y) < 0.1f)
				this.direction = RIGHT;
			else if (direction2.x < 0 && Math.abs(direction2.y) < 0.1f)
				this.direction = LEFT;
			else if (direction2.x > 0 && direction2.y > 0)
				this.direction = UP_RIGHT;
			else if (direction2.x > 0 && direction2.y < 0)
				this.direction = DOWN_RIGHT;
			else if (direction2.x < 0 && direction2.y > 0)
				this.direction = UP_LEFT;
			else
				this.direction = DOWN_LEFT;
		}
	}

	protected void arrived() {
		if (x > xGoal - 10 && x < xGoal + 10 && y > yGoal - 10
				&& y < yGoal + 10) {
			attackCongress();
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
