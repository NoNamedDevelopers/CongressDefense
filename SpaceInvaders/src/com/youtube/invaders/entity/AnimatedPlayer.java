package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.yutube.invaders.camera.OrthoCamera;

public class AnimatedPlayer extends Entity {
	private long lastFire;

	private OrthoCamera camera;

	private static final int FRAME_COLS = 5;
	private static final int FRAME_ROWS = 2;

	Animation flyAnimation;
	static Texture flySheet;
	TextureRegion[] flyFrames;
	// SpriteBatch spriteBatch;
	TextureRegion currentFrame;

	float stateTime;

	static {
		flySheet = new Texture(Gdx.files.internal("policia_sprite.png"));
	}

	public AnimatedPlayer(Vector2 pos, Vector2 direction,
			EntityManager entityManager, OrthoCamera camera) {
		super(flySheet, pos, direction);
		this.camera = camera;
		// ============================================================================
		// Animation
		TextureRegion[][] tmp = TextureRegion.split(flySheet,
				flySheet.getWidth() / FRAME_COLS, flySheet.getHeight()
						/ FRAME_ROWS);
		flyFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				flyFrames[index++] = tmp[i][j];
			}
		}
		flyAnimation = new Animation(0.025f, flyFrames);
		stateTime = 0f;
		// ==============================================================================

	}

	@Override
	public void update() {

		stateTime += Gdx.graphics.getDeltaTime(); // #15
		currentFrame = flyAnimation.getKeyFrame(stateTime, true); // #16
		pos.add(direction);

		int dir = 0;

		if (Gdx.input.isTouched()) {
			Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			if (touch.x > MainGame.WIDTH / 2)
				dir = 2;

			else
				dir = 1;
		}

		if (Gdx.input.isKeyPressed(Keys.A) || dir == 1)
			setDirection(-300, 0);
		else if (Gdx.input.isKeyPressed(Keys.D) || dir == 2)
			setDirection(300, 0);
		else
			setDirection(0, 0);

		// if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if (System.currentTimeMillis() - lastFire >= 500) {

				lastFire = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(currentFrame, pos.x, pos.y);
		// sb.draw(sprite, pos.x, pos.y);
		// sb.draw(texture, pos.x, pos.y);
	}

}
