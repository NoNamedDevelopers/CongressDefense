package com.youtube.invaders.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.youtube.invaders.MainGame;
import com.yutube.invaders.camera.OrthoCamera;

public class EntityManager {

	Player player;
	List<AnimatedPlayer> grupoPolicias;
	TextGUI textGUI;

	public Sound killed, hit;
	// public AssetFonts fonts;

	private final Array<Entity> entities = new Array<Entity>();

	public EntityManager(int amount, OrthoCamera camera) {
		this.grupoPolicias = new ArrayList<AnimatedPlayer>();
		Random randomGenerator = new Random();
		for (int i = 0; i < 50; i++) {
			this.grupoPolicias.add(new AnimatedPlayer(new Vector2(
					/* [0 - 250] */
					randomGenerator.nextInt(100) + 200,
					/* [15-35] */
					randomGenerator.nextInt(20) + 15), 
					new Vector2(0, 0),
					this,
					camera));
		}

		textGUI = new TextGUI(new Vector2(MainGame.WIDTH / 2, MainGame.HEIGHT
				- ((int) (1.5 * 2))), new Vector2(0, 0));

		killed = Gdx.audio.newSound(Gdx.files.internal("sounds/killed.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));

		// fonts = new AssetFonts();
	}

	public void update() {

		for (Entity e : entities)
			e.update();
		// player.update();
		for (AnimatedPlayer a : grupoPolicias)
			a.update();
		// textGUI.update();
		checkCollisions();
	}

	public void render(SpriteBatch sb) {

		for (Entity e : entities)
			e.render(sb);

		for (AnimatedPlayer a : grupoPolicias)
			a.render(sb);
		textGUI.render(sb);

	}

	private void checkCollisions() {
		// not useful right now, maybe in the future
	}

	public void addEntity(Entity e) {
		entities.add(e);

	}

	public void dispose() {

	}

	// GUI Management
	public class AssetFonts {

		public final BitmapFont font;

		public AssetFonts() {
			font = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"),
					false);

			font.setScale(1.0f);
			font.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

}
