package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class BazookaCop extends Cop {
	
	private Sound shot;

	public BazookaCop(CongressDefense game, float x, float y) {

		super(game, x, y);
		direction = UP_LEFT;

		boundingCircle = new Circle();
		boundingCircle.set(x+90, y+90, 10f);
		
		ulAnimation = loadAnimation("sprites/copgun/up_left.png", 5, 5, 0.02f);
		dlAnimation = loadAnimation("sprites/copgun/down_left.png", 5, 5, 0.02f);	
		urAnimation = loadAnimation("sprites/copgun/up_right.png", 5, 5, 0.02f);	
		drAnimation = loadAnimation("sprites/copgun/down_right.png", 5, 5, 0.02f);	
		
		//shot = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));

		updateAnimation();
	}
	
	private void updateAnimation() {		
		switch(direction) {
			case UP_LEFT:
				currentFrame = ulAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_LEFT:
				currentFrame = dlAnimation.getKeyFrame(stateTime, true);
				break;
			case UP_RIGHT:
				currentFrame = urAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_RIGHT:
				currentFrame = drAnimation.getKeyFrame(stateTime, true);
				break;
		}
	}
	
	@Override
	public void checkCollision(Crowd crowd) {
		boolean atLeastOne = false;
		for (Protester protester : crowd.getProtesters())
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle)) {
			atLeastOne = true;
			System.out.println(" PRE state - elapsed: " +(stateTime-elapsedTime));
			if (x-protester.x >0 && y-protester.y>0)
				direction = UP_RIGHT;
			else if (x-protester.x > 0 && y-protester.y < 0)
				direction = DOWN_RIGHT;
			else if (x-protester.x < 0 && y-protester.y < 0)
				direction = DOWN_LEFT;
			else
				direction = UP_LEFT;
			updateAnimation();	

				if (Math.abs(stateTime-elapsedTime) >= 0.5) {
					elapsedTime = stateTime;
					//shoot.play();
					protester.hurt(40);
					shoot(protester);
				}
			}		
		if (!atLeastOne) {
			// EL ERROR DE LOS POLICIAS SUPER ASESINO ESTABA AQIO
//			stateTime = 0;
//			elapsedTime = -0.5f;
		}
	}
	
	private void shoot(Protester protester)
	{
		//proyectileL.add(new Proyectile(game, x, y, protester));
	}

}
