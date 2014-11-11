package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.screens.GameOverScreen;

public class Protester extends GameCharacter {

	private static final float SPEED_X = 0.4f;
	private static final float SPEED_Y = 0.4f / 1.5f;
	private Circle boundingCircle;
	private static Random r;

	private int xGoal = 813;
	private int yGoal = 369;

	private ObjectMap<int[], Short> points;

	public int life;
	private float appearTime;
	private boolean isDead = false;

	static {
		r = new Random();
	}

	public Protester(final CongressDefense game, float x, float y,
			float appearTime) {
		super(game, x, y, "protester" + r.nextInt(3), 4, 5, 0.06f);

	    direction = UP_RIGHT;

		this.appearTime = appearTime;

		life = 100;

		points = new ObjectMap<int[], Short>();

		stateTime = 0f;

		boundingCircle = new Circle();

		updateAnimation();
	}
	
	public void update(float delta) {
		stateTime += delta;
		if (stateTime < appearTime)
			return;

		//for (Entry<int[], Short> point : points)
		//	if (point.key[0] == MathUtils.floor(x)
		//			&& point.key[1] == MathUtils.floor(y))
		//		direction = point.value;

		approach();
		updateAnimation();

		//currentFrame = ulAnimation.getKeyFrame(stateTime, true);
		boundingCircle.set(x + 16, y + 16, 20f);
	}

	private void updateAnimation() {
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
		case REMOVE:
			attack();
			kill();
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
		if (life <= 0)
		{
			game.score += 5;
			game.money += 10;
			kill();
		}
	}

	private void attack() {
		if (game.life - 1 <= 0)
			game.setScreen(new GameOverScreen(game));
		else
			game.life--;
	}

	private void kill() {
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	private void approach() {

		arrived();
		
		Random random = new Random();
		Vector2 Goal = new Vector2(xGoal, yGoal);
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		
		int n = random.nextInt(100);
		
		if(n<60){
			Goal = new Vector2(xGoal,yGoal);
			direction2.set(Goal).sub(position).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}
		/*
		else if (n < 70)
		{
			direction.set(1,0);
			x += direction.x * (random.nextInt(3));
			y += direction.y * (random.nextInt(3));
		}
		else if (n < 80)
		{
			direction.set(-1,0);
			x += direction.x * (random.nextInt(3));
			y += direction.y * (random.nextInt(3));
		}
		else if (n < 90)
		{
			direction.set(0,1);
			x += direction.x * (random.nextInt(3));
			y += direction.y * (random.nextInt(3));
		}
		else
		{
			direction.set(0,-1);
			x += direction.x * (random.nextInt(3));
			y += direction.y * (random.nextInt(3));
		}
		*/
		else if (n < 80){
			//float modif = random.nextFloat();
			Goal = new Vector2(xGoal,0);
			position = new Vector2(x, 0);
			direction2.set(Goal).sub(position).nor();
			//direction.set(direction.x, modif).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}
		else
		{
			//float modif = random.nextFloat();
			Goal = new Vector2(0, yGoal);
			position = new Vector2(0, y);
			direction2.set(Goal).sub(position).nor();
			//direction.set(modif, direction.y).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}
		
				
			
		

	}

	private void arrived() {
		if (x>803 && x<823)
		{
			if (y < 379 && y >359)
			{
				attack();
				kill();
			}
		}
		
	}

}
