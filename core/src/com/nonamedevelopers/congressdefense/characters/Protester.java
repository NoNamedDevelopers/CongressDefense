package com.nonamedevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.nonamedevelopers.congressdefense.CongressDefense;

public class Protester extends GameCharacter {
	
	private static final float SPEED_X = 0.4f; 
	private static final float SPEED_Y = 0.4f/1.5f; 
	private Circle boundingCircle;
	private static Random r;
	
	private ObjectMap<int[], Short> points;	
	
	private int life;	
	private float appearTime;	
	private boolean isDead = false;
	
	static {
		r = new Random();
	}

	
	public Protester(final CongressDefense game, float x, float y, float appearTime) {
		super(game,x,y, "protester"+r.nextInt(3), 4, 5, 0.06f);

		direction = UP_LEFT;
		
		this.appearTime = appearTime;
		
		life = 100;
		
		points = new ObjectMap<int[], Short>();
		
		stateTime = 0f;
		
		boundingCircle = new Circle();
		
		
		points.put(new int[]{76,561}, DOWN_LEFT);
		points.put(new int[]{215,469}, DOWN_RIGHT);
		points.put(new int[]{144,422}, DOWN_LEFT);
		points.put(new int[]{249,352}, UP_LEFT);
		points.put(new int[]{387,445}, DOWN_LEFT);
		points.put(new int[]{490,376}, DOWN_RIGHT);
		points.put(new int[]{389,310}, DOWN_LEFT);
		points.put(new int[]{556,198}, UP_LEFT);
		points.put(new int[]{813,369}, REMOVE);
		
		updateAnimation();
	}
	
	public void update(float delta) {		
		stateTime += delta;		
		if (stateTime < appearTime)
			return;
		
		for (Entry<int[],Short> point : points)
			if (point.key[0] == MathUtils.floor(x) 
				&& point.key[1] ==  MathUtils.floor(y)) 
				direction = point.value;
	
		updateAnimation();
		
		boundingCircle.set(x+16, y+16, 20f);
	}
	
	
	private void updateAnimation() {		
		switch(direction) {
			case UP_LEFT:
				x += 1 * SPEED_X;
				y += 1 * SPEED_Y;
				currentFrame = ulAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_LEFT:
				x += 1 * SPEED_X;
				y += -1 * SPEED_Y;
				currentFrame = dlAnimation.getKeyFrame(stateTime, true);
				break;
			case UP_RIGHT:
				x += -1 * SPEED_X;
				y += 1 * SPEED_Y;
				currentFrame = urAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_RIGHT:
				x += -1 * SPEED_X;
				y += -1 * SPEED_Y;
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
		game.font.draw(batch, "|"+life+"|", x+5, y+40);
		game.font.setScale(1.5f);
	}
	
	public void hurt(int damage) {
		life -= damage;
		if (life <= 0)
			kill();
	}
	
	private void attack() {
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
	
	
}
